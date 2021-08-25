import java.awt.Rectangle;

import ij.*;
import ij.plugin.filter.*;
import ij.process.ImageProcessor;

public class Flip_Horizontaly implements PlugInFilter {

	public int setup(String args, ImagePlus im) {
		return DOES_8G;	// this plugin accepts 8-bit grayscale images
	}

	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight(); 

		// iterate over all image coordinates (u,v)
		for (int u = 0; u < M/2; u++) {
			for (int v = 0; v < N; v++) {
				int p = ip.getPixel(M-1-u, v);
				int tmp = ip.getPixel(u, v);
				ip.putPixel(M-1-u,v,tmp);
				ip.putPixel(u, v, p);
			}
		}
	}
			
}