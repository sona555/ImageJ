import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Cumulative_Histogram implements PlugInFilter {

  ImagePlus im;

  public int setup(String arg, ImagePlus img) {
    this.im = img;
    return DOES_8G + NO_CHANGES;
  }

  @Override
  public void run(ImageProcessor ip) {

    int[] H = new int[256]; // histogram array
    int[] cumulativeHistogram = new int[256];
    int K = H.length;
    // int width = ip.getWidth();
    // int height = ip.getHeight();

    // H = generateHistogram(ip, H, w, h);
    H = ip.getHistogram();
    cumulativeHistogram = generateCumulativeHistogram(H, cumulativeHistogram);

    for (int i = 1; i < H.length; i++) {
      // H[i] += H[i - 1];
    }

    // create the histogram image:
    ImageProcessor histIp = new ByteProcessor(K, 100);
    histIp.setValue(255); // white = 255
    histIp.fill();
    histIp.setValue(0);

    for (int i = 0; i < cumulativeHistogram.length; i++) {
      histIp.drawLine(i, 99, i, 100 - cumulativeHistogram[i] * 100 / cumulativeHistogram[K - 1]);
    }

    for (int i = 0; i < H.length; i++) {
      // histIp.drawLine(i, 99, i, 100 - H[i] * 100 / (width * height));
    }

    // compose a nice title:
    String imTitle = im.getShortTitle();
    String histTitle = "Histogram of " + imTitle;

    // display the histogram image:
    ImagePlus histIm = new ImagePlus(histTitle, histIp);
    histIm.show();
  }

  private int[] generateCumulativeHistogram(int[] H, int[] cumulativeHistogram) {

    for (int i = 0; i < cumulativeHistogram.length; i++) {
      if (i == 0) {
        cumulativeHistogram[i] = H[i];
      } else {
        cumulativeHistogram[i] += H[i] + cumulativeHistogram[i - 1];
      }
    }

    return cumulativeHistogram;
  }

  public int[] generateHistogram(ImageProcessor ip, int[] H, int w, int h) {

    for (int v = 0; v < h; v++) {
      for (int u = 0; u < w; u++) {
        int i = ip.getPixel(u, v);
        H[i] = H[i] + 1;
      }
    }

    return H;
  }
}