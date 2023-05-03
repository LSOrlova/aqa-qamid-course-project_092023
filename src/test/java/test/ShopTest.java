package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import page.CreditPage;
import page.HomePage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopTest {
    @BeforeEach
    public void openPage() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Happy path. Should send request by approved card, payment page")
    void shouldSendRequestSuccessForPayment() {
        Card expiredCard = DataGenerator.getApprovedCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(expiredCard);
        paymentPage.notificationOkIsVisible();
    }

    @Test
    @DisplayName("Should not send request by declined card, payment page")
    void shouldNotSendRequestDeclinedCardForPayment() {
        Card expiredCard = DataGenerator.getDeclinedCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(expiredCard);
        paymentPage.notificationErrorIsVisible();
    }

    @Test
    @DisplayName("Should not send request by random card, payment page")
    void shouldNotSendRequestWithWrongCardNumberForPayment() {
        Card wrongRandomCardNumber = DataGenerator.getWrongRandomCardNumber();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(wrongRandomCardNumber);
        paymentPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if card expired, payment page")
    void shouldShowWarningIfCardIsExpiredForPayment() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidExpDateCard);
        paymentPage.inputInvalidIsVisibleExpiredDate();
    }

    @Test
    @DisplayName("Should show warning if expiration date more than 5 years, payment page")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForPayment() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCardMoreThan5Years();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidExpDateCard);
        paymentPage.inputInvalidIsVisibleMore5Years();
    }

    @Test
    @DisplayName("Should show warning if month 00, payment page")
    void shouldShowWarningIfMonth00ForPayment() {
        Card invalidMonthCard = DataGenerator.getInvalidMonth00();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidMonthCard);
        paymentPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if month expired, payment page")
    void shouldShowWarningIfMonthExpiredForPayment() {
        Card invalidExpiredMonthCard = DataGenerator.getExpiredMonth();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidExpiredMonthCard);
        paymentPage.inputInvalidIsVisibleExpiredDate();
    }

    @Test
    @DisplayName("Should show warning if year 1 number, payment page")
    void shouldShowWarningIfYear1numberForPayment() {
        Card invalidYear1number = DataGenerator.getInvalidYear1number();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidYear1number);
        paymentPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if CVV 1 number, payment page")
    void shouldShowWarningIfCVV1numberForPayment() {
        Card invalidCVV1number = DataGenerator.getInvalidCVV1number();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidCVV1number);
        paymentPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if CVV 2 number, payment page")
    void shouldShowWarningIfCVV2numberForPayment() {
        Card invalidCVV2numbers = DataGenerator.getInvalidCVV2numbers();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidCVV2numbers);
        paymentPage.inputInvalidIsVisible();
    }

//    @Test
//    @DisplayName("Happy path, credit page")
//    void shouldSendRequestSuccessForCredit() {
//        Card expiredCard = DataGenerator.getApprovedCard();
//        HomePage startPage = new HomePage();
//        CreditPage creditPage = startPage.goToCreditPage();
//        creditPage.fillData(expiredCard);
//        creditPage.notificationOkIsVisible();
//    }
//
//    @Test
//    @DisplayName("Should show warning if card expired, credit page")
//    void shouldShowWarningIfCardIsExpiredForCredit() {
//        Card expiredCard = DataGenerator.getInvalidExpDateCard();
//        HomePage startPage = new HomePage();
//        CreditPage creditPage = startPage.goToCreditPage();
//        creditPage.fillData(expiredCard);
//        assertTrue(creditPage.inputInvalidIsVisible("Истёк срок действия карты"), "Should show warning if card expired, credit page");
//    }
//
//    @Test
//    @DisplayName("Should show warning if expiration date more than 5 years, credit page")
//    void shouldShowWarningIfExpirationDateMoreThan5YearsForCredit() {
//        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard();
//        HomePage startPage = new HomePage();
//        CreditPage creditPage = startPage.goToCreditPage();
//        creditPage.fillData(invalidExpDateCard);
//        assertTrue(creditPage.inputInvalidIsVisible("Неверно указан срок действия карты"), "Should show warning if expiration date more than 5 years, credit page");
//    }
}