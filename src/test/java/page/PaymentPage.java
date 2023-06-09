package page;

import com.codeborne.selenide.SelenideElement;
import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private SelenideElement heading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("input");
    private SelenideElement monthField = $(byText("Месяц")).parent().$("input");
    private SelenideElement yearField = $(byText("Год")).parent().$("input");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$("input");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement inputInvalidExpirationDateMoreThan5Years = $$(".input__sub").find(exactText("Неверно указан срок действия карты"));
    private SelenideElement inputInvalidExpirationDate = $$(".input__sub").find(exactText("Истёк срок действия карты"));
    private SelenideElement inputInvalid = $$(".input__sub").find((exactText("Неверный формат")));

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void inputData(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        ownerField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void notificationOkIsVisible() {
        notificationOK.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void notificationErrorIsVisible() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void inputInvalidIsVisibleMore5Years() {
        inputInvalidExpirationDateMoreThan5Years.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void inputInvalidIsVisibleExpiredDate() {
        inputInvalidExpirationDate.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void inputInvalidIsVisible() {
        inputInvalid.shouldBe(visible, Duration.ofSeconds(15));
    }
}


