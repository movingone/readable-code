package cleancode.minesweeper.asis.cell;

public class Cell {

    private final String sign;
//    private final String FLAG_SIGN = "⚑";
//    private final String LAND_MINE_SIGN = "☼";
//    private final String CLOSED_CELL_SIGN = "□";
//    private final String OPENED_CELL_SIGN = "■";

    private Cell(String sign) {
        this.sign = sign;
    }


    public static Cell of(String sign) {
        return new Cell(sign);
    }

    public boolean equalsSign(String cellInput) {
        return this.sign.equals(cellInput);
    }
}
