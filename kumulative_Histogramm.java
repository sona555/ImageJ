import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/**
 * This plugin demonstrates how to create and display a new byte image.
 */
public class kumulative_Histogramm implements PlugInFilter {

  ImagePlus im;

  public int setup(String arg, ImagePlus im) {
    this.im = im;
    return DOES_8G + NO_CHANGES;
  }

  public void run(ImageProcessor ip) {

    int w = ip.getWidth();
    int h = ip.getHeight();

    // obtain the histogram of ip:
    int[] hist = ip.getHistogram();
    int K = hist.length;

    // cummulative histogram
    for (int i = 1; i < K; i++) {
      hist[i] += hist[i - 1];
    }

    // create the histogram image:
    ImageProcessor histIp = new ByteProcessor(K, 100);
    histIp.setValue(255); // white = 255
    histIp.fill();
    histIp.setValue(0);

    /*for (int u = 0; u < K; u++) {
      for (int v = 0; v < 100; v++) {
        histIp.putPixel(u, 100 - v, 0);
      }
    }*/
    for (int i = 0; i < K; i++) {
      histIp.drawLine(i, 99, i, 100 - hist[i] * 100 / hist[K - 1]);
    }

    // compose a nice title:
    String imTitle = im.getShortTitle();
    String histTitle = "Histogram of " + imTitle;

    // display the histogram image:
    ImagePlus histIm = new ImagePlus(histTitle, histIp);
    histIm.show();
  }

}
