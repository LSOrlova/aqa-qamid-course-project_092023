package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.ApiUtils;
import data.Card;
import data.DataGenerator;
import data.DbUtils;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShopAPITest {
    Card invalidHolderCard = DataGenerator.getInvalidHolderCard();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should send payment request with approved card")
    void shouldSendPaymentRequestWithApprovedCard() {
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getApprovedCard(), "/api/v1/pay");
        assertEquals(200, statusCode);
        assertEquals("APPROVED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Shouldn't send payment request with declined card")
    void shouldNotSendPaymentWithDeclinedCard() {
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getDeclinedCard(), "/api/v1/pay");
        assertEquals(400, statusCode);
        assertEquals("DECLINED", DbUtils.findPaymentStatus());
    }

    @Test
    @DisplayName("Shouldn't send payment request with random card")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        DbUtils.clearTables();
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getFakeCard(), "/api/v1/pay");
        assertNotEquals(200, statusCode);
        assertEquals("0", DbUtils.countRecords());
    }

    @Test
    @DisplayName("Should send credit request with approved card")
    void shouldSendCreditRequestWithApprovedCard() {
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getApprovedCard(), "/api/v1/credit");
        assertEquals(200, statusCode);
        assertEquals("APPROVED", DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Shouldn't send credit request with declined card")
    void shouldNotSendCreditWithDeclinedCard() {
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getDeclinedCard(), "/api/v1/credit");
        assertEquals(400, statusCode);
        assertEquals("DECLINED", DbUtils.findCreditStatus());
    }

    @Test
    @DisplayName("Shouldn't send credit request with random card")
    void shouldNotSendCreditRequestWithIncorrectName() {
        DbUtils.clearTables();
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getFakeCard(), "/api/v1/credit");
        assertNotEquals(200, statusCode);
        assertEquals("0", DbUtils.countRecords());
    }
}
