package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Binaryzation {
	public BufferedImage image;
	/**
	 * 
	 */
	public static final int SAMPLE_THRESHOLD = 127;

	public Binaryzation(BufferedImage image) {
		this.image = image;
	}

	public Binaryzation(File input) {
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("fail to load the file ");
			e.printStackTrace();
		}
	}

	public BufferedImage binaryzationWithSampleThreshold() {
		int threshold = SAMPLE_THRESHOLD;
		return binaryzationWithThreshold(threshold);
	}

	public void binaryzationWithSampleThreshold(File output) {
		BufferedImage result = binaryzationWithSampleThreshold();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	public BufferedImage binaryzationWithAverage() {
		int width = image.getWidth();
		int height = image.getHeight();
		int sum = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				int gray = (rgb >> 8) & 0xff;
				sum += gray;
			}
		}
		int threshold = sum / (width * height);
		return binaryzationWithThreshold(threshold);
	}

	public void binaryzationWithAverage(File output) {
		BufferedImage result = binaryzationWithAverage();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	public BufferedImage binaryzationWithHistogram() {
		int threshold = ostu();
		return binaryzationWithThreshold(threshold);
	}

	/**
	 * binaryzation of the gray Image
	 * 
	 * you may see more in
	 * 
	 * https://en.wikipedia.org/wiki/Thresholding_(image_processing)
	 * 
	 * @param output
	 */
	public void binaryzationWithHistogram(File output) {
		BufferedImage result = binaryzationWithHistogram();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * Otsu's method to get the threshold of the binaryzation
	 * 
	 * you may see more in
	 * 
	 * https://en.wikipedia.org/wiki/Otsu%27s_method
	 * 
	 * @return the threshold of the binaryzation
	 */
	public int ostu() {
		int width = image.getWidth();
		int height = image.getHeight();
		int total = width * height;
		// build the histogram and each intensity level
		int[] histogram = new int[256];// the histogram
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				int gray = (rgb >> 8) & 0xff;
				histogram[gray]++;
			}
		}
		// total is the number of pixels in the given image.
		int sum = 0;
		for (int t = 0; t < 256; t++) {
			sum += t * histogram[t];
		}
		int sumB = 0;
		int wB = 0;
		int wF = 0;
		int maximum = 0;
		int threshold = 0;
		for (int t = 0; t < 256; t++) {
			wB += histogram[t];// Weight Background
			if (wB == 0) {
				continue;
			}
			wF = total - wB;// Weight Foreground
			if (wF == 0) {
				break;
			}
			sumB += t * histogram[t];
			int mB = sumB / wB; // Mean Background
			int mF = (sum - sumB) / wF; // Mean Foreground
			// Calculate Between Class Variance
			int between = wB * wF * (mB - mF) * (mB - mF);
			if (between > maximum) {
				maximum = between;
				threshold = t;
			}
		}
		return threshold;
	}

	private BufferedImage binaryzationWithThreshold(int threshold) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = image.getRGB(x, y);
				int gray = Math.abs(rgb >> 16);
				System.out.println(gray);
				if (gray > threshold) {
					rgb = 0x000000;
				} else {
					rgb = 0xFFFFFF;
				}
				result.setRGB(x, y, rgb);
			}
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File input;
		input = new File("E://GrayWithMax.jpg");
		// input = new File("E://GrayWithAverage.jpg");
		// input = new File("E://GrayWithComponent.jpg");
		// input = new File("E://GrayWithWeightedMean.jpg");
		Binaryzation b = new Binaryzation(input);
		int ostu = b.ostu();
		System.out.println(ostu);
		b.binaryzationWithSampleThreshold(new File(
				"E://binaryzationWithSampleThreshold.jpg"));
		b.binaryzationWithAverage(new File("E://binaryzationWithAverage.jpg"));
		b.binaryzationWithHistogram(new File(
				"E://binaryzationWithHistogram.jpg"));
	}

}
