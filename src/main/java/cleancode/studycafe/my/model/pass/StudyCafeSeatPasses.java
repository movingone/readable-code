package cleancode.studycafe.my.model.pass;

import java.util.List;

public class StudyCafeSeatPasses {
  private final List<StudyCafeSeatPass> cafePasses;

  private StudyCafeSeatPasses(List<StudyCafeSeatPass> cafePasses) {
    this.cafePasses = cafePasses;
  }

  public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> cafePasses) {
    return new StudyCafeSeatPasses(cafePasses);
  }


  public List<StudyCafeSeatPass> findPassByStudyCafe(StudyCafePassType studyCafePassType) {
    return cafePasses.stream()
      .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
      .toList();
  }
}
