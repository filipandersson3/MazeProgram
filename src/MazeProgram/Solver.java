package MazeProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Solver {
    private Coordinate position;
    private List<Coordinate> visitedList = new ArrayList<Coordinate>();
    private List<Coordinate> visitedTwiceList = new ArrayList<Coordinate>();
    private int width;
    private int height;
    private byte[] pixels;
    private int upState;
    private int downState;
    private int leftState;
    private int rightState;
    // 0 means empty and not visited,
    // 1 visited once,
    // 2 visited more than once or not empty
    private long startTime = System.nanoTime();
    private boolean solved = false;

    public Solver(int x, int y, byte[] pixels, int width, int height) {
        position = new Coordinate(x,y);
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        System.out.println(pixels[2]);
        while (!solved) {
            System.out.println(position.getX() + " " + position.getY());

            try {
                downState = visited(position.getX(),position.getY()+1);
            } catch (ArrayIndexOutOfBoundsException ignored) {downState = 2;}
            try {
                upState = visited(position.getX(), position.getY()-1);
            } catch (ArrayIndexOutOfBoundsException ignored) {upState = 2;}
            try {
                rightState = visited(position.getX()+1, position.getY());
            } catch (ArrayIndexOutOfBoundsException ignored) {rightState = 2;}
            try {
                leftState = visited(position.getX()-1, position.getY());
            } catch (ArrayIndexOutOfBoundsException ignored) {leftState = 2;}

            System.out.println(downState + " u" + upState + " l" + leftState + " r" + rightState);
            System.out.println(visitedTwiceList.toString());
            if (upState == 0 || downState == 0 || leftState == 0 || rightState == 0) {
                if (upState == 0) {
                    move(0,-1);
                    System.out.println("going up...");
                }
                else if (downState == 0) {
                    move(0,1);
                    System.out.println("going down...");
                }
                else if (leftState == 0) {
                    move(-1,0);
                    System.out.println("going left...");
                }
                else {
                    move(1,0);
                    System.out.println("going right...");
                }
            } else if (upState == 1 || downState == 1 || leftState == 1 || rightState == 1) {
                if (upState == 1) {
                    move(0,-1);
                    System.out.println("going up...");
                }
                else if (downState == 1) {
                    move(0,1);
                    System.out.println("going down...");
                }
                else if (leftState == 1) {
                    move(-1,0);
                    System.out.println("going left...");
                }
                else {
                    move(1,0);
                    System.out.println("going right...");
                }
            }
        }
    }

    private int visited(int x, int y) {
        if ((pixels[(y*width+x)] == -1)) {
            if (visitedBefore(x,y)) {
                if (visitedTwiceBefore(x,y)) {
                    return 2;
                } else {
                    return 1;
                }
            }
        } else {
            return 2;
        }
        return 0;
    }

    private boolean visitedBefore(int x, int y) {
        for (Coordinate c:visitedList) {
            if (c.getX() == x && c.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean visitedTwiceBefore(int x, int y) {
        for (Coordinate c:visitedTwiceList) {
            if (c.getX() == x && c.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Coordinate getCoords() {
        return position;
    }

    public void setX(int x) {
        position.setX(x);
    }

    public void setY(int y) {
        position.setY(y);
    }

    public List<Coordinate> getVisitedList() {
        return visitedList;
    }

    public List<Coordinate> getVisitedTwiceList() {
        return visitedTwiceList;
    }

    public void move(int x, int y) {
        if (position.getX() == 17 && position.getY() == 30) {
            long time = System.nanoTime() - startTime;
            System.out.println("hello");
            System.out.println(time/1000000000f);
            solved = true;
        }
        if (!visitedBefore(position.getX(), position.getY())) {
             if (upState+downState+leftState+rightState >= 7) {
                 visitedTwiceList.add(new Coordinate(position.getX(), position.getY()));
             }
             visitedList.add(new Coordinate(position.getX(), position.getY()));
        } else if (!visitedTwiceBefore(position.getX(), position.getY())) {
             if ((upState == 1 || downState == 1 || leftState == 1 || rightState == 1)
                     && (upState == 0 || downState == 0 || leftState == 0 || rightState == 0)) {
             } else {
                 visitedTwiceList.add(new Coordinate(position.getX(), position.getY()));
             }
        }
        position.setX(position.getX()+x);
        position.setY(position.getY()+y);
    }
}
