package de.htwk.ml.util;

import de.htwk.ml.model.Meal;
import de.htwk.ml.model.Menu;
import org.apache.commons.lang3.ArrayUtils;

import java.time.DayOfWeek;
import java.util.List;

public class MenuToVec {

    public static int[][][] convert(List<Menu> menuList, int numberPreMeals) {
        int[][][] data = new int[menuList.size() - numberPreMeals][5 + 3 * numberPreMeals][3];

        for (int i = numberPreMeals; i < menuList.size(); i++) {

            // add day of week
            Menu menu = menuList.get(i);
            DayOfWeek day = menu.getDate().getDayOfWeek();
            int[] dayVec = convertDayToVec(day);
            int[] preVec = new int[]{};

            for (int j = 1; j <= numberPreMeals; j++) {
                Menu m = menuList.get(i - j);
                preVec = ArrayUtils.addAll(preVec, convertMealToVec(m.getFastPlate()));
            }
            int[] input = ArrayUtils.addAll(dayVec, preVec);

            // add target value
            int[] target = convertMealToVec(menu.getFastPlate());
            data[i - numberPreMeals] = new int[][]{input, target};
        }
        return data;
    }

    private static int[] convertDayToVec(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return new int[]{1, 0, 0, 0, 0};
            case TUESDAY:
                return new int[]{0, 1, 0, 0, 0};
            case WEDNESDAY:
                return new int[]{0, 0, 1, 0, 0};
            case THURSDAY:
                return new int[]{0, 0, 0, 1, 0};
            case FRIDAY:
                return new int[]{0, 0, 0, 0, 1};
            default:
                return new int[]{0, 0, 0, 0, 0};
        }
    }

    private static int[] convertMealToVec(Meal meal) {
        switch (meal) {
            case Chicken:
                return new int[]{1, 0, 0};
            case Pork:
                return new int[]{0, 1, 0};
            case Beef:
                return new int[]{0, 0, 1};
            default:
                return new int[]{0, 0, 0};
        }
    }
}
