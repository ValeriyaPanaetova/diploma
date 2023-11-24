package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.DebitPage;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitPageTest {
    private MainPage mainPage;
    private DebitPage debitPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setupTest() {

        open("http://localhost:8080/");
        mainPage = new MainPage();
        debitPage = mainPage.goToDebitPage();
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Покупка картой со статусом APPROVED")
    void shouldTestBuyCardForStatusApproved() {

        debitPage.fillInCardInfo(DataHelper.getCardNumberForStatusApproved());
        debitPage.setSuccessNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Покупка картой со статусом DECLINED")
    void shouldTestBuyCardForStatusDeclined() {

        debitPage.fillInCardInfo(DataHelper.getCardNumberForStatusDeclined());
        debitPage.setErrorNotificationVisible();
        assertEquals("DECLINED", SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Покупка картой без статуса")
    void shouldTestBuyForCardOfNotStatus() {

        debitPage.fillInCardInfo(DataHelper.getNonStatusCardNumber());
        debitPage.setErrorNotificationVisible();
        assertEquals(null, SQLHelper.getStatusForPayment());
    }

    @Test
    @DisplayName("Покупка картой с пустым полем 'Номер карты'")
    void shouldTestThePurchaseWithAnEmptyCardNumberField() {

        debitPage.fillInCardInfo(DataHelper.getCardNumberForEmptyField());
        debitPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка картой с номером из 14 цифр")
    void shouldTestTheBuyWithA14DigitCard() {

        debitPage.fillInCardInfo(DataHelper.getCardNumberOf14Digits());
        debitPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым номером")
    void shouldTestTheBuyWithACardNumberZero() {

        debitPage.fillInCardInfo(DataHelper.getCardNumberOfZero());
        debitPage.setErrorNotificationVisible();
    }

    @Test
    @DisplayName("Проверка нижнего граничного значения поля 'Месяц'")
    void shouldTestTheLowerBoundaryValueOfTheMonthField() {

        debitPage.fillInCardInfo(DataHelper.getTheFirstMonth());
        debitPage.setSuccessNotificationVisible();
    }

    @Test
    @DisplayName("Проверка верхнего граничного значения поля 'Месяц'")
    void shouldTestTheUpperBoundaryValueOfTheMonthField() {

        debitPage.fillInCardInfo(DataHelper.getTheLastMonth());
        debitPage.setSuccessNotificationVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым полем 'Месяц'")
    void shouldTestMonthFieldOfZero() {

        debitPage.fillInCardInfo(DataHelper.getMonthOfZero());
        debitPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Месяц'")
    void shouldTestEmptyMonthField() {

        debitPage.fillInCardInfo(DataHelper.getMonthEmptyField());
        debitPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Проверка поля 'Месяц' со значением выше верхнего граничного значения")
    void shouldTestMonthFieldForOverUpperBoundaryValue() {

        debitPage.fillInCardInfo(DataHelper.getMonthNotValid());
        debitPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Год'")
    void shouldTestEmptyYearField() {

        debitPage.fillInCardInfo(DataHelper.getYearEmptyField());
        debitPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым полем 'Год'")
    void shouldTestYearFieldOfZero() {

        debitPage.fillInCardInfo(DataHelper.getYearOfZero());
        debitPage.setCardExpiredMessageVisible();
    }

    @Test
    @DisplayName("Покупка с истекшим сроком действия карты")
    void shouldTestPatsValueForYearField() {

        debitPage.fillInCardInfo(DataHelper.getThePastValueInTheYearField());
        debitPage.setCardExpiredMessageVisible();
    }

    @Test
    @DisplayName("Покупка с ненаступившим сроком действия карты")
    void shouldTestFutureValueForYearField() {

        debitPage.fillInCardInfo(DataHelper.getTheFutureValueInTheYearField());
        debitPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Владелец'")
    void shouldTestEmptyHolderField() {

        debitPage.fillInCardInfo(DataHelper.getHolderEmptyField());
        debitPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Поле 'Владелец' состоит из одного имени")
    void shouldTestHolderWithOneName() {

        debitPage.fillInCardInfo(DataHelper.getHolderWithOneName());
        debitPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из кириллицы")
    void shouldTestHolderInCyrillic() {

        debitPage.fillInCardInfo(DataHelper.getHolderInCyrillic());
        debitPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из цифр")
    void shouldTestHolderForDigits() {

        debitPage.fillInCardInfo(DataHelper.getHolderFromDigits());
        debitPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из спецсимволов")
    void shouldTestHolderForSpecialCharacters() {

        debitPage.fillInCardInfo(DataHelper.getHolderFromSpecialCharacters());
        debitPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'CVC/CVV'")
    void shouldTestEmptyCVCField() {

        debitPage.fillInCardInfo(DataHelper.getCVCEmptyField());
        debitPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Значение поля 'CVC/CVV' состоит из 2 цифр")
    void shouldTestCVCTwoDigits() {

        debitPage.fillInCardInfo(DataHelper.getCVCTwoDigits());
        debitPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Значение поля 'CVC/CVV' состоит из нулей")
    void shouldTestCVCFieldOfZero() {

        debitPage.fillInCardInfo(DataHelper.getCVCOfZero());
        debitPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldTestSendingAnEmptyForm() {

        debitPage.fillInCardInfo(DataHelper.getFormFromEmptyFields());
        debitPage.textValidationForTheCardNumberField("Поле обязательно для заполнения");
        debitPage.textValidationForTheMonthField("Поле обязательно для заполнения");
        debitPage.textValidationForTheYearField("Поле обязательно для заполнения");
        debitPage.textValidationForTheHolderField("Поле обязательно для заполнения");
        debitPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }

}