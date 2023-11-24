package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPageTest {
    private MainPage mainPage;
    private CreditPage creditPage;

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
        creditPage = mainPage.goToCreditPage();
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Покупка картой со статусом APPROVED")
    void shouldTestBuyCardForStatusApproved() {

        creditPage.fillInCardInfo(DataHelper.getCardNumberForStatusApproved());
        creditPage.setSuccessNotificationVisible();
        assertEquals("APPROVED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Покупка картой со статусом DECLINED")
    void shouldTestBuyCardForStatusDeclined() {

        creditPage.fillInCardInfo(DataHelper.getCardNumberForStatusDeclined());
        creditPage.setErrorNotificationVisible();
        assertEquals("DECLINED", SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Покупка картой без статуса")
    void shouldTestBuyForCardOfNotStatus() {

        creditPage.fillInCardInfo(DataHelper.getNonStatusCardNumber());
        creditPage.setErrorNotificationVisible();
        assertEquals(null, SQLHelper.getStatusForCredit());
    }

    @Test
    @DisplayName("Покупка картой с пустым полем 'Номер карты'")
    void shouldTestThePurchaseWithAnEmptyCardNumberField() {

        creditPage.fillInCardInfo(DataHelper.getCardNumberForEmptyField());
        creditPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка картой с номером из 14 цифр")
    void shouldTestTheBuyWithA14DigitCard() {

        creditPage.fillInCardInfo(DataHelper.getCardNumberOf14Digits());
        creditPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым номером")
    void shouldTestTheBuyWithACardNumberZero() {

        creditPage.fillInCardInfo(DataHelper.getCardNumberOfZero());
        creditPage.setErrorNotificationVisible();
    }

    @Test
    @DisplayName("Проверка нижнего граничного значения поля 'Месяц'")
    void shouldTestTheLowerBoundaryValueOfTheMonthField() {

        creditPage.fillInCardInfo(DataHelper.getTheFirstMonth());
        creditPage.setSuccessNotificationVisible();
    }

    @Test
    @DisplayName("Проверка верхнего граничного значения поля 'Месяц'")
    void shouldTestTheUpperBoundaryValueOfTheMonthField() {

        creditPage.fillInCardInfo(DataHelper.getTheLastMonth());
        creditPage.setSuccessNotificationVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым полем 'Месяц'")
    void shouldTestMonthFieldOfZero() {

        creditPage.fillInCardInfo(DataHelper.getMonthOfZero());
        creditPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Месяц'")
    void shouldTestEmptyMonthField() {

        creditPage.fillInCardInfo(DataHelper.getMonthEmptyField());
        creditPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Проверка поля 'Месяц' со значением выше верхнего граничного значения")
    void shouldTestMonthFieldForOverUpperBoundaryValue() {

        creditPage.fillInCardInfo(DataHelper.getMonthNotValid());
        creditPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Год'")
    void shouldTestEmptyYearField() {

        creditPage.fillInCardInfo(DataHelper.getYearEmptyField());
        creditPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Покупка картой с нулевым полем 'Год'")
    void shouldTestYearFieldOfZero() {

        creditPage.fillInCardInfo(DataHelper.getYearOfZero());
        creditPage.setCardExpiredMessageVisible();
    }

    @Test
    @DisplayName("Покупка с истекшим сроком действия карты")
    void shouldTestPatsValueForYearField() {

        creditPage.fillInCardInfo(DataHelper.getThePastValueInTheYearField());
        creditPage.setCardExpiredMessageVisible();
    }

    @Test
    @DisplayName("Покупка с ненаступившим сроком действия карты")
    void shouldTestFutureValueForYearField() {

        creditPage.fillInCardInfo(DataHelper.getTheFutureValueInTheYearField());
        creditPage.setInvalidCardExpirationDateMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'Владелец'")
    void shouldTestEmptyHolderField() {

        creditPage.fillInCardInfo(DataHelper.getHolderEmptyField());
        creditPage.setRequiredFieldVisible();
    }

    @Test
    @DisplayName("Поле 'Владелец' состоит из одного имени")
    void shouldTestHolderWithOneName() {

        creditPage.fillInCardInfo(DataHelper.getHolderWithOneName());
        creditPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из кириллицы")
    void shouldTestHolderInCyrillic() {

        creditPage.fillInCardInfo(DataHelper.getHolderInCyrillic());
        creditPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из цифр")
    void shouldTestHolderForDigits() {

        creditPage.fillInCardInfo(DataHelper.getHolderFromDigits());
        creditPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Значение поля 'Владелец' состоит из спецсимволов")
    void shouldTestHolderForSpecialCharacters() {

        creditPage.fillInCardInfo(DataHelper.getHolderFromSpecialCharacters());
        creditPage.setInvalidCharMessageVisible();
    }

    @Test
    @DisplayName("Проверка пустого поля 'CVC/CVV'")
    void shouldTestEmptyCVCField() {

        creditPage.fillInCardInfo(DataHelper.getCVCEmptyField());
        creditPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Значение поля 'CVC/CVV' состоит из 2 цифр")
    void shouldTestCVCTwoDigits() {

        creditPage.fillInCardInfo(DataHelper.getCVCTwoDigits());
        creditPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Значение поля 'CVC/CVV' состоит из нулей")
    void shouldTestCVCFieldOfZero() {

        creditPage.fillInCardInfo(DataHelper.getCVCOfZero());
        creditPage.setInvalidFormatVisible();
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldTestSendingAnEmptyForm() {

        creditPage.fillInCardInfo(DataHelper.getFormFromEmptyFields());
        creditPage.textValidationForTheCardNumberField("Поле обязательно для заполнения");
        creditPage.textValidationForTheMonthField("Поле обязательно для заполнения");
        creditPage.textValidationForTheYearField("Поле обязательно для заполнения");
        creditPage.textValidationForTheHolderField("Поле обязательно для заполнения");
        creditPage.textValidationForTheCVCField("Поле обязательно для заполнения");
    }
}