package by.epamtraining.airlines.domain;

public enum Sex {
    FEMALE("Female"), MALE("Male");



    private final String name;

    Sex(String s) {
        name = s;
    }


    @Override
    public String toString() {
        return name;
    }

}
