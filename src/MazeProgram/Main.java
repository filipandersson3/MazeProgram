package MazeProgram;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        BufferedImage mazeImg = null;
        File mazeFile = new File("img/maze1.png");
        try {
            mazeImg = ImageIO.read(mazeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = mazeImg.getWidth();
        int height = mazeImg.getHeight();
        byte[] pixels = ((DataBufferByte) mazeImg.getRaster().getDataBuffer()).getData();
        System.out.println(mazeImg.getWidth());
        System.out.println(mazeImg.getHeight());
        for (int i = 0; i < width*height; i++) {
            if (pixels[i] == -1) {
                System.out.print(" ");
            }
            if (pixels[i] == 0) {
                System.out.print("█");
            }
            if ((i+1)%(width) == 0) {
                System.out.println("");
            }
        }


    }
}
