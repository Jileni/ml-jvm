package de.htwk.ml.deep;

import de.htwk.ml.model.Menu;
import de.htwk.ml.util.Html;
import de.htwk.ml.util.MenuToVec;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.List;

public class Network {
    private final MultiLayerNetwork net;
    private final int N_EPOCHS = 20000;
    private final double LEARNING_RATE = 0.01;

    public Network(int preMeals) {
        List<Menu> menus = Html.extract(4, 11);
        int[][][] menusVec = MenuToVec.convert(menus, preMeals);
        int trainNbr = menusVec.length;
        int inputSize = menusVec[0][0].length;
        int targetSize = menusVec[0][1].length;

        INDArray input = Nd4j.zeros(trainNbr, inputSize); // row x col
        INDArray labels = Nd4j.zeros(trainNbr, targetSize);

        for (int row = 0; row < trainNbr; row++) {
            int[][] menuVec = menusVec[row];
            for (int col = 0; col < inputSize; col++) {
                input.putScalar(new int[]{row, col}, menuVec[0][col]);
            }
            for (int col = 0; col < targetSize; col++) {
                labels.putScalar(new int[]{row, col}, menuVec[1][col]);
            }
        }

        DataSet ds = new DataSet(input, labels);
        System.out.println(ds);

        int neuronsHiddenLayer = 32;

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .iterations(N_EPOCHS)
                .learningRate(LEARNING_RATE)
                .seed(123)
                .optimizationAlgo(OptimizationAlgorithm.LINE_GRADIENT_DESCENT)
                .biasInit(0)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(neuronsHiddenLayer)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(neuronsHiddenLayer)
                        .nOut(neuronsHiddenLayer)
                        .activation(Activation.RELU)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.SQUARED_LOSS)
                        .nIn(neuronsHiddenLayer)
                        .nOut(targetSize)
                        .activation(Activation.SOFTMAX)
                        .build())
                .build();

        net = new MultiLayerNetwork(conf);
        net.init();
        net.fit(ds);

        INDArray output = net.output(ds.getFeatureMatrix());
        System.out.println(output);

        Evaluation eval = new Evaluation(targetSize);
        eval.eval(ds.getLabels(), output);
        System.out.println(eval.stats());
    }

    public MultiLayerNetwork getNet() {
        return net;
    }
}