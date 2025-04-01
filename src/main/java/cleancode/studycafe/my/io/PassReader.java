package cleancode.studycafe.my.io;

import cleancode.studycafe.my.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.my.model.pass.locker.StudyCafeLockerPasses;

public interface PassReader {
  StudyCafeSeatPasses readStudyCafePasses();

  StudyCafeLockerPasses readLockerPasses();
}
