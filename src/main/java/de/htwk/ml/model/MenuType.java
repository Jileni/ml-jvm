package de.htwk.ml.model;

public enum MenuType {
    FastPlate("Schneller Teller"), MainComponent("Hauptkomponente");

    private final String name;

    MenuType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
