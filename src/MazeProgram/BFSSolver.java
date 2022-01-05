package MazeProgram;

import java.util.ArrayList;
import java.util.List;

public class BFSSolver {
    private List<Coordinate> visitedList = new ArrayList<Coordinate>();
    private List<Coordinate> visitedTwiceList = new ArrayList<Coordinate>();
    private int width;
    private int height;
    private byte[] pixels;
    private long startTime = System.nanoTime();
    private boolean solved = false;
    private List<BFSCell> BFSCells = new ArrayList<BFSCell>();
    private List<BFSCell> BFSCellTrash = new ArrayList<BFSCell>();
    private List<BFSCell> createCellList = new ArrayList<BFSCell>();

    public BFSSolver(int x, int y, byte[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        System.out.println(pixels[2]);
        BFSCells.add(new BFSCell(x,y,width,height,pixels));
        while (!solved) {
            for (BFSCell c:BFSCells) {
                c.update(visitedList);
                if (c.foundSolution()) {
                    solved = true;
                }
                if (c.isDeadEnd()) {
                    BFSCellTrash.add(c);
                }
                for (Coordinate coord:c.getVisitedList()) {
                    if (!visitedList.contains(coord)) {
                        visitedList.add(coord);
                    }
                }
                for (Coordinate newCell:c.getCreateCellList()) {
                    if (!BFSCells.contains(new BFSCell(newCell.getX(),newCell.getY(),width,height,pixels))
                        && !createCellList.contains(new BFSCell(newCell.getX(),newCell.getY(),width,height,pixels))) {
                        createCellList.add(new BFSCell(newCell.getX(),newCell.getY(),width,height,pixels));
                    }
                }
            }
            BFSCells.addAll(createCellList);
            for (BFSCell t:BFSCellTrash) {
                BFSCells.remove(t);
            }
            BFSCellTrash.clear();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("hello");
        System.out.println(time/1000000000f);
    }

    private int visited(int x, int y) {
        if ((pixels[(y*width+x)] == -1)) {
            if (visitedBefore(x,y)) {
                return 1;
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
        return false;
    }

    public List<Coordinate> getVisitedList() {
        return visitedList;
    }

    public List<Coordinate> getVisitedTwiceList() {
        return visitedTwiceList;
    }

}
