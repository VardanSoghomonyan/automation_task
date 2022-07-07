package statics;

public enum CountriesEnum {
    LITHUANIA("Lithuania", "LT"),
    LATVIA("Latvia", "LV"),
    ESTONIA("Estonia", "EE"),
    BULGARIA("Bulgaria", "BG"),
    SPAIN("Spain", "ES"),
    ROMANIA("Romania", "RO"),
    POLAND("Poland", "PL"),
    UNITED_KINGDOM("United Kingdom", "GB"),
    GERMANY("Germany", "DE"),
    RUSSIA("Russia", "RU"),
    ALGERIA("Algeria", "DZ"),
    ALBANIA("Albania", "AL"),
    KOSOVO("Kosovo", "XK"),
    UKRAINE("Ukraine", "UA"),
    FRANCE("France", "FR");

    private final String countryName;
    private final String countryISOCode;

    CountriesEnum(String countryName, String countryISOCode) {
        this.countryName = countryName;
        this.countryISOCode = countryISOCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }
}
