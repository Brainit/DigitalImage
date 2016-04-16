package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		return result;
	}

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
		System.out.println(gray);
		return gray;
	}

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
				if (getGray(x - 1 + i, y - 1 + j) > 0) {
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
