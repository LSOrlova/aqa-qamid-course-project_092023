package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {
    private DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public static String generateRandomCardNumber() {
        return faker.number().digits(16);
    }

    public static String generateMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int shift) {
        return LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateCVC() {
        return faker.number().digits(3);
    }

    public static String generateName() {
        return faker.name().fullName();
    }


    public static String generateWrongRandomCardNumber() {
        return faker.number().digits(15);
    }

    public static String generateInvalid1() {
        return faker.number().digits(1);
    }

    public static String generateInvalid2() {
        return faker.number().digits(2);
    }

    public static String generateFakeHolder() {
        return faker.regexify("[A-Z0-9._%+-]");
    }

    public static Card getApprovedCard() {
        return new Card("1111 2222 3333 4444", generateMonth(2), generateYear(2), generateName(), generateCVC());
    }

    public static Card getDeclinedCard() {
        return new Card("5555 6666 7777 8888", generateMonth(1), generateYear(3), generateName(), generateCVC());
    }

    public static Card getFakeCard() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(1), generateName(), generateCVC());
    }

    public static Card getWrongRandomCardNumber() {
        return new Card(generateWrongRandomCardNumber(), generateMonth(5), generateYear(6), generateName(), generateCVC());
    }

    public static Card getInvalidHolderCard() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(1), generateFakeHolder(), generateCVC());
    }

    public static Card getInvalidExpDateCardMoreThan5Years() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(6), generateName(), generateCVC());
    }

    public static Card getInvalidExpDateCard() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(-6), generateName(), generateCVC());
    }

    public static Card getInvalidMonth00() {
        return new Card(generateRandomCardNumber(), "00", generateYear(1), generateName(), generateCVC());
    }

    public static Card getInvalidYear1number() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateInvalid1(), generateName(), generateCVC());
    }

    public static Card getInvalidCVV1number() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(1), generateName(), generateInvalid1());
    }

    public static Card getInvalidCVV2numbers() {
        return new Card(generateRandomCardNumber(), generateMonth(1), generateYear(1), generateName(), generateInvalid2());
    }

    public static Card getExpiredMonth() {
        return new Card(generateRandomCardNumber(), generateMonth(-1), generateYear(0), generateName(), generateCVC());
    }
}
