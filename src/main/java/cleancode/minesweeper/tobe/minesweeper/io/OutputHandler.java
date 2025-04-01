package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;

public interface OutputHandler {
    void showGameStartComments() ;
    void showBoard(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();
    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

     void showSimpleMessage(String message);
}
