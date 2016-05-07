package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A binary image is a digital image that has only two possible values for each
 * pixel.
 * 
 * Typically, the two colors used for a binary image are black and white,
 * 
 * though any two colors can be used.
 * 
 * Thresholding of the gray Image
 * 
 * you may see more in
 * 
 * https://en.wikipedia.org/wiki/Thresholding_(image_processing)
 * 
 * more info : https://en.wikipedia.org/wiki/Binary_image
 * 
 * @author brainit branit
 * 
 *         http://www.brainit.cn/
 * 
 */
public class Thresholding {
	public BufferedImage image;
	/**
	 * sample threshold: which threshold is up to use
	 */
	public static final int SAMPLE_THRESHOLD = 127;

	/**
	 * build the Thresholding with bufferedImage
	 * 
	 * @param image
	 *            bufferedImage
	 */
	public Thresholding(BufferedImage image) {
		this.image = image;
	}

	/**
	 * build the Thresholding with Image File
	 * 
	 * @param input
	 *            Image File
	 */
	public Thresholding(File input) {
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("fail to load the file ");
			e.printStackTrace();
		}
	}

	/**
	 * Thresholding with SAMPLE_THRESHOLD
	 * 
	 * SAMPLE_THRESHOD = 127;
	 * 
	 * @return the bufferedImage after editing
	 */
	public BufferedImage thresholdingWithSampleThreshold() {
		int threshold = SAMPLE_THRESHOLD;
		return thresholdingWithThreshold(threshold);
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method
	 * thresholdingWithSampleThreshold()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void thresholdingWithSampleThreshold(File output) {
		BufferedImage result = thresholdingWithSampleThreshold();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * Thresholding with average of grays
	 * 
	 * grays = (g1,g2,...,gn)
	 * 
	 * average = (g1 + g2 + ... + gn) / n
	 * 
	 * threshold = average
	 * 
	 * @return the bufferedImage after editing
	 */
	public BufferedImage thresholdingWithAverage() {
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
		return thresholdingWithThreshold(threshold);
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method
	 * thresholdingWithSampleThreshold()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 *            destFile
	 */
	public void thresholdingWithAverage(File output) {
		BufferedImage result = thresholdingWithAverage();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * Thresholding with Histogram
	 * 
	 * threshold = ostu()
	 * 
	 * more info you may find in ostu();
	 * 
	 * @return the bufferedImage after editing
	 */
	public BufferedImage thresholdingWithHistogram() {
		int threshold = ostu();
		return thresholdingWithThreshold(threshold);
	}

	/**
	 * output data to file
	 * 
	 * get the BufferedImage after editing with method
	 * thresholdingWithHistogram()
	 * 
	 * output the buffereImage to destFile
	 * 
	 * @param output
	 */
	public void thresholdingWithHistogram(File output) {
		BufferedImage result = thresholdingWithHistogram();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to write BufferedImage to File");
		}
	}

	/**
	 * Otsu's method to get the threshold of the thresholding
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

	/**
	 * Through the threshold
	 * 
	 * make sure one pixel with black or white
	 * 
	 * @param threshold
	 *            0 <= threshold <= 255
	 * @return return the bufferedImage after editing
	 */
	private BufferedImage thresholdingWithThreshold(int threshold) {
		System.out.println(threshold);
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = image.getRGB(x, y);
				int gray = Math.abs(rgb >> 16);
				// System.out.println(gray);
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
	 * Testing method
	 * 
	 * @param args
	 *            system parameters
	 */
	public static void main(String[] args) {
		File input;
//		input = new File("E://case//filter//AverageMean.jpg");
//		 input = new File("E://case//filter//AverageMedian.jpg");
//		 input = new File("E://case//filter//MaxMean.jpg");
//		 input = new File("E://case//filter//MaxMedian.jpg");
//		 input = new File("E://case//filter//RedMean.jpg");
//		 input = new File("E://case//filter//RedMedian.jpg");
//		 input = new File("E://case//filter//WeightedMean.jpg");
//		 input = new File("E://case//filter//WeightedMedian.jpg");
		 input = new File("E://GrayWithMax.jpg");
		// input = new File("E://GrayWithAverage.jpg");
		// input = new File("E://GrayWithComponent.jpg");
		// input = new File("E://GrayWithWeightedMean.jpg");
		Thresholding b = new Thresholding(input);
		int ostu = b.ostu();
		System.out.println(ostu);
		b.thresholdingWithSampleThreshold(new File(
				"E://output//thresholdingWithSampleThreshold.jpg"));
		b.thresholdingWithAverage(new File("E://output//thresholdingWithAverage.jpg"));
		b.thresholdingWithHistogram(new File(
				"E://output//thresholdingWithHistogram.jpg"));
	}

}
