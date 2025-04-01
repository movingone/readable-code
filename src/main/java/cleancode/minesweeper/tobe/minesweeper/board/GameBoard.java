package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GameBoard {

  private final Cell[][] board;

  private final int landMineCount;
  private GameStatus gameStatus;


  public GameBoard(GameLevel gameLevel) {
    int rowSize = gameLevel.getRowSize();
    int colSize = gameLevel.getColSize();
    board = new Cell[rowSize][colSize];

    landMineCount = gameLevel.getLandMineCount();
    initializeGameStatus();
  }

  // 상태 변경

  private static List<CellPosition> calculateSurroundedPosition(CellPosition cellPosition, int colSize, int rowSize) {
    return RelativePosition.SURROUNDED_POSITIONS.stream()
      .filter(cellPosition::canCalculatePositionBy)
      .map(cellPosition::calculatePositionBy)
      .filter(position -> position.isRowIndexLessThan(rowSize))
      .filter(position -> position.isColIndexLessThan(colSize))
      .toList();
  }

  public void initializeGame() {
    initializeGameStatus();
    CellPositions cellPositions = CellPositions.from(board);

    initializeEmptyCells(cellPositions);

    List<CellPosition> landMinePositions = cellPositions.extractRandomPosition(landMineCount);
    initializeLandMineCells(landMinePositions);


    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);
    initializeNumberCells(numberPositionCandidates);
  }

  public void openAt(CellPosition cellPosition) {
    if (isLandMineCell(cellPosition)) {
      openOneCellAt(cellPosition);
      changeGamesStatusToLose();
      return;
    }

    openSurroundedCells(cellPosition);
    checkIfGameIsOver();
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();

    checkIfGameIsOver();
  }

  // 판별
  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();
    return cellPosition.isRowIndexMoreThanOrEqual(rowSize) || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public boolean isInProgress() {
    return gameStatus == GameStatus.IN_PROGRESS;
  }

  public boolean isWinStatus() {
    return gameStatus == GameStatus.WIN;
  }


  // 조회

  public boolean isLoseStatus() {
    return gameStatus == GameStatus.LOSE;
  }

  public CellSnapshot getSnapshot(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();

    return cell.getSnapShot();
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

//    ----------------------------------------------------------------------------------------------------------------

  private void openOneCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  private void openSurroundedCells(CellPosition cellPosition) {
    Deque<CellPosition> deque = new ArrayDeque<>();

    deque.push(cellPosition);

    while (!deque.isEmpty()) {
      openAndPushCellAt(deque);
    }
  }

  private void openAndPushCellAt(Deque<CellPosition> deque) {
    CellPosition currentCellPosition = deque.pop();
    if (isOpenedCell(currentCellPosition)) {
      return;
    }
    if (isOpenedCell(currentCellPosition)) {
      return;
    }
    if (isLandMineCell(currentCellPosition)) {
      return;
    }

    openAt(currentCellPosition);

    if (doesCellHaveLandMineCount(currentCellPosition)) {
      return;
    }

    List<CellPosition> surroundedPositions = calculateSurroundedPosition(currentCellPosition, getRowSize(), getColSize());
    for (CellPosition surroundedPosition : surroundedPositions) {
      deque.push(surroundedPosition);
    }
  }

  private void initializeGameStatus() {
    gameStatus = GameStatus.IN_PROGRESS;
  }

  private void initializeLandMineCells(List<CellPosition> landMinePositions) {
    for (CellPosition position : landMinePositions) {
      updateCellAt(position, new LandMineCell());
    }
  }

  private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
    for (CellPosition candidatePosition : numberPositionCandidates) {
      int count = countNearByLandMines(candidatePosition);
      if (count != 0)
        updateCellAt(candidatePosition, new NumberCell(count));

    }
  }

  private void initializeEmptyCells(CellPositions cellPositions) {
    List<CellPosition> allPositions = cellPositions.getPositions();
    for (CellPosition position : allPositions) {
      updateCellAt(position, new EmptyCell());
    }
  }

  private int countNearByLandMines(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();


    long count = calculateSurroundedPosition(cellPosition, colSize, rowSize).stream()
      .filter(this::isLandMineCell)
      .count();

    return (int) count;
  }

  private void updateCellAt(CellPosition position, Cell cell) {
    board[position.getRowIndex()][position.getColIndex()] = cell;
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  private boolean isLandMineCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isLandMine();
  }

  private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.hasLandMineCount();
  }

  private void checkIfGameIsOver() {
    if (isAllCellChecked()) {
      changeGamesStatusToWin();
    }
  }

  private boolean isAllCellChecked() {
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
  }

  private void changeGamesStatusToWin() {
    gameStatus = GameStatus.WIN;
  }

  private void changeGamesStatusToLose() {
    gameStatus = GameStatus.LOSE;
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }
}
