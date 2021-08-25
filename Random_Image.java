	import ij.*;
	import ij.plugin.filter.*;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
	
public class Random_Image implements PlugInFilter{

	ImagePlus im;
	
	public int setup(String arg, ImagePlus im){
		this.im = im;
		return DOES_8G + NO_CHANGES;
	}
	
	public void run(ImageProcessor ip){
		
		// create new 8-bit image (new ImageProcessor object needed)
		// and fill with white

		ImageProcessor newip = new ByteProcessor(ip.getWidth(), ip.getHeight());
		newip.setValue(255); 
		newip.fill();
		
		int M = ip.getWidth();
		int N = ip.getHeight(); 
		int max = 255;
	    int min = 0;
	    int range = max - min + 1;
	    
		// iterate over all image coordinates (u,v)
		for (int u = 0; u < M; u++) {
			for (int v = 0; v < N; v++) {
				
				int rand = (int)(Math.random() * range) + min;
				newip.putPixel(u, v, rand);
			}
		}
		
		// give the new image a title
		String imTitle = im.getShortTitle();
		String newTitle = "Empty  " + imTitle;
		
		// display the new image (new ImagePlus object needed).
		ImagePlus newim = new ImagePlus(newTitle, newip);
		newim.show();
		
	}

}
