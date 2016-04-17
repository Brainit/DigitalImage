package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Image Thinning With Hilditch
 * 
 * Hilditch's Algorithm for Skeletonization
 * 
 * more info
 * :http://cgm.cs.mcgill.ca/~godfried/teaching/projects97/azar/skeleton.html
 * 
 * 
 * 
 * P9 P2 P3
 * 
 * P8 P1 P4
 * 
 * P7 P6 P5
 * 
 * 2 < = B(p1) < = 6
 * 
 * A(p1)=1
 * 
 * p2.p4.p8=0 or A(p2)!= 1
 * 
 * p2.p4.p6=0 or A(p4)!= 1
 * 
 * @author brainit brainit
 * 
 *         http://www.brainit.cn/
 * 
 */
public class Thinning {

	public BufferedImage image;

	public Thinning(File input) {
		try {
			this.image = ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("Fail to load the image file");
			e.printStackTrace();
		}
	}

	public Thinning(BufferedImage input) {
		this.image = image;
	}

	public BufferedImage thinWithHilditch() {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (getGray(i, j) != 0) {
					result.setRGB(i, j, image.getRGB(i, j));
					continue;
				}

				// border or not
				if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
					result.setRGB(i, j, image.getRGB(i, j));
					continue;
				}
				boolean check1 = false, check2 = false, check3 = false, check4 = false;
				/*
				 * first check part
				 * 
				 * 2 < = B(p1) < = 6
				 */
				if (2 <= B(i, j) && B(i, j) <= 6) {
					check1 = true;
				}
				/*
				 * second check part
				 * 
				 * A(p1)=1
				 */
				if (A(i, j) == 1) {
					check2 = true;
				}
				/*
				 * third check part
				 * 
				 * p2.p4.p8=0 or A(p2)!= 1
				 */
				if (getGray(i - 1, j) != 0 && getGray(i + 1, j) != 0
						&& getGray(i, j - 1) != 0) {
					if (j > 1)
						if (A(i, j - 1) != 1)
							check3 = true;

				}
				/*
				 * forth check part
				 * 
				 * p2.p4.p6=0 or A(p4)!= 1
				 */
				if (getGray(i, j - 1) != 0 && getGray(i + 1, j) != 0
						&& getGray(i, j + 1) != 0) {
					if (i < width - 2)
						if (A(i + 1, j) != 1)
							check4 = true;
				}
				/*
				 * all checked
				 */
				int gray = 0;
				if (check1 & check2 & check3 & check4) {
					gray = 255;
				} else {
					gray = 0;
				}
				int newRGB = (gray << 24) | (gray << 16) | (gray << 8) | gray;
				result.setRGB(i, j, newRGB);
			}
		}
		return result;
	}

	public void thinWithHilditch(File output) {
		BufferedImage result = thinWithHilditch();
		try {
			ImageIO.write(result, "png", output);
			System.out.println("successfully output");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fial to write BufferedImage to File");
		}
	}

	/**
	 * get the gray of target point
	 * 
	 * @param x
	 *            the horizontal of location
	 * @param y
	 *            the vertical of location
	 * @return return the gray of target point
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
		int gray = rgb >> 16 & 0xff; // get the red as gray
		// System.out.println(gray);
		return gray;
	}

	/**
	 * B(p1) = number of non-zero neighbors of p1
	 * 
	 * @param x
	 *            the horizontal of location
	 * @param y
	 *            the vertical to location
	 * @return the number of non-zero neighbors of p1
	 */
	public int B(int x, int y) {
		int width = image.getWidth();
		int height = image.getHeight();
		/**
		 * check the point existing
		 * 
		 * if not ,return -1
		 * 
		 * conditional Code -1:point not existing
		 */
		if (x < 0 || y < 0 || x > width || y > height) {
			return -1;
		}
		/**
		 * check the point locating at the border of image
		 * 
		 * if so ,return -2
		 * 
		 * conditional Code -2 :point locating at the border of image
		 */
		if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
			return -2;
		}
		int result = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i != 1 && j != 1)
					if (getGray(x - 1 + i, y - 1 + j) == 0) {
						result++;
					}
			}
		}
		return result;
	}

	public int A(int x, int y) {
		int width = image.getWidth();
		int height = image.getHeight();
		/**
		 * check the point existing
		 * 
		 * if not ,return -1
		 * 
		 * conditional Code -1:point not existing
		 */
		if (x < 0 || y < 0 || x > width || y > height) {
			return -1;
		}
		/**
		 * check the point locating at the border of image
		 * 
		 * if so ,return -2
		 * 
		 * conditional Code -2 :point locating at the border of image
		 */
		if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
			return -2;
		}
		int result = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(getGray(x - 1, y - 1));
		list.add(getGray(x, y - 1));
		list.add(getGray(x + 1, y - 1));
		list.add(getGray(x + 1, y));
		list.add(getGray(x + 1, y + 1));
		list.add(getGray(x, y + 1));
		list.add(getGray(x - 1, y + 1));
		list.add(getGray(x - 1, y));
		list.add(getGray(x - 1, y - 1));
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) == 255) {
				if (list.get(i + 1) == 0) {
					result++;
				}
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
		// input = new File("E://1.jpg");
		input = new File("E://thresholdingWithAverage.jpg");
		input = new File("E://thresholdingWithSampleThreshold.jpg");
		Thinning t = new Thinning(input);
		t.thinWithHilditch(new File("E://thinWithHilditch.jpg"));
	}

}
