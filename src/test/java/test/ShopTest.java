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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopTest {
    @BeforeEach
    public void openPage() {
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

    // PAYMENT PAGE
    @Test
    @DisplayName("Happy path. Should send request by approved card, payment page")
    void shouldSendRequestSuccessForPayment() {
        Card approvedCard = DataGenerator.getApprovedCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(approvedCard);
        paymentPage.notificationOkIsVisible();
        assertEquals("APPROVED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Should not send request by declined card, payment page")
    void shouldNotSendRequestDeclinedCardForPayment() {
        Card declinedCard = DataGenerator.getDeclinedCard();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(declinedCard);
        paymentPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Should not send request by random card, payment page")
    void shouldNotSendRequestWithWrongCardNumberForPayment() {
        DbUtils.clearTables();
        Card wrongRandomCardNumber = DataGenerator.getWrongRandomCardNumber();
        HomePage startPage = new HomePage();
        PaymentPage paymentPage = startPage.goToPaymentPage();
        paymentPage.inputData(wrongRandomCardNumber);
        paymentPage.inputInvalidIsVisible();
        assertEquals("0", DbUtils.countRecords());
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

    // CREDIT PAGE
    @Test
    @DisplayName("Happy path. Should send request by approved card, credit page")
    void shouldSendRequestSuccessForCredit() {
        Card approvedCard = DataGenerator.getApprovedCard();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(approvedCard);
        creditPage.notificationOkIsVisible();
        assertEquals("APPROVED", DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Should not send request by declined card, credit page")
    void shouldNotSendRequestDeclinedCardForCredit() {
        Card declinedCard = DataGenerator.getDeclinedCard();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(declinedCard);
        creditPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Should not send request by random card, credit page")
    void shouldNotSendRequestWithWrongCardNumberForCredit() {
        DbUtils.clearTables();
        Card wrongRandomCardNumber = DataGenerator.getWrongRandomCardNumber();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(wrongRandomCardNumber);
        creditPage.inputInvalidIsVisible();
        assertEquals("0", DbUtils.countRecords());
    }

    @Test
    @DisplayName("Should show warning if card expired, credit page")
    void shouldShowWarningIfCardIsExpiredForCredit() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCard();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidExpDateCard);
        creditPage.inputInvalidIsVisibleExpiredDate();
    }

    @Test
    @DisplayName("Should show warning if expiration date more than 5 years, credit page")
    void shouldShowWarningIfExpirationDateMoreThan5YearsForCredit() {
        Card invalidExpDateCard = DataGenerator.getInvalidExpDateCardMoreThan5Years();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidExpDateCard);
        creditPage.inputInvalidIsVisibleMore5Years();
    }

    @Test
    @DisplayName("Should show warning if month 00, credit page")
    void shouldShowWarningIfMonth00ForCredit() {
        Card invalidMonthCard = DataGenerator.getInvalidMonth00();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidMonthCard);
        creditPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if month expired, credit page")
    void shouldShowWarningIfMonthExpiredForCredit() {
        Card invalidExpiredMonthCard = DataGenerator.getExpiredMonth();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidExpiredMonthCard);
        creditPage.inputInvalidIsVisibleExpiredDate();
    }

    @Test
    @DisplayName("Should show warning if year 1 number, credit page")
    void shouldShowWarningIfYear1numberForCredit() {
        Card invalidYear1number = DataGenerator.getInvalidYear1number();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidYear1number);
        creditPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if CVV 1 number, credit page")
    void shouldShowWarningIfCVV1numberForCredit() {
        Card invalidCVV1number = DataGenerator.getInvalidCVV1number();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidCVV1number);
        creditPage.inputInvalidIsVisible();
    }

    @Test
    @DisplayName("Should show warning if CVV 2 number, credit page")
    void shouldShowWarningIfCVV2numberForCredit() {
        Card invalidCVV2numbers = DataGenerator.getInvalidCVV2numbers();
        HomePage homePage = new HomePage();
        CreditPage creditPage = homePage.goToCreditPage();
        creditPage.fillData(invalidCVV2numbers);
        creditPage.inputInvalidIsVisible();
    }
}