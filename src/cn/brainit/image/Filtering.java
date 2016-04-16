package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

/**
 * Image Filtering
 * 
 * you may see more at
 * 
 * http://www.cnblogs.com/libing64/archive/2011/12/14/2878737.html
 * 
 * http://lodev.org/cgtutor/filtering.html
 * 
 * @author brainit brainit
 * 
 *         http://www.brainit.cn/
 * 
 */
public class Filtering {

	/**
	 * Source Image data
	 */
	BufferedImage image;

	/**
	 * constructor of Class Filtering with ImageFile
	 * 
	 * @param input
	 *            input Image File
	 */
	public Filtering(File input) {
		try {
			this.image = ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("Fail to read input Image");
			e.printStackTrace();
		}
	}

	/**
	 * constructor of class Filtering with BufferedImage
	 * 
	 * @param image
	 *            BufferedImage
	 */
	public Filtering(BufferedImage image) {
		this.image = image;
	}

	/**
	 * filtering the image with mean of RGB
	 * 
	 * gray = (g1 , g2 .... gn)
	 * 
	 * mean = (g1 + g2 + ... + gn)/n
	 * 
	 * @return the Image finished filtering
	 */
	public BufferedImage filteringWithMean() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// boundary or not
				if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
					result.setRGB(i, j, image.getRGB(i, j));
				} else {
					int gray = (getGray(i - 1, j - 1) + getGray(i, j - 1)
							+ getGray(i + 1, j - 1) + getGray(i - 1, j)
							+ getGray(i, j) + getGray(i + 1, j)
							+ getGray(i - 1, j + 1) + getGray(i, j + 1) + getGray(
							i + 1, j + 1)) / 9;
					int newRGB = (gray << 24) | (gray << 16) | (gray << 8)
							| gray;
					result.setRGB(i, j, newRGB);
				}
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get BufferedImage after filtering witht mean
	 * 
	 * output the bufferedImage to file
	 * 
	 * more info you may see filteringWithMean()
	 * 
	 * @param output
	 *            destFile
	 */
	public void filteringWithMean(File output) {
		BufferedImage result = filteringWithMean();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fial to write BufferedImage to File");
		}
	}

	/**
	 * filtering the image with median of neighbor
	 * 
	 * gray = (g1 , g2 .... gn)
	 * 
	 * neighbor(i,j) = {gray(i-1,j-1),gray(i,j-1),gray(i+1,j-1);
	 * 
	 * gray(i-1,j),gray(i,j),gray(i+1,j);
	 * 
	 * gray(i-1,j+1),gray(i,j+1),gray(i+1,j+1);}
	 * 
	 * gray(i,j) = median(neighbor(i,j))
	 * 
	 * tips:the border will be kept with no change
	 * 
	 * @return the Image finished filtering with median
	 */
	public BufferedImage filteringWithMedian() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// boundary or not
				if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
					result.setRGB(i, j, image.getRGB(i, j));
				} else {
					ArrayList<Integer> data = new ArrayList<Integer>();
					for (int m = 0; m < 3; m++) {
						for (int n = 0; n < 3; n++) {
							data.add(getGray(i - 1 + m, j - 1 + n));
						}
					}
					Collections.sort(data);
					int gray = data.get(4);
					int newRGB = (gray << 24) | (gray << 16) | (gray << 8)
							| gray;
					result.setRGB(i, j, newRGB);
				}
			}
		}
		return result;
	}

	/**
	 * output data to file
	 * 
	 * get BufferedImage after filtering with median of neighbor
	 * 
	 * output the bufferedImage to file
	 * 
	 * more info you may see filteringWithMedian()
	 * 
	 * @param output
	 *            destFile
	 */
	public void filteringWithMedian(File output) {
		BufferedImage result = filteringWithMedian();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fial to write BufferedImage to File");
		}
	}

	/**
	 * regarding the image as Gray Image
	 * 
	 * get the gray level of target location
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return the gray level of target location
	 */
	public int getGray(int x, int y) {
		int width = image.getWidth();
		int height = image.getHeight();
		/*
		 * check the point existing
		 * 
		 * if not ,return -1
		 */
		if (x < 0 || y < 0 || x > width || y > height) {
			return -1;
		}
		int rgb = image.getRGB(x, y);
		int gray = rgb >> 16 & 0xff;
		System.out.println(gray);
		return gray;
	}

	/**
	 * Testing method
	 * 
	 * @param args
	 *            system parameters
	 */
	public static void main(String[] args) {
		File input;
		// input = new File("E://GrayWithMax.jpg");
		// input = new File("E://GrayWithAverage.jpg");
		input = new File("E://GrayWithComponent.jpg");
		// input = new File("E://GrayWithWeightedMean.jpg");
		Filtering f = new Filtering(input);
		File output1 = new File("E://FilteringWithMean.jpg");
		File output2 = new File("E://FilteringWithMedian.jpg");
		f.filteringWithMean(output1);
		f.filteringWithMedian(output2);
	}
}
