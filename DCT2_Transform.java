import java.awt.Rectangle;
import java.util.Arrays;

import ij.*;
	import ij.plugin.filter.*;
	import ij.process.ImageProcessor;

public class DCT2_Transform implements PlugInFilter {
		
	public int setup(String args, ImagePlus im) {
		return DOES_8G;	// this plugin accepts 8-bit grayscale images
	}

	public void run(ImageProcessor ip) {

		double[][]	in = new double[8][8];
		// iterate over all image coordinates (u,v)
		for (int u = 0; u < 8; u++) {
			for (int v=0; v < 8; v++) {
				in[u][v] = ip.getPixel(u, v);
			}
		}

		int	x,y,u,v;
		

		double[][] norm=	{
			{5,3,4,4,4,3,5,4},
			{4,4,5,5,5,6,7,12},
			{8,7,7,7,7,15,11,11},
			{9,12,13,15,18,18,17,15},
			{20,20,20,20,20,20,20,20},
			{20,20,20,20,20,20,20,20},
			{20,20,20,20,20,20,20,20},
			{20,20,20,20,20,20,20,20}};

		double[][]	out = new double[8][8];
		double	sum,Cu,Cv;

			for (u=0;u<8;u++)
			{
				for (v=0;v<8;v++)
				{
					sum=0;
					for (x=0;x<8;x++)
						for (y=0;y<8;y++)
						{
							sum=sum+in[x][y]*Math.cos(((2.0*x+1)*u*Math.PI)/16.0)*
								Math.cos(((2.0*y+1)*v*Math.PI)/16.0);
						}
					if (u==0) Cu=1/Math.sqrt(2); else Cu=1;
					if (v==0) Cv=1/Math.sqrt(2); else Cv=1;

					out[u][v]=1/4.0*Cu*Cv*sum/norm[u][v];
					out[u][v] = (int)out[u][v];
				//	System.out.println((int)out[u][v]);
				}
			//	System.out.println("\n");
			}

			//System.out.println("\n");
			StringBuilder sb = new StringBuilder();
			for(double[] s1 : out){
					 sb.append(Arrays.toString(s1)).append('\n');
			}
			String s = sb.toString();
		IJ.showMessage("DCTII und Quantization",s);
	}
			
}