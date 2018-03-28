package de.htwk.ml.util;

import de.htwk.ml.model.Menu;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CSVTest {

    @Before
    public void exportMenuListAsCSV() {
        List<Menu> menuList = Html.extract(4, 13);
        assert CSV.export(menuList, "data.csv");
    }

    @Test
    public void importMenuListFromCSV() {
        List<Menu> menuList = Html.extract(4, 13);
        assertEquals(menuList, CSV._import("data.csv"));
    }
}
