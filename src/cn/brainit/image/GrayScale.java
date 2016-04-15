package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayScale {

	public BufferedImage image;

	public class Component {
		public static final int TYPE_RED = 1201;
		public static final int TYPE_GREEEN = 1202;
		public static final int TYPE_BLUE = 1203;
	}

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
				int alpha = (cARGB >> 24) & 0xff; // 透明度通道
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
				int alpha = (cARGB >> 24) & 0xff; // 透明度通道
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
				int alpha = (cARGB >> 24) & 0xff; // 透明度通道
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
				int alpha = (cARGB >> 24) & 0xff; // 透明度通道
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

	public static int formatGray(int gray) {
		int result = gray > 255 ? 255 : (gray < 0 ? 0 : gray);
		return result;
	}

	/**
	 * @param args
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
