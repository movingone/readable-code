package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
    // 기간, 가격, 할인율 -> 이용권, 여기서 불러옴

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();
            outputHandler.askPassTypeSelection();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            // 시간권, 주권, 고정석

            List<StudyCafePass> userPasses = studyCafePasses.stream().toList();
            // 시간권 선택

            outputHandler.showPassListForSelection(userPasses);
            //보여주기

            StudyCafePass selectedPass = inputHandler.getSelectPass(userPasses);


            if (studyCafePassType == StudyCafePassType.FIXED) {
//                여기서 부터
                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                            && option.getDuration() == selectedPass.getDuration()
                    )
                    .findFirst()
                    .orElse(null);

                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    inputHandler.getLockerSelection();
                }
                outputHandler.showPassOrderSummary(selectedPass, lockerPass);
            }
            else
              outputHandler.showPassOrderSummary(selectedPass, null);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

}
