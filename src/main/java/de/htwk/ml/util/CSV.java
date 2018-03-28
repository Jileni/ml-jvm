package de.htwk.ml.util;

import de.htwk.ml.model.Meal;
import de.htwk.ml.model.Menu;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private static final String PATH = "data/";

    public static boolean export(List<Menu> menuList, String fileName) {
        try {
            BufferedWriter w = Files.newBufferedWriter(Paths.get(PATH + fileName));
            CSVPrinter p = new CSVPrinter(w, CSVFormat.DEFAULT.withHeader("date", "menu"));
            for (int i = 0; i < menuList.size(); i++) {
                Menu m = menuList.get(i);
                p.printRecord(m.getDate(), m.getFastPlate());
            }
            p.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<Menu> _import(String fileName) {
        List<Menu> menuList = new ArrayList<>();
        try {
            Reader r = Files.newBufferedReader(Paths.get(PATH + fileName));
            CSVParser p = new CSVParser(r,
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim());
            p.getRecords().forEach(m -> {
                Meal t = Meal.getType(m.get("menu"));
                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(m.get("date"), f);
                menuList.add(new Menu(t, date));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuList;
    }
}
