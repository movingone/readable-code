package cleancode.studycafe.my;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudyCafePassMachineTest {

    @Test
    void run() {
        // 테스트용 의존성 설정
        SeatPassProvider seatProvider = new FakeSeatPassProvider();
        LockerPassProvider lockerProvider = new FakeLockerPassProvider();

        StudyCafePassMachine machine = new StudyCafePassMachine(seatProvider, lockerProvider);

        // 실행 및 검증: 예외 발생 없이 정상 종료
        assertDoesNotThrow(() -> machine.run());
    }

    /* 테스트용 Provider 구현체 */
    static class FakeSeatPassProvider implements SeatPassProvider {
        @Override
        public StudyCafeSeatPasses getSeatPasses() {
            return new StudyCafeSeatPasses(List.of(
                    new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10_000)
            ));
        }
    }

    static class FakeLockerPassProvider implements LockerPassProvider {
        @Override
        public StudyCafeLockerPasses getLockerPasses() {
            return new StudyCafeLockerPasses(List.of(
                    new StudyCafeLockerPass("기본 사물함", 5_000)
            ));
        }
    }
}
