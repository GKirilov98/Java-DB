package weddingplaner.domain.entity.enums;

public enum Gender {
     MALE("Male"), FEMALE("Female"), NOTSPECIFIED("NotSpecified");

     private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
