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
    // 0 betyder passerbar och inte besökt,
    // 1 besökt en gång,
    // 2 besökt mer än en gång eller inte passerbar
    private int[] stateList = new int[5];
    int r = ((int)(Math.random()*4));
    private Coordinate end;
    private long startTime;
    private boolean solved = false;

    public Solver(byte[] pixels, int width, int height, Coordinate start, Coordinate end) {
        position = new Coordinate(start.getX(), start.getY());
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.end = end;

        startTime = System.nanoTime();

        while (!solved) {

            checkNeighbours();

            //finns det passerbar och inte besökt ruta, gå dit
            if (upState == 0 || downState == 0 || leftState == 0 || rightState == 0) {
                stateList[0] = upState;
                stateList[1] = downState;
                stateList[2] = leftState;
                stateList[3] = rightState;
                stateList[4] = rightState;
                while (stateList[r] != 0) {
                    r = ((int)(Math.random()*4));
                }
                if (r == 0) {move(0,-1);}
                else if (r == 1) {move(0,1);}
                else if (r == 2) {move(-1,0);}
                else if (r == 3 || r == 4) {move(1,0);}

                //annars gå till passerbara och besökta rutor, "backtracking" till det finns en annan väg
            } else if (upState == 1 || downState == 1 || leftState == 1 || rightState == 1) {
                if (upState == 1) {
                    move(0,-1);
                }
                else if (downState == 1) {
                    move(0,1);
                }
                else if (leftState == 1) {
                    move(-1,0);
                }
                else {
                    move(1,0);
                }
            }
        }
    }

    private void checkNeighbours() {
        //try catch för att det inte går att röra sig utanför bilden
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
        if (position.getX() == end.getX() && position.getY() == end.getY()) {
            long time = System.nanoTime() - startTime;
            System.out.println("solved in " + (time/1000000000f) + " seconds");
            solved = true;
        }
        if (!visitedBefore(position.getX(), position.getY())) {
            //ser om det är en återvändsgränd, lägger då till i visitedTwiceList
             if ((upState+downState+leftState+rightState >= 7) && !(position.getX() == end.getX() && position.getY() == end.getY())) {
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
