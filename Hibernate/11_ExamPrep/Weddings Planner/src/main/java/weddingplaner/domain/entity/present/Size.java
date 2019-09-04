package weddingplaner.domain.entity.present;

public enum Size {
    SMALL("Small"), MEDIUM("Medium"), LARGE("Large"), NOT_SPECIFIED("Not Specified");

    private String value;

    Size(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
