package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

    @ParameterizedTest
    @CsvFileSource(resources = "/valuesPayment.cvs", numLinesToSkip = 1)
    @DisplayName("Should show warning pop up during fill in form")
    void shouldShowWarningIfValueIsIncorrectForPayment(String number, String month, String year, String owner, String cvc, String message) {
        Card incorrectValuesCard = new Card(number, month, year, owner, cvc);
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(incorrectValuesCard);
        assertTrue(paymentPage.inputInvalidIsVisible(), message);
    }


}