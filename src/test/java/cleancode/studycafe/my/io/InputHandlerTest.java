package cleancode.studycafe.my.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import cleancode.studycafe.my.exception.AppException;
import cleancode.studycafe.my.io.InputHandler;
import cleancode.studycafe.my.model.pass.StudyCafePassType;
import cleancode.studycafe.my.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @Test
    @DisplayName("사용자가 1을 입력하면 HOURLY 타입이 반환된다.")
    void getPassTypeSelectingUserAction_hourly() {
        // Given: 사용자 입력을 "1"로 설정
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        // When: 메서드 호출
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // Then: HOURLY 타입이어야 함
        assertEquals(StudyCafePassType.HOURLY, result);
    }

    @Test
    @DisplayName("사용자가 2를 입력하면 WEEKLY 타입이 반환된다.")
    void getPassTypeSelectingUserAction_weekly() {
        // Given: 사용자 입력을 "2"로 설정
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        // When: 메서드 호출
        StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

        // Then: WEEKLY 타입이어야 함
        assertEquals(StudyCafePassType.WEEKLY, result);
    }

    @Test
    @DisplayName("잘못된 입력 시 AppException이 발생한다.")
    void getPassTypeSelectingUserAction_invalidInput() {
        // Given: 사용자 입력을 잘못된 값으로 설정
        String input = "invalid\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        // When & Then: 예외가 발생해야 함
        assertThrows(AppException.class, inputHandler::getPassTypeSelectingUserAction);
    }

    @Test
    @DisplayName("사용자가 올바른 인덱스를 선택하면 해당 좌석 이용권이 반환된다.")
    void getSelectPass_validIndex() {
        // Given: 사용자 입력을 "2"로 설정 (두 번째 이용권 선택)
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        List<StudyCafeSeatPass> passes = List.of(
                StudyCafeSeatPass.of(StudyCafePassType.HOURLY, "1시간 이용권", 10_000),
                StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, "1주일 이용권", 50_000)
        );

        // When: 메서드 호출
        StudyCafeSeatPass result = inputHandler.getSelectPass(passes);

        // Then: 두 번째 이용권이어야 함
        assertEquals(passes.get(1), result);
    }

    @Test
    @DisplayName("사용자가 사물함 선택 시 1을 입력하면 true가 반환된다.")
    void getLockerSelection_true() {
        // Given: 사용자 입력을 "1"로 설정 (사물함 선택)
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        // When: 메서드 호출
        boolean result = inputHandler.getLockerSelection();

        // Then: true여야 함
        assertTrue(result);
    }

    @Test
    @DisplayName("사용자가 사물함 선택에서 1 이외의 값을 입력하면 false가 반환된다.")
    void getLockerSelection_false() {
        // Given: 사용자 입력을 "0"으로 설정 (사물함 선택 안 함)
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputHandler inputHandler = new InputHandler();

        // When: 메서드 호출
        boolean result = inputHandler.getLockerSelection();

        // Then: false여야 함
        assertFalse(result);
    }
}

