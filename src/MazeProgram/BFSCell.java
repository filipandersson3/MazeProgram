package MazeProgram;

import java.util.ArrayList;
import java.util.List;

public class BFSCell {
    private Coordinate position;
    private List<Coordinate> visitedList = new ArrayList<Coordinate>();
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
    private List<Coordinate> createCellList = new ArrayList<Coordinate>();

    public BFSCell(int x, int y, int width, int height, byte[] pixels) {
        position = new Coordinate(x,y);
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public void update(List<Coordinate> totalVisitedList) {
        //System.out.println(position.getX() + " " + position.getY());

        try {
            downState = visited(position.getX(),position.getY()+1, totalVisitedList);
        } catch (ArrayIndexOutOfBoundsException ignored) {downState = 2;}
        try {
            upState = visited(position.getX(), position.getY()-1, totalVisitedList);
        } catch (ArrayIndexOutOfBoundsException ignored) {upState = 2;}
        try {
            rightState = visited(position.getX()+1, position.getY(), totalVisitedList);
        } catch (ArrayIndexOutOfBoundsException ignored) {rightState = 2;}
        try {
            leftState = visited(position.getX()-1, position.getY(), totalVisitedList);
        } catch (ArrayIndexOutOfBoundsException ignored) {leftState = 2;}

        //System.out.println(downState + " u" + upState + " l" + leftState + " r" + rightState);
        if (upState+downState == 0) {  //poopa code
            junctionCreate();
        } else if (upState+rightState == 0) {
            junctionCreate();
        } else if (upState+leftState == 0) {
            junctionCreate();
        } else if (downState+rightState == 0) {
            junctionCreate();
        } else if (downState+leftState == 0) {
            junctionCreate();
        } else if (rightState+leftState == 0) {
            junctionCreate();
        } else if (upState == 0 || downState == 0 || leftState == 0 || rightState == 0) {
            if (upState == 0) {
                move(0,-1);
                //System.out.println("going up...");
            }
            else if (downState == 0) {
                move(0,1);
                //System.out.println("going down...");
            }
            else if (leftState == 0) {
                move(-1,0);
                //System.out.println("going left...");
            }
            else {
                move(1,0);
                //System.out.println("going right...");
            }
        }
    }

    private void junctionCreate() {
        createCellList.clear();
        if (upState == 0) {
            createCellList.add(new Coordinate(position.getX(), position.getY()-1));
        }
        if (downState == 0) {
            createCellList.add(new Coordinate(position.getX(), position.getY()+1));
        }
        if (rightState == 0) {
            createCellList.add(new Coordinate(position.getX()+1, position.getY()));
        }
        if (leftState == 0) {
            createCellList.add(new Coordinate(position.getX()-1, position.getY()));
        }
        visitedList.addAll(createCellList);
    }

    private int visited(int x, int y, List<Coordinate> totalVisitedList) {
        if ((pixels[(y*width+x)] == -1)) {
            if (visitedBefore(x,y,totalVisitedList)) {
                return 1;
            }
        } else {
            return 2;
        }
        return 0;
    }
    private boolean visitedBefore(int x, int y, List<Coordinate> totalVisitedList) {
        for (Coordinate c:totalVisitedList) {
            if (c.getX() == x && c.getY() == y) {
                return true;
            }
        }
        return false;
    }
    public void move(int x, int y) {
        visitedList.add(new Coordinate(position.getX(), position.getY()));
        position.setX(position.getX()+x);
        position.setY(position.getY()+y);
    }
    public boolean isDeadEnd() {
        if (upState+downState+leftState+rightState >= 7) {
            visitedList.add(new Coordinate(position.getX(), position.getY()));
            return true;
        }
        return false;
    }
    public boolean foundSolution() {
        if (position.getX() == 17 && position.getY() == 30) {
            visitedList.add(new Coordinate(17,30));
            return true;
        }
        return false;
    }

    public List<Coordinate> getVisitedList() {
        return visitedList;
    }

    public List<Coordinate> getCreateCellList() {
        return createCellList;
    }
}
