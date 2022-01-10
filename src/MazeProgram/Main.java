package MazeProgram;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BufferedImage mazeImg = null;
        File mazeFile = new File("img/" + args[1] + ".bmp");
        try {
            mazeImg = ImageIO.read(mazeFile);
        } catch (IOException e) {
            if (!args[0].equals("dfs") && !args[0].equals("bfs")) {
                System.out.println("enter either bfs or dfs as the first argument \nand ");
            }
            System.out.println("the maze must be \nof type .bmp, \nin the img folder \nand referred to without spaces and by name only as the second argument");
            e.printStackTrace();
        }
        int width = mazeImg.getWidth();
        int height = mazeImg.getHeight();
        int vertices = 0;
        boolean visited;
        boolean visitedTwice;
        List<Coordinate> visitedList = new ArrayList<Coordinate>();
        List<Coordinate> visitedTwiceList = new ArrayList<Coordinate>();
        byte[] pixels = new byte[width*height];
        for (int j = 0; j < width*height; j++) {
            if (mazeImg.getRGB(j%width,j/width) == -1) {
                pixels[j] = -1;
            } else if (mazeImg.getRGB(j%width,j/width) == -16777216) {
                pixels[j] = 0;
            }
        }

        Coordinate start = new Coordinate(0,0);
        Coordinate end = new Coordinate(0,0);

        //hittar start och mål för labyrinter som skapas i daedalus, med start längst upp och mål längst ner.
        for (int starti = 0; starti<width; starti++) {
            if (pixels[starti] == -1) {
                start.setX(starti%width);
                start.setY(starti/width);
            }
        }

        for (int endi = width*(height-1); endi<width*height; endi++) {
            if (pixels[endi] == -1) {
                end.setX(endi%width);
                end.setY(endi/width);
            }
        }
        if (args[0].equals("dfs")) {
            Solver dfssolver = new Solver(pixels, width, height, start, end);
            visitedList = dfssolver.getVisitedList();
            visitedTwiceList = dfssolver.getVisitedTwiceList();
            System.out.println("checked " + visitedList.size() + " pixels");
            if (!args[2].equals("false")) {
                System.out.println("gray is solution, dark gray is paths checked");
            }
        } else if (args[0].equals("bfs")) {
            BFSSolver bfssolver = new BFSSolver(pixels,width,height,start,end);
            visitedList = bfssolver.getVisitedList();
            visitedTwiceList = bfssolver.getVisitedTwiceList();
            System.out.println("checked " + visitedList.size() + " pixels");
            if (!args[2].equals("false")) {
                System.out.println("dark gray is solution, gray is paths checked");
            }
        } else {
            System.out.println("enter either bfs or dfs as the first argument");
            System.exit(1);
        }

        for (int i = 0; i < width*height; i++) {
            if (pixels[i] == -1) {
                vertices++;
            }
        }
        System.out.println("Number of vertices: " + vertices + " Number of edges: " + (vertices-1));

        if (!args[2].equals("false")) {
            //visualisering
            for (int i = 0; i < width*height; i++) {
                visited = false;
                visitedTwice = false;
                for (Coordinate c:visitedList) {
                    if (c.getX() == i%width && c.getY() == i/width) {
                        visited = true;
                    }
                }
                for (Coordinate c:visitedTwiceList) {
                    if (c.getX() == i%width && c.getY() == i/width) {
                        visitedTwice = true;
                    }
                }
                if (visitedTwice) {
                    System.out.print("▓▓");
                } else if (visited) {
                    System.out.print("░░");
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
}
