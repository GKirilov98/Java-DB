package weddingplaner.domain.entity.enums;

public enum Family {
    BRIDEGROOM("Bridegroom"), BRIDE("Bride");

    private String value;

    Family(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
