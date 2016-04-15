package cn.brainit.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
