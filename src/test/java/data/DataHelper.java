package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerLangEn = new Faker(new Locale("en"));
    private static Faker fakerLangRu = new Faker(new Locale("ru"));

    /*заполненная карта валидными данными со статусом APPROVED*/
    public static CardInfo getCardNumberForStatusApproved() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*заполненная карта валидными данными со статусом DECLINE*/
    public static CardInfo getCardNumberForStatusDeclined() {
        return new CardInfo("4444 4444 4444 4442", getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*ДЛЯ ТЕСТОВ С ПОЛЕМ "НОМЕР КАРТЫ"*/

    /*пустое поле "Номер карты"*/
    public static CardInfo getCardNumberForEmptyField() {
        return new CardInfo("", getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*"Номер карты" из 14 цифр*/
    public static CardInfo getCardNumberOf14Digits() {
        return new CardInfo("4444 4444 4444 44", getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*"Номер карты" из нулей*/
    public static CardInfo getCardNumberOfZero() {
        return new CardInfo("0000 0000 0000 0000", getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*"Номер карты" без статуса*/
    public static CardInfo getNonStatusCardNumber() {
        return new CardInfo(getRandomCardNumber(), getValidMonth(), getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*ДЛЯ ТЕСТОВ С ПОЛЕМ "МЕСЯЦ"*/

    /*первый месяц в поле "Месяц"*/
    public static CardInfo getTheFirstMonth() {
        return new CardInfo("4444 4444 4444 4441", "01", getAlwaysValidYear(), getValidHolder(), getValidCVV());
    }

    /*последний месяц в поле "Месяц"*/
    public static CardInfo getTheLastMonth() {
        return new CardInfo("4444 4444 4444 4441", "12", getAlwaysValidYear(), getValidHolder(), getValidCVV());
    }

    /*нулевые значения в поле "Месяц"*/
    public static CardInfo getMonthOfZero() {
        return new CardInfo("4444 4444 4444 4441", "00", getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*пустое поле "Месяц"*/
    public static CardInfo getMonthEmptyField() {
        return new CardInfo("4444 4444 4444 4441", "", getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*неверное значение в поле "Месяц"*/
    public static CardInfo getMonthNotValid() {
        return new CardInfo("4444 4444 4444 4441", "13", getCurrentYear(), getValidHolder(), getValidCVV());
    }

    /*ДЛЯ ТЕСТОВ С ПОЛЕМ "ГОД"*/

    /*пустое поле "Год"*/
    public static CardInfo getYearEmptyField() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), "", getValidHolder(), getValidCVV());
    }

    /*нулевое значение в поле "Год"*/
    public static CardInfo getYearOfZero() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), "00", getValidHolder(), getValidCVV());
    }

    /*прошлый год в поле "Год"*/
    public static CardInfo getThePastValueInTheYearField() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getPastYear(), getValidHolder(), getValidCVV());
    }

    /*будущий год в поле "Год"*/
    public static CardInfo getTheFutureValueInTheYearField() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getFutureYear(), getValidHolder(), getValidCVV());
    }

    /*ДЛЯ ТЕСТОВ С ПОЛЕМ "ВЛАДЕЛЕЦ"*/

    /*пустое поле "Владелец"*/
    public static CardInfo getHolderEmptyField() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), "", getValidCVV());
    }

    /*поле "Владелец" из одного имени*/
    public static CardInfo getHolderWithOneName() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), "Anna", getValidCVV());
    }

    /*поле "Владелец" на кириллице*/
    public static CardInfo getHolderInCyrillic() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), getHolderRusLang(), getValidCVV());
    }

    /*цифровое значение в поле "Владелец"*/
    public static CardInfo getHolderFromDigits() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), "12345 678", getValidCVV());
    }

    /*поле "Владелец" из спецсимовлов*/
    public static CardInfo getHolderFromSpecialCharacters() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), ":%№;( :?;", getValidCVV());
    }

    /*ДЛЯ ТЕСТОВ С ПОЛЕМ "CVC/CVV"*/

    /*пусто поле "CVC/CVV"*/
    public static CardInfo getCVCEmptyField() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), getValidHolder(), "");
    }

    /*поле "CVC/CVV" из двух цифр*/
    public static CardInfo getCVCTwoDigits() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), getValidHolder(), "01");
    }

    /*нулевое значение в поле "CVC/CVV"*/
    public static CardInfo getCVCOfZero() {
        return new CardInfo("4444 4444 4444 4441", getValidMonth(), getCurrentYear(), getValidHolder(), "000");
    }

    /*ДЛЯ ТЕСТА С ПУСТОЙ ФОРМОЙ"*/

    public static CardInfo getFormFromEmptyFields() {
        return new CardInfo("", "", "", "", "");
    }


    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getPastYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }

    public static String getCurrentYear() {
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return currentYear;
    }

    public static String getAlwaysValidYear() {
        String alwaysValidYear = LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
        return alwaysValidYear;
    }

    public static String getFutureYear() {
        String futureYear = LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        return futureYear;
    }


    public static String getValidHolder() {
        return fakerLangEn.name().firstName().toUpperCase() + " " + fakerLangEn.name().lastName().toUpperCase();
    }

    public static String getHolderRusLang() {
        String name = fakerLangRu.name().fullName();
        return name;
    }

    public static String getRandomCardNumber() {
        return fakerLangEn.business().creditCardNumber();
    }

    public static String getValidCVV() {
        return fakerLangEn.number().digits(3);
    }

    @Data
    @AllArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }
}
