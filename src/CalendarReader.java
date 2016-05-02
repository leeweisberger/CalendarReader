import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sourceforge.tess4j.Word;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;


public class CalendarReader {
	public static final String RESIDENT_CALENDAR="resident_cal.pdf";

	public static void main(String[] args) throws IOException {
		List<CalendarEntry> entryList = new ArrayList<>();
		//convert pdf to buffered image
		BufferedImage[] bis = PDFConverter.PDFToBufferedImage(RESIDENT_CALENDAR);
		for(BufferedImage bi : bis){
			//create a mat and process it
			Mat copy = ImageProcessingUtils.createMat(bi);
			Mat m = ImageProcessor.processImage(bi);
			//find contours of processed mat
			List<MatOfPoint> contours = RectangleBounder.getContours(m);
//			RectangleBounder.drawContours(m, contours);
			//bound contours in rectangles
			List<Rect> rectangles = RectangleBounder.getRectangles(contours,RectangleBounder.RECT_VERT_DIV_HORIZ, RectangleBounder.CONTOUR_PERIM_THRESH, RectangleBounder.MIN_RECT_WIDTH, RectangleBounder.MIN_RECT_HEIGHT);
			RectangleBounder.drawRectangles(m, rectangles);
			
			System.out.println("Num Rectangles: " + rectangles.size());
			ImageProcessingUtils.writeMat(new Random().nextInt(999) + "test.jpg", m);
			//loop through the rectangles, read the text, and parse
			for(int i=0; i<rectangles.size(); i++){
				Mat bodySection = ImageProcessingUtils.cropMat(copy, rectangles.get(i));
				List<CalendarEntry> e = null;
				List<Word> lines = TextReader.readText(ImageProcessingUtils.convertMatToBufferedImage(bodySection));
				if(lines!=null)
					e = CalendarParser.parseCalendarEntries(lines);
				
				if(e!=null)
					entryList.addAll(e);
			}
			writeCalendarEntriesToFile(entryList);
		}
		
		
	}
	
	private static void writeCalendarEntriesToFile(List<CalendarEntry> entryList){
		try(FileWriter fw = new FileWriter("outfilename.txt", false);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
				for(CalendarEntry entry : entryList){
					out.println(entry.toString());
				}
			   
			} catch (IOException e) {
			}
	}

}
