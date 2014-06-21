/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import reader.FileUtils;

/**
 *
 * @author bzzzt
 */
public class Backpropagation1 {

//    public final static int PATTERN_COUNT = 4;
//    public final static int PATTERN_SIZE = 2;
//    public final static int NETWORK_INPUTNEURONS = 3;
    public final static int NETWORK_OUTPUT = 3;
    public final static int HIDDEN_LAYERS = 1;
    public final static int EPOCHS = 500000;
//    public final static int EPOCHS = 10000;
    public final static double ERROR_THRESH = 0.001d;
    public static double bestPrecision = 0d;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Network best = new Network();
        TrainingData trainData = FileUtils.getIrisData(new File("D:\\w\\iris\\train.data"));
        TrainingData testData = FileUtils.getIrisData(new File("D:\\w\\iris\\test.data"));

        int patternSize = trainData.getInput()[0].length;
//        int patternCount = trainData.getInput().length;

        for (int i = 0; i < 10; i++) {
            for (int j = 1; j < 11; j++) {
                int[] layers = {j};
                Network net = new Network();
                net.init(patternSize, patternSize, NETWORK_OUTPUT, layers, HIDDEN_LAYERS);
                train(net, trainData.getOutput(), trainData.getInput());
                //train without hidden layers
                double p = test(net, testData.getInput(), testData.getOutput());
                if (p > bestPrecision) {
                    FileUtils.saveNet(net, "1_" + j);
                    bestPrecision = p;
                }
            }
        }
        System.out.println(bestPrecision);
    }

    private static void train(Network net, double[][] desired, double[][] input) {
        double error = 0;
        int currentEpoch = 0;
        do {

            for (int i = 0; i < input.length; i++) {
                error += net.train(desired[i], input[i], 0.001d, 0.01d);
            }
            error = error / input.length;

            currentEpoch++;
            if (error < ERROR_THRESH) {
                System.out.println("ERROR: " + error);
                System.out.println(String.format("Threshold reached at epoch %s", currentEpoch));
                break;
            }
//            if ((currentEpoch % 100) == 0) {
//                System.out.println("Epoch: " + currentEpoch + " Error: " + error);
//            }
        } while (EPOCHS > currentEpoch);
    }

    private static double test(Network net, double[][] pattern, double[][] desired) {
        int correctCount = 0;
        for (int i = 0; i < pattern.length; i++) {
            net.propagate(pattern[i]);
            Neuron[] out = net.getOutput().neurons;
            String s = "Desired value = %s, Found = %s";

            if (desired[i][0] == 1.0d && out[0].output > out[1].output && out[0].output > out[2].output) {
//                System.out.println(String.format(s, desired[i][0], out[0].output));
                correctCount++;
            } else {
                if (desired[i][1] == 1.0d && out[1].output > out[0].output && out[1].output > out[2].output) {
//                    System.out.println(String.format(s, desired[i][1], out[1].output));
                    correctCount++;
                } else {
                    if (desired[i][2] == 1.0d && out[2].output > out[0].output && out[2].output > out[1].output) {
//                        System.out.println(String.format(s, desired[i][2], out[2].output));
                        correctCount++;
                    }
                }
            }
        }
        double p = ((float) correctCount / (float) pattern.length) * 100;
        System.out.println("Precision: " + p + " %");
        return p;
    }
}
