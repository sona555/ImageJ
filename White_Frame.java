import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class White_Frame implements PlugInFilter {
	
public int setup(String args, ImagePlus im) {
	return DOES_8G;	// this plugin accepts 8-bit grayscale images
}

public void run(ImageProcessor ip) {
	int M = ip.getWidth();
	int N = ip.getHeight(); 
	// iterate over all image coordinates (u,v)
	for (int u = 0; u < M; u++) {
		for (int v = 0; v < N; v++) {
			if(v < 10 || v > N -10) {
				ip.putPixel(u, v, 255);
			}
			else if (u < 10 || u > M -10){
				ip.putPixel(u, v, 255);
			}
			
		}
	}
}
		
}