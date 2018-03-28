package de.htwk.ml.util;

import de.htwk.ml.model.Meal;
import de.htwk.ml.model.Menu;
import de.htwk.ml.model.MenuType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Html {
    private static final String PATH = "data/kw";

    public static List<Menu> extract(int startWeek, int endWeek) {
        List<Menu> menuList = new ArrayList<>();
        DirectoryStream<Path> stream = null;
        try {
            for (int kw = startWeek; kw <= endWeek; kw++) {
                Path dir = Paths.get(PATH + kw);
                stream = Files.newDirectoryStream(dir, "*.{html}");
                for (Path p : stream) {
                    Menu m = extractMenu(p);
                    if (m.getFastPlate() != null) {
                        menuList.add(m);
                    } else {
                        menuList.add(new Menu(Meal.Unknown, m.getDate()));
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return menuList;
    }

    private static Menu extractMenu(Path p) {
        String html = null;
        try {
            html = Files.readAllLines(p).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);
        String[] s = doc.getElementsByClass("meals").eachText().get(0).split(",");
        Meal fastPlate = null;
        for (int i = 0; i < s.length; i++) {
            if (s[i].trim().equals(MenuType.FastPlate.getName())) {
                fastPlate = Meal.getType(s[i + 19].toString().trim());
                break;
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(p.getFileName().toString().subSequence(0, 10), formatter);

        return new Menu(fastPlate, date);
    }
}
