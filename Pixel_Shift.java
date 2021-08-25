import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Pixel_Shift implements PlugInFilter {
	ImagePlus im; 
	public int setup(String args, ImagePlus im) {
		this.im = im; 
		return DOES_8G;	// this plugin accepts 8-bit grayscale images
	}

	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight(); 
		int i=0,j =0;

		// iterate over all image coordinates (u,v)
			int u=i;
			for (int v=j; v < N; v++) {
				int p = ip.getPixel(u, v);
				int tmp = ip.getPixel(M-u-1, v);
				ip.putPixel(u,v,tmp);
				ip.putPixel(M-u-1, v, p);
			}
			i++;
			j++;
		im.updateAndDraw();
	}
	
}
		