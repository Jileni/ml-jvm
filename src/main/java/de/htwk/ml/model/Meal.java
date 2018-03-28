package de.htwk.ml.model;

public enum Meal {
    Pork("Schwein", "Pork"),
    Beef("Rind", "Beef"),
    Chicken("Geflügel", "Chicken"),
    Unknown("Unbekannt", "Unknown");

    private String ger;
    private String eng;

    Meal(String ger, String eng) {
        this.ger = ger;
        this.eng = eng;
    }

    public static Meal getType(String name) {
        if (Pork.ger.equals(name) || Pork.eng.equals(name)) {
            return Pork;
        } else if (Chicken.ger.equals(name) || Chicken.eng.equals(name)) {
            return Chicken;
        } else if (Beef.ger.equals(name) || Beef.eng.equals(name)) {
            return Beef;
        }
        return Unknown;
    }
}
