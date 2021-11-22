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
        File mazeFile = new File("img/testmaze.png");
        try {
            mazeImg = ImageIO.read(mazeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = mazeImg.getWidth();
        int height = mazeImg.getHeight();
        boolean visited;
        boolean visitedTwice;
        byte[] pixels = ((DataBufferByte) mazeImg.getRaster().getDataBuffer()).getData();
        Solver solver = new Solver(29,0,pixels,width,height);

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
