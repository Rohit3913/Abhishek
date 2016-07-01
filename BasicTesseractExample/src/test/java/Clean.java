import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class Clean {

	
	
	
	//SetGrayscale
	  public Bitmap SetGrayscale(Bitmap img)
	        {

	            Bitmap temp = (Bitmap)img;
	            Bitmap bmap = (Bitmap)temp.Clone();
	            Color c;
	            for (int i = 0; i < bmap.Width; i++)
	            {
	                for (int j = 0; j < bmap.Height; j++)
	                {
	                    c = bmap.GetPixel(i, j);
	                    byte gray = (byte)(.299 * c.R + .587 * c.G + .114 * c.B);

	                    bmap.SetPixel(i, j, Color.FromArgb(gray, gray, gray));
	                }
	            }
	            return (Bitmap)bmap.Clone();

	        }
	//RemoveNoise
	   public Bitmap RemoveNoise(Bitmap bmap)
	        {

	            for (var x = 0; x < bmap.Width; x++)
	            {
	                for (var y = 0; y < bmap.Height; y++)
	                {
	                    var pixel = bmap.GetPixel(x, y);
	                    if (pixel.R < 162 && pixel.G < 162 && pixel.B < 162)
	                        bmap.SetPixel(x, y, Color.Black);
	                }
	            }

	            for (var x = 0; x < bmap.Width; x++)
	            {
	                for (var y = 0; y < bmap.Height; y++)
	                {
	                    var pixel = bmap.GetPixel(x, y);
	                    if (pixel.R > 162 && pixel.G > 162 && pixel.B > 162)
	                        bmap.SetPixel(x, y, Color.White);
	                }
	            }

	            return bmap;
	        }
}

public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
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
