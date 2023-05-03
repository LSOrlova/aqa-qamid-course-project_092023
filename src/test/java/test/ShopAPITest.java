package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.ApiUtils;
import data.Card;
import data.DataGenerator;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShopTestApi {
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
    }
    @Test
    @DisplayName("Shouldn't send payment request with declined card")
    void shouldNotSendPaymentWithDeclinedCard() {
        int statusCode = ApiUtils.getRequestStatusCode(DataGenerator.getDeclinedCard(), "/api/v1/pay");
        assertEquals(500, statusCode);
    }

    @Test
    @DisplayName("Shouldn't send payment request with incorrect name")
    void shouldNotSendPaymentRequestWithIncorrectName() {
        int statusCode = ApiUtils.getRequestStatusCode(invalidHolderCard, "/api/v1/pay");
        assertNotEquals(200, statusCode);
    }

    @Test
    @DisplayName("Shouldn't send credit request with incorrect name")
    void shouldNotSendCreditRequestWithIncorrectName() {
        int statusCode = ApiUtils.getRequestStatusCode(invalidHolderCard, "/api/v1/credit");
        assertNotEquals(200, statusCode);
    }
}
