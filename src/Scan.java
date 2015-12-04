import java.awt.*;
import java.awt.image.BufferedImage;

public class Scan {

    public int[] scan(BufferedImage[] a, int chunks, int lums, boolean neg) {

        int[] results = new int[chunks];

        int ind = 0;



        for (BufferedImage i : a) {
            //Scan all images, and return array of pixel count for each image

            int total = 0;
            for (int b = 0; b < i.getHeight(); b++) {
                for (int c = 0; c < i.getWidth(); c++) {
                    Color color = new Color(i.getRGB(c, b));
                    double Y = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
                    //System.out.println(Y);

                    if (neg) {
                        if (Y > lums) {
                            // Look for LIGHT pixels
                            total++;
                        }

                    }
                    else {
                        if(Y < lums){
                            //Look for DARK pixels
                            total++;
                        }

                    }

                }
            }

            results[ind] = total;
            ind++;
        }


        return results;
    }
}
