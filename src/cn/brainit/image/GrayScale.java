package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * In photography and computing,
 * 
 * a grayscale or greyscale digital image is an image in which the value of each
 * pixel is a single sample,
 * 
 * that is, it carries only intensity information.
 * 
 * more info :https://en.wikipedia.org/wiki/Grayscale
 * 
 * @author brainit brainit
 * 
 *         http://www.brainit.cn/
 * 
 */
public class GrayScale {

	/**
	 * Source Image Data
	 */
	public BufferedImage image;

	/**
	 * when gray scale the Image with component
	 * 
	 * different component can be choose
	 * 
	 * including red ,green ,blue
	 * 
	 * @author brainit brainit
	 * 
	 *         http://www.brainit.cn/
	 * 
	 */
	public class Component {
		/**
		 * sign the method type,
		 * 
		 * gray scale the image with red
		 */
		public static final int TYPE_RED = 1201;
		/**
		 * sign the method type
		 * 
		 * gray scale the image with green
		 */
		public static final int TYPE_GREEEN = 1202;
		/**
		 * sign the method type
		 * 
		 * gray scale the image with blue
		 */
		public static final int TYPE_BLUE = 1203;
	}

	/**
	 * Calssic Weight of method toGrayImageWithWeightMean()
	 * 
	 * 
	 * @author brainit
	 * 
	 */
	public class WeightedMean {

		public static final double WEIGHT_RED = 0.3;
		public static final double WEIGHT_GREEN = 0.59;
		public static final double WEIGHT_BLUE = 0.11;
	}

	/**
	 * Build the Class with Image File
	 * 
	 * @param file
	 *            image file
	 */
	public GrayScale(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error to load the file");
		}
	}

	/**
	 * Build the Class with BufferedImage
	 * 
	 * @param bi
	 *            the parameters to build the class
	 */
	public GrayScale(BufferedImage bi) {
		this.image = bi;
	}

	/**
	 * gray scale image with max of RGB
	 * 
	 * gray = max(R,G,B)
	 * 
	 * @return the bufferedImage after editing
	 */
	public BufferedImage toGrayImageWithMax() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		// the detail of the method to gray
		int type = image.getType();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int cARGB = image.getRGB(i, j);
				int alpha = (cARGB >> 24) & 0xff;
				int red = (cARGB >> 16) & 0xff;
				int green = (cARGB >> 8) & 0xff;
				int blue = cARGB & 0xff;
				int gray = formatGray(Math.max(Math.max(red, green), blue));
				int newRGB = (gray << 24) | (gray << 16) | (gray << 8) | gray;
				result.setRGB(i, j, newRGB);
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method toGrayImageWithMax()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void toGrayImageWithMax(File output) {
		BufferedImage result = toGrayImageWithMax();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * gray scale image with component of RGB
	 * 
	 * gray = (R | G | B)
	 * 
	 * @return the bufferedImage after editing with gray
	 */
	public BufferedImage toGrayImageWithComponent(int componentType) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		// the detail of the method to gray
		int type = image.getType();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int cARGB = image.getRGB(i, j);
				int alpha = (cARGB >> 24) & 0xff;
				int red = (cARGB >> 16) & 0xff;
				int green = (cARGB >> 8) & 0xff;
				int blue = cARGB & 0xff;
				int gray = red;
				switch (componentType) {
				case Component.TYPE_RED:
					gray = red;
					break;
				case Component.TYPE_GREEEN:
					gray = green;
					break;
				case Component.TYPE_BLUE:
					gray = blue;
					break;
				default:
					break;
				}
				gray = formatGray(gray);
				int newRGB = (gray << 24) | (gray << 16) | (gray << 8) | gray;
				result.setRGB(i, j, newRGB);
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method
	 * toGrayImageWithComponent()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void toGrayImageWithComponent(int componentType, File output) {
		BufferedImage result = toGrayImageWithComponent(componentType);
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * gray scale image with average of RGB
	 * 
	 * gray = (R + G + B)/3
	 * 
	 * @return the bufferedImage after editing with gray
	 */
	public BufferedImage toGrayImageWithAverage() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		// the detail of the method to gray
		int type = image.getType();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int cARGB = image.getRGB(i, j);
				int alpha = (cARGB >> 24) & 0xff;
				int red = (cARGB >> 16) & 0xff;
				int green = (cARGB >> 8) & 0xff;
				int blue = cARGB & 0xff;
				int gray = formatGray((red + green + blue) / 3);
				int newRGB = (gray << 24) | (gray << 24) | (gray << 16)
						| (gray << 8) | gray;
				result.setRGB(i, j, newRGB);
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method toGrayImageWithAverage()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void toGrayImageWithAverage(File output) {
		BufferedImage result = toGrayImageWithAverage();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * gray scale image with WeightedMean of RGB
	 * 
	 * gray = aR + bG + cB;
	 * 
	 * commonly:a + b + c = 1;
	 * 
	 * a>=0;b>=0;c>=0
	 * 
	 * @return the bufferedImage after editing with gray
	 */
	public BufferedImage toGrayImageWithWeightedMean(double redWeight,
			double greenWeight, double blueWeight) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		// the detail of the method to gray
		int type = image.getType();
		double total = redWeight + greenWeight + blueWeight;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int cARGB = image.getRGB(i, j);
				int alpha = (cARGB >> 24) & 0xff;
				int red = (cARGB >> 16) & 0xff;
				int green = (cARGB >> 8) & 0xff;
				int blue = cARGB & 0xff;
				int gray = formatGray((int) (redWeight / total * red
						+ greenWeight / total * green + blueWeight / total
						* blue));
				int newRGB = (gray << 24) | (gray << 16) | (gray << 8) | gray;
				result.setRGB(i, j, newRGB);
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method
	 * toGrayImageWithWeightedMean()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void toGrayImageWithWeightedMean(double redWeight,
			double greenWeight, double blueWeight, File output) {
		BufferedImage result = toGrayImageWithWeightedMean(redWeight,
				greenWeight, blueWeight);
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * format gray of one pixel
	 * 
	 * if gray<0
	 * 
	 * format: gray=0
	 * 
	 * if gray>255
	 * 
	 * format: gray=255
	 * 
	 * @param gray
	 *            the gray of one pixel
	 * @return the formated gray
	 */
	public static int formatGray(int gray) {
		int result = gray > 255 ? 255 : (gray < 0 ? 0 : gray);
		return result;
	}

	/**
	 * Testing method
	 * 
	 * @param args
	 *            system parameters
	 */
	public static void main(String[] args) {
		GrayScale sc = new GrayScale(new File("E://1.jpg"));
		File file2 = new File("E://GrayWithMax.jpg");
		File file3 = new File("E://GrayWithComponent.jpg");
		File file4 = new File("E://GrayWithAverage.jpg");
		File file5 = new File("E://GrayWithWeightedMean.jpg");
		sc.toGrayImageWithMax(file2);
		sc.toGrayImageWithComponent(Component.TYPE_RED, file3);
		sc.toGrayImageWithWeightedMean(WeightedMean.WEIGHT_RED,
				WeightedMean.WEIGHT_GREEN, WeightedMean.WEIGHT_BLUE, file4);
		sc.toGrayImageWithAverage(file5);

	}
}
