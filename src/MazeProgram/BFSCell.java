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
    // 0 betyder passerbar och inte besökt,
    // 1 besökt en gång,
    // 2 besökt mer än en gång eller inte passerbar
    private List<Coordinate> createCellList = new ArrayList<Coordinate>();
    private Coordinate end;

    public BFSCell(int x, int y, int width, int height, byte[] pixels, List<Coordinate> lastVisited, Coordinate end) {
        position = new Coordinate(x,y);
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.end = end;
        visitedList.addAll(lastVisited);
    }

    public void update(List<Coordinate> totalVisitedList) {
        visitedList.add(new Coordinate(position.getX(), position.getY()));

        checkNeighbours(totalVisitedList);

        //ser om mer än en väg är möjlig, annars flytta till en möjlig väg om det finns
        if ((upState + downState == 0
                || upState + rightState == 0
                || upState + leftState == 0
                || downState + rightState == 0
                || downState + leftState == 0)
                || rightState + leftState == 0) {
            junctionCreate();
        } else {
            if (upState == 0) {
                move(0, -1);
            } else if (downState == 0) {
                move(0, 1);
            } else if (leftState == 0) {
                move(-1, 0);
            } else if (rightState == 0) {
                move(1, 0);
            }
        }
    }

    private void checkNeighbours(List<Coordinate> totalVisitedList) {
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
    }

    private void junctionCreate() {
        //lägger till celler som ska skapas i en lista som BFSSolver använder
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
    }

    private int visited(int x, int y, List<Coordinate> totalVisitedList) {
        if ((pixels[(y*width+x)] == -1)) {
            if (visitedBefore(x,y,totalVisitedList)) {
                return 1;
            }
        } else {
            return 2;
        }
        return 0; // <-- används inte men måste finnas
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
        position.setX(position.getX()+x);
        position.setY(position.getY()+y);
    }
    public boolean isDeadEnd() {
        if (!(upState==0||downState==0||rightState==0||leftState==0)) {
            visitedList.add(new Coordinate(position.getX(), position.getY()));
            return true;
        }
        return false;
    }
    public boolean foundSolution() {
        if (position.getX() == end.getX() && position.getY() == end.getY()) {
            visitedList.add(new Coordinate(position.getX(), position.getY()));
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

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Coordinate getPosition() {
        return position;
    }
}
