package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataGenerator;
import data.DbUtils;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.HomePage;
import page.PaymentPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopDBTest {
    Card validCard = DataGenerator.getApprovedCard();
    Card declinedCard = DataGenerator.getDeclinedCard();
    Card fakeCard = DataGenerator.getFakeCard();

    @BeforeEach
    public void openPage() throws SQLException {
        DbUtils.clearTables();
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
    @DisplayName("Должен подтверждать покупку по карте со статусом APPROVED")
    void shouldConfirmPaymentWithValidCard() throws SQLException {
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(validCard);
        paymentPage.notificationOkIsVisible();
        assertEquals("APPROVED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Должен подтверждать кредит по карте со статусом APPROVED")
    void shouldConfirmCreditWithValidCard() throws SQLException {
        HomePage startPage = new HomePage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(validCard);
        creditPage.notificationOkIsVisible();
        assertEquals("APPROVED", DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Не должен подтверждать покупку по карте со статусом DECLINED")
    void shouldNotConfirmPaymentWithDeclinedCard() throws SQLException {
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(declinedCard);
        paymentPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Не должен подтверждать кредит по карте со статусом DECLINED")
    void shouldNotConfirmCreditWithDeclinedCard() throws SQLException {
        HomePage startPage = new HomePage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(declinedCard);
        creditPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Не должен подтверждать покупку по несуществующей карте")
    void shouldNotConfirmPaymentWithFakeCard() throws SQLException {
        DbUtils.clearTables();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(fakeCard);
        paymentPage.notificationErrorIsVisible();
        assertEquals("0", DbUtils.countRecords());
    }

    @Test
    @DisplayName("Не должен подтверждать кредит по несуществующей карте")
    void shouldNotConfirmCreditWithFakeCard() throws SQLException {
        HomePage startPage = new HomePage();
        CreditPage creditPage = startPage.goToCreditPage();
        creditPage.fillData(fakeCard);
        creditPage.notificationErrorIsVisible();
        assertEquals("0", DbUtils.countRecords());
    }
}