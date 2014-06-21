/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backpropagation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author bzzzt
 */
public class Neuron implements Serializable{
    double[] weights;
    double[] deltas;
    double output;
    double gain;
    double weightGain;

    public Neuron() {
    }

    public Neuron(double gain, double weightGain) {
        this.output = 0d;
        this.gain = gain;
        this.weightGain = weightGain;
    }
    
    public Neuron(int count){
        if (count > 0) {
            Random r = new Random();
            this.weights = new double[count];
            this.deltas = new double[count];
            
            for (int i = 0; i < count; i++) {
                
                this.weights[i] = (0.001 - 0.0001) * r.nextDouble() + 0.0001;
                deltas[i] = 0d;
            }
            this.gain = 1.0d;
            this.weightGain = (0.001 - 0.0001) * r.nextDouble() + 0.0001;
        }
    }
}
