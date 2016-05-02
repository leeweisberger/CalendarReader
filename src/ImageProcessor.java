import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;


public class ImageProcessor {
	public static Mat processImage(BufferedImage bi){
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat m = ImageProcessingUtils.createMat(bi);
		ImageProcessingUtils.convertToBlackAndWhite(m);
		ImageProcessingUtils.applyGaussianBlur(m, ImageProcessingUtils.GAUSSIAN_BLUR_SIZE);
		ImageProcessingUtils.applyAdaptiveThreshold(m);
		ImageProcessingUtils.applyCanny(m);
		return m;
	}

}
