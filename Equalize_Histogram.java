import java.util.HashMap;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Equalize_Histogram implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();
		int K = 256; // number of intensity values

		// compute the cumulative histogram:
		int[] H = ip.getHistogram();
		HashMap<Integer, Integer> histogram = new HashMap<Integer,Integer>();
		
		for (int j = 1; j < H.length; j++) {
			H[j] = H[j - 1] + H[j];
		}

		// equalize the image:
		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				int a = ip.get(u, v);
				
				if(histogram.containsKey(a)) {
					
					ip.set(u, v, histogram.get(a));
				} else {
				int b = H[a] * (K - 1) / (M * N);
				ip.set(u, v, b);
				histogram.put(a,b);
				}
			}
		}
	}
	
}
