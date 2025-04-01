package cleancode.studycafe.my.model.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudyCafePassOrderTest {

    @Test
    @DisplayName("좌석 이용권만 있는 주문 생성")
    void createOrderWithSeatPassOnly() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        assertNotNull(order);
        assertEquals(seatPass, order.getSeatPass());
        assertTrue(order.getLockerPass().isEmpty());
    }

    @Test
    @DisplayName("좌석 이용권과 사물함 이용권이 모두 있는 주문 생성")
    void createOrderWithSeatAndLockerPass() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000);
        StudyCafeLockerPass lockerPass = new StudyCafeLockerPass("소형 사물함", 5000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        assertNotNull(order);
        assertEquals(seatPass, order.getSeatPass());
        assertTrue(order.getLockerPass().isPresent());
        assertEquals(lockerPass, order.getLockerPass().get());
    }

    @Test
    @DisplayName("할인이 없는 경우 총 가격 계산")
    void calculateTotalPriceWithoutDiscount() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000);
        StudyCafeLockerPass lockerPass = new StudyCafeLockerPass("소형 사물함", 5000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        assertEquals(15000, order.getTotalPrice());
    }

    @Test
    @DisplayName("할인이 있는 경우 총 가격 계산")
    void calculateTotalPriceWithDiscount() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000) {
            @Override
            public int getDiscountPrice() {
                return 2000;
            }
        };
        StudyCafeLockerPass lockerPass = new StudyCafeLockerPass("소형 사물함", 5000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        assertEquals(13000, order.getTotalPrice());
    }

    @Test
    @DisplayName("사물함 이용권이 없는 경우 총 가격 계산")
    void calculateTotalPriceWithoutLockerPass() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        assertEquals(10000, order.getTotalPrice());
    }

    @Test
    @DisplayName("할인 가격 확인")
    void getDiscountPrice() {
        StudyCafeSeatPass seatPass = new StudyCafeSeatPass(StudyCafePassType.HOURLY, "1시간 이용권", 10000) {
            @Override
            public int getDiscountPrice() {
                return 2000;
            }
        };
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        assertEquals(2000, order.getDiscountPrice());
    }
}
