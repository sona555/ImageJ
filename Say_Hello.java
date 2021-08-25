import java.util.Arrays;

import ij.*;
import ij.plugin.*;
	
public class Say_Hello implements PlugIn{

	public void run(String arg) {
	
		IJ.showMessage("Say Hello", "Hello");
	}
	
	public static void main(String[] args) {
		new ImageJ(); //opens the ImageJ window
	} 
	
}
