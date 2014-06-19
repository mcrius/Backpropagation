/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backpropagation;

import java.io.Serializable;

/**
 *
 * @author bzzzt
 */
public class Layer implements Serializable {

    Neuron[] neurons;
    private int neuronCount;
    double[] layerInput;
    private int inputCount;

    public Layer() {
    }

    public Layer(int neuronCount, int inputSize) {
        if (neuronCount > 0 && inputSize > 0) {
            neurons = new Neuron[neuronCount];
//            for (Neuron neuron : neurons) {
//                neuron = new Neuron(1d, 1d);
//                neuron.init(inputSize);
//            }
            for (int i = 0; i < neuronCount; i++) {
                neurons[i] = new Neuron(inputSize);
            }
            layerInput = new double[inputSize];
            this.neuronCount = neuronCount;
            this.inputCount = inputSize;
        }
    }

    public int getNeuronCount() {
        return neuronCount;
    }

    public void setNeuronCount(int neuronCount) {
        this.neuronCount = neuronCount;
    }

    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public void calculate() {
        double sum = 0d;

        for (int i = 0; i < this.neuronCount; i++) {
            for (int j = 0; j < this.inputCount; j++) {
                sum += neurons[i].weights[j] * layerInput[j]; // input * weight
            }
            sum += neurons[i].weightGain * neurons[i].gain;
            double sigmoid = 1.0f / (1.0f + Math.exp(-1 * sum));
            neurons[i].output = sigmoid;
        }
    }

}
