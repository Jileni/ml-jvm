package de.htwk.ml.model;

import java.time.LocalDate;

public class Menu {
    private final Meal fastPlate;
    private final LocalDate date;

    public Menu(Meal fastPlate, LocalDate date) {
        this.fastPlate = fastPlate;
        this.date = date;
    }

    public Meal getFastPlate() {
        return fastPlate;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        Menu m = (Menu) o;
        return this.date.equals(m.getDate())
                && this.fastPlate.equals(m.getFastPlate());
    }
}
