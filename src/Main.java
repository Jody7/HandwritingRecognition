import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        VERY CRUDE!!!!!!!!!!!!!!!!!!!!!!!1

        Takes 2 images and divides them into chunks by Rows and Columns,
        then compares Chunks by seeing if they have Dark Pixels or not.
        Also compares Light Pixels for Extra Accuracy.

        Is pretty in-accurate. If I were to spend more time,
        I could try implementing a neural network and train it.
         */

        Scan scan = new Scan();

        File file = new File("k.png");
        File file2 = new File("j.png");
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis); //reading the image file
        FileInputStream fis2 = new FileInputStream(file2);
        BufferedImage image2 = ImageIO.read(fis2); //reading the image file

        int rows = 10;
        int cols = 10;
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols;

        int chunkHeight = image.getHeight() / rows;

        int count1 = 0;
        int count2 = 0;

        BufferedImage imgs[] = new BufferedImage[chunks];
        BufferedImage imgs2[] = new BufferedImage[chunks];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                imgs[count1] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                Graphics2D gr = imgs[count1++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);

                imgs2[count2] = new BufferedImage(chunkWidth, chunkHeight, image2.getType());
                Graphics2D gr2 = imgs2[count2++].createGraphics();
                gr2.drawImage(image2, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);


                gr.dispose();
                gr2.dispose();
            }
        }
        System.out.println("Splitting done for both images");

        int[] res = scan.scan(imgs, chunks, 200, false);
        int[] res2 = scan.scan(imgs2, chunks, 200, false);


        int[] resNEG = scan.scan(imgs, chunks, 15, true);
        int[] resNEG2 = scan.scan(imgs2, chunks, 15, true);

        int Total = 0;
        int TotalA = 0;
        int TotalB = 0;
        int a = 0;
        int b = 0;

        for (int i=0; i < chunks; i++){
            if(res[i] > 0 && res2[i] > 0){
                Total++;
            }

            if(res[i] > 0){
                TotalA++;
            }
            if(res2[i] > 0){
                TotalB++;
            }

            a = a + resNEG[i];
            b = b + resNEG2[i];
            //get Light Pixel Counts

        }

        System.out.println(Total + "..." + TotalA + ":" + TotalB + " ---- " + Math.abs((a-b)));
        System.out.println("\n\nPercent Quads With Dark Pixels: " + ( (Total * 10) / ((TotalA + TotalB)/2) * 10) + "% --- \n BONUS ACC (Should Be Under 500-600) Lower = More Sim: " + Math.abs((a-b)));

    }
}  