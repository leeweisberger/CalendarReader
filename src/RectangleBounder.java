import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;


public class RectangleBounder {
	private static final int SAME_RECTANGLE_THRESH = 40;
	public static final int MIN_RECT_HEIGHT = 350;
	public static final int RECT_VERT_DIV_HORIZ = 1;
	public  static final int CONTOUR_PERIM_THRESH = 500;
	public static final int MIN_RECT_WIDTH = 285;
	
	
	public static List<Rect> getNumberAreaRectangles(List<Rect> rectangles) {
		List<Rect> topRectangles = new ArrayList<>();
	    for(Rect rect : rectangles){
	    	Rect topRect = new Rect(rect.x,rect.y, rect.width,60);
	    	topRectangles.add(topRect);
	    }
		return topRectangles;
	}
	
	//put a little bit of buffer on the rectangle to ensure that text does not get cut off
	private static void bufferRectangle(Rect r){
		r.set(new double []{r.x-1,r.y-1,r.width+1,r.height+1});
	}
	
	public static List<Rect> getRectangles(List<MatOfPoint> contours, double widthDivHeight, int contourAreaThresh, int minWidth, int minHeight) {
		List<Rect> rectangles = new ArrayList<Rect>();
	    for (int x = 0; x < contours.size(); x++) {
			MatOfPoint mp = contours.get(x);
			Mat m2 = new Mat();
			mp.convertTo(m2, -1);
			double area = Imgproc.contourArea(m2);
			if(area>contourAreaThresh){
				Rect r = Imgproc.boundingRect(contours.get(x));
				//only get vertical rectangles that pass the ratio, height, and width checks
				if((1.0*r.width/r.height)<widthDivHeight && r.height>minHeight && r.width>minWidth){
					bufferRectangle(r);
					if(!rectangleExists(r, rectangles))
						rectangles.add(r);
				}
			}
		}
		return rectangles;
	}
	
	private static boolean rectangleExists(Rect r, List<Rect> rectangles){
		for(Rect test : rectangles){
			if(Math.abs(test.x-r.x)<SAME_RECTANGLE_THRESH &&
					Math.abs(test.y-r.y)<SAME_RECTANGLE_THRESH &&
					Math.abs(test.width-r.width)<SAME_RECTANGLE_THRESH &&
					Math.abs(test.height-r.height)<SAME_RECTANGLE_THRESH){
				return true;
			}
		}
		return false;
	}


	public static void drawRectangles(Mat m, List<Rect> rectangles) {
		ImageProcessingUtils.convertToColor(m);
		for(Rect r : rectangles){
			Imgproc.rectangle(m, r.tl(), r.br(), ImageProcessingUtils.SCALARS[new Random().nextInt(ImageProcessingUtils.SCALARS.length)],ImageProcessingUtils.DRAW_WIDTH);
		}
		
	}
	
	public static List<MatOfPoint> getContours(Mat m){
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hier = new Mat();
		Imgproc.findContours(m, contours, hier, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);		
		return contours;
	}
	
	public static void drawContours(Mat m,List<MatOfPoint> contours ){
		ImageProcessingUtils.convertToColor(m);
		for (int x = 0; x < contours.size(); x++) {
			MatOfPoint mp = contours.get(x);
			MatOfPoint2f  mp2f = new MatOfPoint2f( mp.toArray() );
			double perimeter = Imgproc.arcLength(mp2f, false);
			if(perimeter>CONTOUR_PERIM_THRESH){
//				System.out.println(perimeter);

				Imgproc.drawContours(m, contours, x, ImageProcessingUtils.SCALARS[new Random().nextInt(ImageProcessingUtils.SCALARS.length)],ImageProcessingUtils.DRAW_WIDTH);
			}


		}
	}

}
