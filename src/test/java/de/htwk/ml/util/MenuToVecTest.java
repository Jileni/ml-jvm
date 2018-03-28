package de.htwk.ml.util;

import de.htwk.ml.model.Menu;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MenuToVecTest {

    @Test
    public void convertWithTwoPreMeals() {
        final int numberPreMeals = 2;
        List<Menu> menuList = Html.extract(4, 11);
        int[][][] trainData = MenuToVec.convert(menuList, numberPreMeals);
        Arrays.stream(trainData).forEach(i -> {
                    Arrays.stream(i).forEach(j -> System.out.println(Arrays.toString(j)));
                }
        );
        assert trainData.length == menuList.size() - numberPreMeals;
    }

    @Test
    public void convertWithThreePreMeals() {
        final int numberPreMeals = 3;
        List<Menu> menuList = Html.extract(4, 11);
        int[][][] trainData = MenuToVec.convert(menuList, numberPreMeals);

        assert trainData.length == menuList.size() - numberPreMeals;
    }

    @Test
    public void convertWithFourPreMeals() {
        final int numberPreMeals = 4;
        List<Menu> menuList = Html.extract(4, 11);
        int[][][] trainData = MenuToVec.convert(menuList, numberPreMeals);

        assert trainData.length == menuList.size() - numberPreMeals;
    }
}
