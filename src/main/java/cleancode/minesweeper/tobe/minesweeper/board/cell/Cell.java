package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {


    boolean isLandMine();
    boolean hasLandMineCount();
    CellSnapshot getSnapShot();
    void open();
    void flag();
    boolean isChecked();
    boolean isOpened();
}
