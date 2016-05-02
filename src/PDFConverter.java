import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.util.PdfUtilities;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;

//Utility class to convert PDF to BufferedImage
public class PDFConverter {
	public static BufferedImage[] PDFToBufferedImage(String fileName){
		File pdfFile = new File(fileName);
		PDFDocument doc = new PDFDocument();
		BufferedImage[] bis = null;
		try {
			doc.load(pdfFile);
			bis= new BufferedImage[doc.getPageCount()];
			File[] imageFiles = PdfUtilities.convertPdf2Png(pdfFile);
			System.out.println(imageFiles.length);
			for(int i=0; i<imageFiles.length; i++){
				bis[i] = ImageIO.read(imageFiles[i]);
			}	
		} catch (IOException | DocumentException e1) {
			e1.printStackTrace();
		}
		
		return bis;
	}

}
