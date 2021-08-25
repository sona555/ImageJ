/*******************************************************************************
 * This software is provided as a supplement to the authors' textbooks on digital
 * image processing published by Springer-Verlag in various languages and editions.
 * Permission to use and distribute this software is granted under the BSD 2-Clause 
 * "Simplified" License (see http://opensource.org/licenses/BSD-2-Clause). 
 * Copyright (c) 2006-2015 Wilhelm Burger, Mark J. Burge. 
 * All rights reserved. Visit http://www.imagingbook.com for additional details.
 *  
 *******************************************************************************/


import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;

public class Filter_Arbitrary implements PlugInFilter {

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int M = ip.getWidth();
        int N = ip.getHeight();
        
       float H[] = {1,1,1,1,1,1,1};
        
        
        // H[L][K] is the center element of H:
        int K = H.length / 2;	// H[0].length = width of H
        int L = H.length / 2;		// H.length = height of H
        
        ImageProcessor copy = ip.duplicate();
        ImageProcessor tmp = ip.duplicate();

      
        //along x- direction
        	for (int v = 0; v <= N - 1; v++) {
        		 for (int u = K; u <= M - K - 1; u++) {
                // compute filter result for position (u, v):
                float sum = 0;
                for (int i = -K; i <= K; i++) {
						int p = copy.getPixel(u + i, v);
						float c = H[i + K];
                        sum = sum + c * p;
                }
                int q = (int) (sum);
                // clamp result:
                if (q < 0)   q = 0;
                if (q > 255) q = 255;
                tmp.putPixel(u, v, q);
        	}
        }
        //along y-direction
            for (int u = 0; u < M ; u++) {
        	for (int v = L; v < N - L; v++) {
                // compute filter result for position (u, v):
                float sum = 0;
                	for (int j = -L; j <= L; j++) {
						int p = tmp.getPixel(u, v + j);
						float c = H[j + L];
                        sum = sum + c * p;
                    }
                int q = (int) (sum);
                // clamp result:
                if (q < 0)   q = 0;
                if (q > 255) q = 255;
                ip.putPixel(u, v, q);
        	}
        }
        }
	    
    
    float[] makeGaussKernel1(double sigma) {
    	// create the kernel h:
    	int center = (int) (3.0 * sigma);
    	float[] h = new float[2 * center + 1]; // odd size
    	// fill the kernel h:
    	double sigma2 = sigma * sigma; // σ2
    	for (int i = 0; i < h.length; i++) {
    	 double r = center - i;
    	 h[i] = (float) Math.exp(-0.5 * (r * r) / sigma2);
    	}
    	return h;
    }

}
