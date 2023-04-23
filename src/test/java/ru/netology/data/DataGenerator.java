package ru.netology.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DataGenerator {
    public static Card getApprovedCard() {
        return new Card("1111 2222 3333 4444", 08, 25, "Ivan Petrov", "456");
    }
    public static Card getDeclinedCard() {
        return new Card("5555 6666 7777 8888", 08, 25, "Petr Ivanov", "654");
    }
    public static Card getInvalidCard() {
        return new Card("1111 2222 3333 4448", 08,25, "Sidor Dvash", "159");
    }
    public static Card getInvalidHolderCard() {
        return new Card("1111 2222 3333 4448", 08,25, "1#)", "159");
    }
    public static Card getInvalidExpDateCard(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, months);
        String date = new SimpleDateFormat("dd.MM.yy").format(calendar.getTime());
        System.out.println(date);
        String month = new SimpleDateFormat("MM").format(calendar.getTime());
        String year = new SimpleDateFormat("yy").format(calendar.getTime());
        System.out.println(month + " " + year);
        return new Card("4444 4444 4444 4441", month, year, "Card Holder", "123");
    }
}
