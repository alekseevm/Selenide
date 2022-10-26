import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class CardFormDeliveryTest {


        String generateDate(int days, String pattern) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
        }

        @BeforeEach
        void openUrlInBrowser() {
            open("http://localhost:9999");
        }

        @Test
        void shouldSuccessfulSentForm() {
            String date = generateDate(3, "dd.MM.yyyy");
            $("[data-test-id='city'] input").setValue("Пермь");
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id='date'] input").setValue(date);
            $("[data-test-id='name'] input").setValue("Михаил Алексеев");
            $("[data-test-id='phone'] input").setValue("+79991234567");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            $("[data-test-id='notification']  .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
            $("[data-test-id='notification']  .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно забронирована на " + date));
        }
    }

