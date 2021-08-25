import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Min_Max implements PlugInFilter {
	
public int setup(String args, ImagePlus im) {
	return DOES_8G;	// this plugin accepts 8-bit grayscale images
}

public void run(ImageProcessor ip) {
	int M = ip.getWidth();
	int N = ip.getHeight(); 
	int min = 255;
	int max = 0;
	// iterate over all image coordinates (u,v)
	for (int u = 0; u < M; u++) {
		for (int v = 0; v < N; v++) {
			int p = ip.getPixel(u, v);
			if(p < min) {
				min = ip.getPixel(u,v);
			}
			if(p > max) {
				max = p;
			}
		}		
	}
	IJ.log("Min: "+String.valueOf(min)+"Max: "+String.valueOf(max));
}
		
}