import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;


public class CleanImage {
	
	public BufferedImage gaussianNoise(BufferedImage image, BufferedImage output) {
		double stdDev = 10.0;
        Raster source = image.getRaster();
        WritableRaster out = output.getRaster();
          
        int currVal;                    // the current value
        double newVal;                  // the new "noisy" value
        double gaussian;                // gaussian number
        int bands  = out.getNumBands(); // number of bands
        int width  = image.getWidth();  // width of the image
        int height = image.getHeight(); // height of the image
        java.util.Random randGen = new java.util.Random();
          
        for (int j=0; j<height; j++) {
            for (int i=0; i<width; i++) {
                gaussian = randGen.nextGaussian();
                  
                for (int b=0; b<bands; b++) {
                    newVal = stdDev * gaussian;
                    currVal = source.getSample(i, j, b);
                    newVal = newVal + currVal;
                    if (newVal < 0)   newVal = 0.0;
                    if (newVal > 255) newVal = 255.0;
                      
                    out.setSample(i, j, b, (int)(newVal));
                }
            }
        }
          
        return output;
    }
	
	
	public BufferedImage impulseNoise(BufferedImage image, BufferedImage output) {
		  double  impulseRatio = 0.05;
	        output.setData(image.getData());
	          
	        Raster source = image.getRaster();
	        WritableRaster out = output.getRaster();
	          
	        double rand;
	        double halfImpulseRatio = impulseRatio / 2.0;
	        int bands  = out.getNumBands();
	        int width  = image.getWidth();  // width of the image
	        int height = image.getHeight(); // height of the image
	        java.util.Random randGen = new java.util.Random();
	          
	        for (int j=0; j<height; j++) {
	            for (int i=0; i<width; i++) {
	                rand = randGen.nextDouble();
	                if (rand < halfImpulseRatio) {
	                    for (int b=0; b<bands; b++) out.setSample(i, j, b, 0);
	                } else if (rand < impulseRatio) {
	                    for (int b=0; b<bands; b++) out.setSample(i, j, b, 255);
	                }
	            }
	        }
	          
	        return output;
	    }
	
	
	public  BufferedImage thresholdImage(BufferedImage image, int threshold) {
	    BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	    result.getGraphics().drawImage(image, 0, 0, null);
	    WritableRaster raster = result.getRaster();
	    int[] pixels = new int[image.getWidth()];
	    for (int y = 0; y < image.getHeight(); y++) {
	        raster.getPixels(0, y, image.getWidth(), 1, pixels);
	        for (int i = 0; i < pixels.length; i++) {
	            if (pixels[i] < threshold) pixels[i] = 0;
	            else pixels[i] = 255;
	        }
	        raster.setPixels(0, y, image.getWidth(), 1, pixels);
	    }
	    return result;
	}
	

}
