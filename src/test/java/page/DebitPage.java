package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPage {

    /*для полей*/
    private SelenideElement heading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$("button").find(exactText("Продолжить"));

    /*для вывода сообщений*/
    private SelenideElement successNOTIF = $(".notification_status_ok");
    private SelenideElement errorNOTIF = $(".notification_status_error");
    private SelenideElement requiredField = $(byText("Поле обязательно для заполнения"));
    private SelenideElement invalidFormat = $(byText("Неверный формат"));
    private SelenideElement invalidCharMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement invalidCardExpirationDateMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));

    public DebitPage() {
        heading.shouldBe(visible);
    }

    public void fillInCardInfo(DataHelper.CardInfo cardInfo) {
        cardNumberField.sendKeys(cardInfo.getCardNumber());
        monthField.sendKeys(cardInfo.getMonth());
        yearField.sendKeys(cardInfo.getYear());
        holderField.sendKeys(cardInfo.getHolder());
        cvcField.sendKeys(cardInfo.getCvc());
        buttonContinue.click();
    }

    /*для проверки пустого поля*/
    public void textValidationForTheCardNumberField(String text) {
        cardNumberField.shouldHave(text(text), Duration.ofSeconds(12)).shouldBe(visible);
    }

    public void textValidationForTheMonthField(String text) {
        monthField.shouldHave(text(text), Duration.ofSeconds(12)).shouldBe(visible);
    }

    public void textValidationForTheYearField(String text) {
        yearField.shouldHave(text(text), Duration.ofSeconds(12)).shouldBe(visible);
    }

    public void textValidationForTheHolderField(String text) {
        holderField.shouldHave(text(text), Duration.ofSeconds(12)).shouldBe(visible);
    }

    public void textValidationForTheCVCField(String text) {
        cvcField.shouldHave(text(text), Duration.ofSeconds(12)).shouldBe(visible);
    }

    /*методы для вывода сообщений*/
    public void setSuccessNotificationVisible() {
        successNOTIF.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setErrorNotificationVisible() {
        errorNOTIF.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setRequiredFieldVisible() {
        requiredField.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setInvalidFormatVisible() {
        invalidFormat.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setInvalidCharMessageVisible() {
        invalidCharMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setInvalidCardExpirationDateMessageVisible() {
        invalidCardExpirationDateMessage.shouldBe(visible, Duration.ofSeconds(12));
    }

    public void setCardExpiredMessageVisible() {
        cardExpiredMessage.shouldBe(visible, Duration.ofSeconds(12));
    }
}