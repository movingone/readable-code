package cleancode.studycafe.my.provider;

import cleancode.studycafe.my.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.my.model.pass.locker.StudyCafeLockerPasses;

public interface LockerPassProvider {

  StudyCafeLockerPasses getLockerPasses();
}
