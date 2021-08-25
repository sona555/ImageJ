import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import ij.gui.*;


public class YCbCr_Transformer implements PlugInFilter{
    private ImagePlus  imp;               // Original image
    private ImageStack sstack;            // Stack result
    private String     title;             // Name of the original image
    private int        M;             // Width of the original image
    private int        N;            // Height of the original image
    private int        total;              // Total number of pixels
    private float[]    rf, gf, bf;        // r, g, b values
    private float[]    c1, c2, c3;        // Color space values
    private String     n1, n2, n3;   	 // Names for every layer on the stack
    private boolean    toRGBcolor = true; // Convert to RGB color (8 bits per channel)
    private boolean    full16 = true;     // Display the full 16-bit range (0-65535)

  
    public int setup(String arg, ImagePlus imp){
        this.imp = imp;
  
        return DOES_ALL;
    }
  
   
  
    public void run(ImageProcessor ip) {
        
        int offset, i;
        M = ip.getWidth();
        N = ip.getHeight();
        total = M*N;
        rf = new float[total];
        gf = new float[total];
        bf = new float[total];
        c1 = new float[total];
        c2 = new float[total];
        c3 = new float[total];

        if ((imp.getBitDepth() == 16) && full16) {
          imp.setSlice(3);
          IJ.setMinAndMax(imp, 0, 65535);
          imp.setSlice(2);
          IJ.setMinAndMax(imp, 0, 65535);
          imp.setSlice(1);
          IJ.setMinAndMax(imp, 0, 65535); }

        if ((imp.getBitDepth() != 24) && toRGBcolor) {
          imp.unlock();
          IJ.run(imp, "RGB Color", "");
          imp.lock();
          ImagePlus imp2 = WindowManager.getCurrentImage();
          ip = imp2.getProcessor(); }


        for (int row = 0; row < N; row++){
            offset = row*M;
            for (int col = 0; col < M; col++) {
                i = offset + col;
                int c = ip.getPixel(col, row);
                rf[i] = ((c&0xff0000)>>16)/255f;    //R 0..1
                gf[i] = ((c&0x00ff00)>>8)/255f;     //G 0..1
                bf[i] =  (c&0x0000ff)/255f;         //B 0..1
            }
        }

        title = imp.getTitle().replace(" (RGB)", " (YCbCr) ");
        sstack=new ImageStack(M,N); 

            n1 = "Y";
            n2 = "Cb";
            n3 = "Cr";              
            getYCbCr();
       

	if (c1.length == total) {

          ImagePlus imc1=NewImage.createFloatImage(n1,M,N,1,NewImage.FILL_BLACK);
          ImageProcessor ipc1=imc1.getProcessor();
          ipc1.setPixels(c1);
          sstack.addSlice(n1,ipc1);

          ImagePlus imc2=NewImage.createFloatImage(n2,M,N,1,NewImage.FILL_BLACK);
          ImageProcessor ipc2=imc2.getProcessor();
          ipc2.setPixels(c2);
          sstack.addSlice(n2,ipc2);

          ImagePlus imc3=NewImage.createFloatImage(n3,M,N,1,NewImage.FILL_BLACK);
          ImageProcessor ipc3=imc3.getProcessor();
          ipc3.setPixels(c3);
          sstack.addSlice(n3,ipc3);


        ImagePlus imluv=new ImagePlus(title + "(YCbCr)", sstack);
        imluv.show();
        IJ.resetMinAndMax();
	}

    }

    public void getYCbCr(){
        for(int q=0; q<total; q++){

            rf[q] *= 255;
            gf[q] *= 255;
            bf[q] *= 255;

            float Y =  0.299f * rf[q] + 0.587f * gf[q] + 0.114f * bf[q];
            float Cb = -0.169f * rf[q] - 0.331f * gf[q] + 0.500f * bf[q] + 128;
            float Cr =  0.500f * rf[q] - 0.419f * gf[q] - 0.081f * bf[q] + 128;
            
            c1[q] = Y;
            c2[q] = Cb;
            c3[q] = Cr;    
        }        
    } 

}
