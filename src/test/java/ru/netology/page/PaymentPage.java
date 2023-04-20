package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$("input__control");
    private SelenideElement month = $(byText("Месяц")).parent().$("input__control");
    private SelenideElement year = $(byText("Год")).parent().$("input__control");
    private SelenideElement owner = $(byText("Владелец")).parent().$("input__control");
    private SelenideElement cvv = $(byText("CVC/CVV")).parent().$("input__control");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOK = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");
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
        notificationOK.waitUntil(visible, 15_000);
    }

    public void notificationErrorIsVisible() {
        notificationError.waitUntil(visible, 15_000);
    }

    public boolean inputInvalidIsVisible() {
        return inputInvalid.isDisplayed();
    }
    }

}
