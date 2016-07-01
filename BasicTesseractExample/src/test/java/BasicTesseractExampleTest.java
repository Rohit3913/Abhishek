import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import java.io.File;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.*;
import org.junit.Test;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;
import static org.junit.Assert.assertTrue;



public class BasicTesseractExampleTest {
	String whiteList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzXYZ1234567890";
	
	
    @Test
    public void givenTessBaseApi_whenImageOcrd_thenTextDisplayed() throws Exception {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        api.SetVariable("classify_bln_numeric_mode", "1"); 
        if (api.Init(".", "ENG") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

       
        
        
        int threshold = 128;
        	
            BufferedImage image = ImageIO.read(new File("5.PNG"));
            
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
            
            
            
           
         CleanImage cl = new CleanImage();
            
          //  result = cl.gaussianNoise(result, result);
         //   result = cl.thresholdImage(result,255);
         //result = cl.impulseNoise(result, result);
           
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
          
            File outputfile = new File("saved.png");
            ImageIO.write(result, "png", outputfile);
        
        
        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        
        
         // PIX image1 = pixRead(result);
          PIX image1 = pixRead("saved.png");
        // Open input image with leptonica library
      
        api.SetImage(image1);
        // Get OCR result
        outText = api.GetUTF8Text();
        String string = outText.getString();
        assertTrue(!string.isEmpty());
        System.out.println("OCR output:\n" + string);

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image1);
    }
}