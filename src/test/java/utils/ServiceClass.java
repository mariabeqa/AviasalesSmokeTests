package utils;

public enum ServiceClass {
    ECONOMY("Эконом"),
    COMFORT("Комфорт"),
    BUSINESS("Бизнес"),
    FIRST_CLASS("Первый класс");

    private final String name;

    ServiceClass(String name) {
        this.name = name;

    }

    public String getValue() {
        return name;
    }
}
