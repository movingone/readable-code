package cleancode.studycafe.my;

import cleancode.studycafe.my.exception.AppException;
import cleancode.studycafe.my.io.StudyCafeIOHandler;
import cleancode.studycafe.my.model.order.StudyCafePassOrder;
import cleancode.studycafe.my.model.pass.*;
import cleancode.studycafe.my.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.my.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.my.provider.LockerPassProvider;
import cleancode.studycafe.my.provider.SeatPassProvider;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

  private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
  private final SeatPassProvider seatPassProvider;
  private final LockerPassProvider lockerPassProvider;


  public StudyCafePassMachine(SeatPassProvider seatPassProvider, LockerPassProvider lockerPassProvider) {
    this.seatPassProvider = seatPassProvider;
    this.lockerPassProvider = lockerPassProvider;
  }

  // io 와 같이 파일에서 읽는 다는 개념은 추상화 레벨에서 하위의 개념
  // 상위에서 무엇이든 제공해준다는 개념으로 제공 provider 를 따로 분리해서 두는것이 매우 중요하다

  // -> 이것이 헥사고날 아키텍처의 개념으로 - 포트와 어댑터가 된다.(규격만 맞으면 꽂을 수 있다)

  public void run() {
    try {

      ioHandler.showWelcomeMessage();
      ioHandler.showAnnouncement();

      StudyCafeSeatPass selectedPass = selectPass();
      Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

      StudyCafePassOrder passOrder = StudyCafePassOrder.of(
        selectedPass, optionalLockerPass.orElse(null)
      );

      ioHandler.showPassOrderSummary(passOrder);



    } catch (AppException e) {
      ioHandler.showSimpleMessage(e.getMessage());
    } catch (Exception e) {
      ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
    }
  }

  private StudyCafeSeatPass selectPass() {

    StudyCafePassType passType = ioHandler.askPassTypeSelecting();
    List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(passType);

    return ioHandler.askPassSelecting(passCandidates);
  }

  private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
    StudyCafeSeatPasses allPasses = seatPassProvider.getSeatPasses();

    return allPasses.findPassByStudyCafe(studyCafePassType);
  }

  private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {

    // 고정좌석 타입이 아닌가?
    // 더 높은 추상화 레벨 -> (사물함 옵션을 사용할 수 있는 타입이 아닌가?)
    if (selectedPass.cannotUseLocker()) return Optional.empty();

    Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

    if (lockerPassCandidate.isPresent()) {
      StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

      boolean isLockerSelected = ioHandler.askLockerPass(lockerPass);
      if (isLockerSelected) {
        return Optional.of(lockerPass);
      }
    }
    return Optional.empty();
  }

  private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass pass) {
    StudyCafeLockerPasses allLockerPasses = lockerPassProvider.getLockerPasses();

    return allLockerPasses.findLockerPassBy(pass);
  }
}
