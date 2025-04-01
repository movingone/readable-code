package cleancode.studycafe.my.io;

import cleancode.studycafe.my.model.order.StudyCafePassOrder;
import cleancode.studycafe.my.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.my.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.my.model.pass.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {
  private final OutputHandler outputHandler = new OutputHandler();
  private final InputHandler inputHandler = new InputHandler();

  public void showPassOrderSummary(StudyCafePassOrder selectedPass) {

    outputHandler.showPassOrderSummary(selectedPass);
  }


  public void showSimpleMessage(String message) {
    outputHandler.showSimpleMessage(message);
  }

  public StudyCafePassType askPassTypeSelecting() {
    outputHandler.askPassTypeSelection();
    return inputHandler.getPassTypeSelectingUserAction();
  }

  public void showWelcomeMessage() {
    outputHandler.showWelcomeMessage();
  }

  public void showAnnouncement() {
    outputHandler.showAnnouncement();
  }

  public StudyCafeSeatPass askPassSelecting(List<StudyCafeSeatPass> passCandidates) {
    outputHandler.showPassListForSelection(passCandidates);
    return inputHandler.getSelectPass(passCandidates);
  }

  public boolean askLockerPass(StudyCafeLockerPass lockerPassCandidate) {
    outputHandler.askLockerPass(lockerPassCandidate);
    return inputHandler.getLockerSelection();
  }
}
