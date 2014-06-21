/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import backpropagation.Backpropagation;
import backpropagation.Network;
import backpropagation.TrainingData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bzzzt
 */
public class FileUtils {
//
//    public static void main(String[] args) {
//        TrainingData dataFromFile = getDataFromFile(new File("D:\\w\\test.csv"));
//    }

    public static TrainingData getDataFromFile(File file) {

        try {
            List<String[]> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] splits = line.trim().split(",");
                    lines.add(splits);
                }
            }
            double[][] inputs = new double[lines.size()][lines.get(0).length];
            double[][] outputs = new double[lines.size()][1];
            if (!lines.isEmpty()) {
                for (int i = 0; i < inputs.length; i++) {
                    String[] s = lines.get(i);
                    inputs[i][0] = Double.parseDouble(s[0]);
                    String sex = s[1];
                    if (sex.equalsIgnoreCase("female")) {
                        inputs[i][1] = 0d;
                        inputs[i][2] = 1d;
                    } else {
                        inputs[i][1] = 1d;
                        inputs[i][2] = 0d;
                    }

                    for (int j = 3; j < inputs[i].length; j++) {
                        try {
                            inputs[i][j] = Double.parseDouble(s[j - 1]);
                        } catch (NumberFormatException e) {
                            System.out.println(Arrays.toString(s) + " " + i);
                        }
                    }
                    outputs[i][0] = Double.parseDouble(s[s.length - 1]);
                }
            }

            return new TrainingData(inputs, outputs);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static TrainingData getIrisData(File file) {
        try {
            List<String[]> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] splits = line.trim().split(",");
                    lines.add(splits);
                }
            }
            double[][] inputs = new double[lines.size()][lines.get(0).length - 3];
            double[][] outputs = new double[lines.size()][3];
            for (int i = 0; i < inputs.length; i++) {
                for (int j = 0; j < lines.get(i).length - 3; j++) {
                    inputs[i][j] = Double.parseDouble(lines.get(i)[j]);
                }
                outputs[i][2] = Double.parseDouble(lines.get(i)[lines.get(i).length - 3]);
                outputs[i][1] = Double.parseDouble(lines.get(i)[lines.get(i).length - 2]);
                outputs[i][0] = Double.parseDouble(lines.get(i)[lines.get(i).length - 1]);
            }
            return new TrainingData(inputs, outputs);
        } catch (IOException e) {
        }
        return null;
    }

    public static TrainingData test() {
        double[][] in = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };
        double[][] out = {
            {0},
            {1},
            {1},
            {0}
        };
        return new TrainingData(in, out);
    }

    public static void saveNet(Network net, String c) {
        File f = new File("net_" + c + ".nn");
        if (f.exists()) {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Backpropagation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
                oos.writeObject(net);
                oos.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Backpropagation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Backpropagation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
