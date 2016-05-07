package cn.brainit.image.core;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class Image extends BufferedImage {

	int width;
	int height;
	File input;
	File output;
	int[][] pixel;

	public Image(ColorModel cm, WritableRaster raster,
			boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
		super(cm, raster, isRasterPremultiplied, properties);
	}

	public Image(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	public Image(int width, int height, int imageType, IndexColorModel cm) {
		super(width, height, imageType, cm);
	}

	public static Image readFile(File input) {
		BufferedImage result;
		try {
			result = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to read file");
		}
		return null;
	}

	public void write() {
		write(output);
	}

	public void write(File output) {

	}

}
