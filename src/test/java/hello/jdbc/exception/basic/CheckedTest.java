package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class CheckedTest {

    @DisplayName("예외를 catch로 잡아서 처리한다.")
    @Test
    void checked_catch() throws Exception {
        Service service = new Service();
        service.callCatch();
    }

    @DisplayName("예외를 throws로 던진다.")
    @Test
    void checked_throw() throws Exception {
        Service service = new Service();
        assertThatThrownBy(service::callThrow)
                .isInstanceOf(MyCheckedException.class)
                .hasMessage("Repository Exception");
    }

    /**
     * Exception을 상송받은 예외는 체크 예외가 된다.
     * */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘중 하나를 필수로 선택해야 한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡앗 처리하는 코드
         * */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외 처리 로직
                log.debug("예외 처리, message = {}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야 한다.
         * @throws MyCheckedException
         * */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("Repository Exception");
        }
    }
}
