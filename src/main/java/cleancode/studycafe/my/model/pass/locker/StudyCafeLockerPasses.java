package cleancode.studycafe.my.model.pass.locker;

import cleancode.studycafe.my.model.pass.StudyCafeSeatPass;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {
  private List<StudyCafeLockerPass> lockerPasses;

  private StudyCafeLockerPasses(List<StudyCafeLockerPass> lockerPasses) {
    this.lockerPasses = lockerPasses;
  }

  public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> lockerPasses) {
    return new StudyCafeLockerPasses(lockerPasses);
  }

  public Optional<StudyCafeLockerPass> findLockerPassBy(StudyCafeSeatPass pass) {
    return lockerPasses.stream()
      .filter(lockerPass -> (pass.isSameDurationType(lockerPass))
      )
      .findFirst();
  }
}
