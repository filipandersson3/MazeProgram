package MazeProgram;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BufferedImage mazeImg = null;
        File mazeFile = new File("img/testmaze.bmp");
        try {
            mazeImg = ImageIO.read(mazeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = mazeImg.getWidth();
        int height = mazeImg.getHeight();
        System.out.println(mazeImg.getRGB(0,29));
        boolean visited;
        boolean visitedTwice;
        byte[] pixels = new byte[width*height];        //((DataBufferByte) mazeImg.getRaster().getDataBuffer()).getData();
        System.out.println(pixels.length);
        for (int j = 0; j < width*height; j++) {
            if (mazeImg.getRGB(j%width,j/width) == -1) {
                pixels[j] = -1;
            } else if (mazeImg.getRGB(j%width,j/width) == -16777216) {
                pixels[j] = 0;
            }
        }
        Solver solver = new Solver(30,0,pixels,width,height);

        for (int i = 0; i < width*height; i++) {
            visited = false;
            visitedTwice = false;
            for (Coordinate c:solver.getVisitedList()) {
                if (c.getX() == i%width && c.getY() == i/width) {
                    visited = true;
                }
            }
            for (Coordinate c:solver.getVisitedTwiceList()) {
                if (c.getX() == i%width && c.getY() == i/width) {
                    visitedTwice = true;
                }
            }
            if (visitedTwice) {
                System.out.print("B)");
            } else if (visited) {
                System.out.print(":D");
            } else {
                if (pixels[i] == -1) {
                    System.out.print("  ");
                }
                if (pixels[i] == 0) {
                    System.out.print("██");
                }
            }


            if ((i+1)%(width) == 0) {
                System.out.println("");
            }
        }


    }
}
