/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;

import java.io.File;
import reader.FileUtils;

/**
 *
 * @author bzzzt
 */
public class Backpropagation {

//    public final static int PATTERN_COUNT = 4;
//    public final static int PATTERN_SIZE = 2;
//    public final static int NETWORK_INPUTNEURONS = 3;
    public final static int NETWORK_OUTPUT = 1;
    public final static int HIDDEN_LAYERS = 0;
    public final static int EPOCHS = 200000;
//    public final static int EPOCHS = 50000;
    public final static double ERROR_THRESH = 0.01d;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Network best = new Network();
//        TrainingData trainData = FileUtils.getDataFromFile(new File("D:\\w\\train.csv"));
//        TrainingData testData = FileUtils.getDataFromFile(new File("D:\\w\\test.csv"));
//        TrainingData trainData = FileUtils.getIrisData(new File("D:\\w\\iris\\iris.data"));
//        TrainingData testData = FileUtils.getIrisData(new File("D:\\w\\iris\\iris.data"));
        TrainingData trainData = FileUtils.test();
        TrainingData testData = FileUtils.test();
        
        int patternSize = trainData.getInput()[0].length;
        int patternCount = trainData.getInput().length;
        int[] layers = {0};
        
        System.out.println(patternSize);
        System.out.println(patternCount);
        
        
        Network net = new Network();
        net.init(patternCount, patternSize, NETWORK_OUTPUT, layers, HIDDEN_LAYERS);
        train(net, trainData.getOutput(), trainData.getInput());
        //train without hidden layers
        best = net;
        
        
        test(net, testData.getInput(), testData.getOutput());

    }

    private static void saveNet(Network net) {

    }

    private static void train(Network net, double[][] desired, double[][] input) {
        double error = 0;
        int currentEpoch = 0;
        do {

            for (int i = 0; i < input.length; i++) {
                error += net.train(desired[i], input[i], 0.1d, 0d);
            }
            error = error / input.length;

            currentEpoch++;
            if (error < ERROR_THRESH) {
                System.out.println("ERROR: " + error);
                System.out.println(String.format("Threshold reached at epoch %s", currentEpoch));
                break;
            }
            if ((currentEpoch % 100) == 0) {
                System.out.println("Epoch: " + currentEpoch + " Error: " + error);
            }
        } while (EPOCHS > currentEpoch);
    }

    private static double test(Network net, double[][] pattern, double[][] desired) {
        int correctCount = 0;
        for (int i = 0; i < pattern.length; i++) {
            net.propagate(pattern[i]);
            String s = "Desired: %s, Actual: %s";
            System.out.println(String.format(s, desired[i][0], Math.round(net.getOutput().neurons[0].output)));
            if (desired[i][0] == Math.round(net.getOutput().neurons[0].output)) {
                correctCount++;
            }
        }
        double p = ( (float) correctCount / (float) pattern.length) * 100 ;
        System.out.println("Precision: " + p);
        return p;
    }
}
