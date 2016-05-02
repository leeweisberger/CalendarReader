import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class ImageProcessingUtils {


	public static final int GAUSSIAN_BLUR_SIZE = 3;
	private static final int MAX_THRESH = 255;
	private static final int THREE_CHANNEL = 3;
	private static final int ONE_CHANNEL = 1;
	public static final int DRAW_WIDTH = 7;
	private static final int CANNY_THRESH_2 = 200;
	private static final int CANNY_THRESH_1 = 35;
	public final static Scalar RED = new Scalar(0,0,255);
	public final static Scalar BLUE = new Scalar(255,0,0);
	public final static Scalar GREEN = new Scalar(0,255,0);
	public final static Scalar PINK = new Scalar(255, 102, 255);
	public final static Scalar LBLUE = new Scalar(255, 255, 77);
	public final static Scalar[] SCALARS = new Scalar[]{RED,BLUE,GREEN,PINK,LBLUE};

	public static void applyCanny(Mat m){
		Imgproc.Canny(m, m, CANNY_THRESH_1, CANNY_THRESH_1*3);
	}
	

	public static void convertToBlackAndWhite(Mat m){
		if(m.channels()!=ONE_CHANNEL)
			Imgproc.cvtColor(m, m, Imgproc.COLOR_BGR2GRAY);
	}
	
	public static void convertToColor(Mat m){
		if(m.channels()!=THREE_CHANNEL)
			Imgproc.cvtColor(m, m, Imgproc.COLOR_GRAY2RGB);
	}

	public static void applyAdaptiveThreshold(Mat m){
		Imgproc.adaptiveThreshold(m, m, MAX_THRESH,Imgproc.ADAPTIVE_THRESH_MEAN_C , Imgproc.THRESH_BINARY, 75, 10);

	}
	
	public static void applyOtsuThreshold(Mat m){
		Imgproc.threshold(m, m, 23, MAX_THRESH, Imgproc.THRESH_OTSU);
	}

	public static void applyGaussianBlur(Mat m, int size){
		Imgproc.GaussianBlur(m, m, new Size(size,size), 0);

	}

	public static Mat createMat(String fileName){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		return Imgcodecs.imread(fileName);
	}

	public static Mat createMat(BufferedImage bi){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		byte[] pixels = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		Mat mat = new Mat(bi.getHeight(), bi.getWidth(),CvType.CV_8UC1);
		mat.put(0, 0, pixels);
		return mat;
	}

	public static void writeMat(String fileName, Mat mat){
		Imgcodecs.imwrite(fileName, mat);
	}
	
	public static Mat cropMat(Mat m, Rect roi){
		return new Mat(m, roi);
	}
	
	public static BufferedImage convertMatToBufferedImage(Mat m){
		BufferedImage gray = new BufferedImage(m.width(), m.height(), BufferedImage.TYPE_BYTE_GRAY);

		// Get the BufferedImage's backing array and copy the pixels directly into it
		byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
		m.get(0, 0, data);
		return gray;
	}




}
