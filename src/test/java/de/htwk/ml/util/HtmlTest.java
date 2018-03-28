package de.htwk.ml.util;

import de.htwk.ml.model.Menu;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HtmlTest {

    @Test
    public void extractedMenuListShouldHaveCorrectSize() {
        List<Menu> menuList = Html.extract(4, 12);
        assertEquals(45, menuList.size());
    }
}
