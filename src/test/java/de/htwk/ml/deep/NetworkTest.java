package de.htwk.ml.deep;

import de.htwk.ml.model.Meal;
import de.htwk.ml.model.Menu;
import de.htwk.ml.util.Html;
import de.htwk.ml.util.MenuToVec;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest {

    private static Network model;
    private static DataSet testDs;
    private static List<String> prediction;

    @BeforeClass
    public static void prepareTestData() {
        final int preMeals = 4;
        model = new Network(preMeals);

        final List<Menu> menus = Html.extract(11, 12);
        menus.remove(0);
        final int[][][] menusVec = MenuToVec.convert(menus, preMeals);

        int nbrTest = 5;
        int inputSize = menusVec[0][0].length;
        int targetSize = menusVec[0][1].length;

        INDArray input = Nd4j.zeros(nbrTest, inputSize); // row x col
        INDArray labels = Nd4j.zeros(nbrTest, targetSize);

        for (int row = 0; row < nbrTest; row++) {
            int[][] menuVec = menusVec[row];
            for (int col = 0; col < inputSize; col++) {
                input.putScalar(new int[]{row, col}, menuVec[0][col]);
            }
            for (int col = 0; col < targetSize; col++) {
                labels.putScalar(new int[]{row, col}, menuVec[1][col]);
            }
        }
        testDs = new DataSet(input, labels);

        List<String> names = new ArrayList<>();
        names.add(0, Meal.Chicken.toString());
        names.add(1, Meal.Pork.toString());
        names.add(2, Meal.Beef.toString());
        testDs.setLabelNames(names);
        System.out.println(testDs);

        MultiLayerNetwork net = model.getNet();
        prediction = net.predict(testDs);
        System.out.println(prediction);
    }

    @Test
    public void predictionOnMondayShouldBePork() {
        assert (Meal.getType(prediction.get(0)) == Meal.Pork);
    }

    @Test
    public void predictionOnTuesdayShouldBeBeef() {
        assert (Meal.getType(prediction.get(1)) == Meal.Beef);
    }

    @Test
    public void predictionOnWednesdayShouldBeChicken() {
        assert (Meal.getType(prediction.get(2)) == Meal.Chicken);
    }

    @Test
    public void predictionOnThursdayShouldBePork() {
        assert (Meal.getType(prediction.get(3)) == Meal.Pork);
    }

    @Test
    public void predictionOnFridayShouldBeChicken() {
        assert (Meal.getType(prediction.get(4)) == Meal.Chicken);
    }
}
