package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Image Filtering
 * 
 * you may see more at
 * 
 * http://www.cnblogs.com/libing64/archive/2011/12/14/2878737.html
 * 
 * @author brainit brainit
 * 
 *         http://www.brainit.cn/
 * 
 */
public class Filtering {

	BufferedImage image;

	public Filtering(File input) {
		try {
			this.image = ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("Fail to read input Image");
			e.printStackTrace();
		}
	}

	public Filtering(BufferedImage image) {
		this.image = image;
	}

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

	public BufferedImage filteringWithMedian() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_GRAY);
		return result;
	}

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
	 * @param y
	 * @return the gray level of target location
	 */
	public int getGray(int x, int y) {
		int rgb = image.getRGB(x, y);
		int gray = Math.abs(rgb >> 16);
		return gray;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
