package MazeProgram;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        System.out.println(mazeImg.getWidth());
        System.out.println(mazeImg.getHeight());

    }
}
