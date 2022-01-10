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

    public BFSSolver(byte[] pixels, int width, int height, Coordinate start, Coordinate end) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        BFSCells.add(new BFSCell(start.getX(), start.getY(), width,height,pixels,new ArrayList<Coordinate>(),end));
        while (!solved) {
            for (BFSCell c:BFSCells) {
                c.update(visitedList);

                if (c.foundSolution(visitedList)) {
                    visitedTwiceList = c.getVisitedList(); //den väg som cellen färdats från start sparas i
                    solved = true;                         //visitedTwiceList för att färgen ska ändras
                }
                if (c.isDeadEnd(visitedList)) {
                    BFSCellTrash.add(c);
                }
                //tar createCellList från varje cell, skapar de cellerna och tar bort den cellen som skapar
                //det är vid korsningar det här händer, så att en cell kan dela sig för att ta alla vägar i korsningen
                for (Coordinate newCell : c.getCreateCellList()) {
                    if (!BFSCells.contains(
                            new BFSCell(newCell.getX(), newCell.getY(), width, height, pixels, c.getVisitedList(), end))
                    && !createCellList.contains(
                            new BFSCell(newCell.getX(), newCell.getY(), width, height, pixels, c.getVisitedList(), end)))
                    {
                        createCellList.add(new BFSCell(newCell.getX(), newCell.getY(), width, height, pixels, c.getVisitedList(), end));
                        BFSCellTrash.add(c);
                    }
                }
            }
            //för att undvika att ändra i en lista som blir loopad igenom
            BFSCells.addAll(createCellList);
            createCellList.clear();
            for (BFSCell t:BFSCellTrash) {
                BFSCells.remove(t);
            }
            BFSCellTrash.clear();
        }
        //hittat lösning så sparar tiden och fortsätter i Main
        long time = System.nanoTime() - startTime;
        System.out.println("solved in " + (time/1000000000f) + " seconds");
    }

    public List<Coordinate> getVisitedList() {
        return visitedList;
    }

    public List<Coordinate> getVisitedTwiceList() {
        return visitedTwiceList;
    }

}
