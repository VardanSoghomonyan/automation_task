package utils;

import com.neovisionaries.i18n.CountryCode;
import statics.CountriesEnum;

import java.util.Currency;

public class Util {
    public static int getRandomNumber(int from, int to) {
        int range = to - from + 1;
        return (int) (Math.random() * range) + from;
    }

    public static String getCurrencyCode(CountriesEnum countriesEnum) {
        Currency currency = CountryCode.findByName(countriesEnum.getCountryName()).get(0).getCurrency();
        return currency.getCurrencyCode();
    }
}
