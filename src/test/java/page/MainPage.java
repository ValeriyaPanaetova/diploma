package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement buttonBuy = $(byText("Купить"));
    private SelenideElement buttonCredit = $(byText("Купить в кредит"));

    public MainPage() {

        heading.shouldBe(visible);
    }

    public DebitPage goToDebitPage() {
        buttonBuy.click();
        return new DebitPage();
    }

    public CreditPage goToCreditPage() {
        buttonCredit.click();
        return new CreditPage();
    }
}