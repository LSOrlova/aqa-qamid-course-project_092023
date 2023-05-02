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

//    @ParameterizedTest
//    @CsvFileSource(resources = "/values.cvs", numLinesToSkip = 1)
//    @DisplayName("Should show warning pop up during fill in payment form")
//    void shouldShowWarningIfValueIsIncorrectForPayment(String number, String month, String year, String owner, String cvc, String message) {
//        Card incorrectValuesCard = new Card(number, month, year, owner, cvc);
//        HomePage startPage = new HomePage();
//        PaymentPage paymentPage = startPage.goToPaymentPage();
//        paymentPage.inputData(incorrectValuesCard);
//        assertTrue(paymentPage.inputInvalidIsVisible(), message);
//    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/values.cvs", numLinesToSkip = 1)
//    @DisplayName("Should show warning pop up during fill in credit form")
//    void shouldShowWarningIfValueIsIncorrectForCredit(String number, String month, String year, String owner, String cvc, String message) {
//        Card incorrectValuesCard = new Card(number, month, year, owner, cvc);
//        HomePage startPage = new HomePage();
//        CreditPage creditPage = startPage.goToCreditPage();
//        creditPage.fillData(incorrectValuesCard);
//        assertTrue(creditPage.inputInvalidIsVisible("Истёк срок действия карты"), message);
//    }

    @Test
    @DisplayName("Should show warning if card expired, payment page")
    void shouldShowWarningIfCardIsExpiredForPayment() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(expiredCard);
        assertTrue(paymentPage.inputInvalidIsVisible("Истёк срок действия карты"), "Should show warning if card expired, payment page");
    }

    @Test
    @DisplayName("Should show warning if card expired, credit page")
    void shouldShowWarningIfCardIsExpiredForCredit() {
        Card expiredCard = DataGenerator.getInvalidExpDateCard();
        HomePage startPage = new HomePage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(expiredCard);
        assertTrue(creditPage.inputInvalidIsVisible("Истёк срок действия карты"), "Should show warning if card expired, credit page");
    }

    @Test
    @DisplayName("Should show warning if expiration date more than 5 years, payment page")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForPayment() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(invalidExpDateCard);
        assertTrue(paymentPage.inputInvalidIsVisible("Неверно указан срок действия карты"), "Should show warning if expiration date more than 5 years, payment page");
    }

    @Test
    @DisplayName("Should show warning if expiration date more than 5 years, credit page")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForCredit() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard();
        HomePage startPage = new HomePage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(invalidExpDateCard);
        assertTrue(creditPage.inputInvalidIsVisible("Неверно указан срок действия карты"), "Should show warning if expiration date more than 5 years, credit page");
    }
}