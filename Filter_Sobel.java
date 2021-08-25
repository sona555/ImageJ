import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
public class Filter_Sobel implements PlugInFilter {
  ImagePlus im = null;

  public int setup(String arg, ImagePlus imp) {
    im = imp;
    return DOES_8G + PlugInFilter.NO_CHANGES;
  }

  public void run(ImageProcessor ip) {


	  
    // create the new image processor Objects
    FloatProcessor Ix = (ip.duplicate()).convertToFloatProcessor();
    FloatProcessor Iy = (FloatProcessor) Ix.duplicate();

    
    // apply the linear filter
    Ix.convolve3x3(new int[] {-1, 0, 1, -2, 0, 2, -1, 0, 1});
    Iy.convolve3x3(new int[] {-1, -2, -1, 0, 0, 0, 1, 2, 1});

    // display the new images Ix, Iy.
    (new ImagePlus("Ix", Ix)).show();
    (new ImagePlus("Iy", Iy)).show();

    // compute and display E (gradient magnitude).
    // Use the functions of class ImageProcessor sqr, sqrt and copybits to operate
    // on the images.

    FloatProcessor Ix2 = (FloatProcessor) Ix.duplicate();
    FloatProcessor Iy2 = (FloatProcessor) Iy.duplicate();
    
    Ix2.sqr();
    Iy2.sqr();
   
    FloatProcessor E = (FloatProcessor) Ix2.duplicate();
    E.copyBits(Iy2, 0, 0, Blitter.ADD);
    E.sqrt();

    (new ImagePlus("E", E)).show();

    // compute and display Phi.

    FloatProcessor phi = (ip.duplicate()).convertToFloatProcessor();
    phi = generatePhi(phi, Ix, Iy);
    
    (new ImagePlus("phi", phi)).show();
  }

  private FloatProcessor generatePhi(FloatProcessor phi, FloatProcessor ix, FloatProcessor iy) {

    iy.copyBits(ix, 0, 0, Blitter.DIVIDE);

    for (int i = 0; i < iy.getWidth(); i++) {

      for (int j = 0; j < iy.getHeight(); j++) {

        int currPx = iy.getPixel(i, j);
        int atanCalc = (int) (Math.atan(currPx));

        iy.putPixel(i, j, atanCalc);
      }
    }

    phi.copyBits(iy, 0, 0, Blitter.COPY);

    return phi;
  }

}