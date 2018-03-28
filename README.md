# Deep Learning in Javas Virtual Machine
This project builds a deep neural net with the popular machine learning library [DeepLearning4J](https://deeplearning4j.org/).
Its neural net tries to predict canteens menu for today based on the day of week and the previous meals.

#### Data
The training and test data set were extracted from the official [students service Leipzig](https://www.studentenwerk-leipzig.de/mensen-cafeterien/speiseplan)
web page as plain, unstructured HTML with [JSOUP](https://jsoup.org/). The model so far as simplified, that it only 
holds the meat type for a given menu. Moreover these types were reduced to the three frequently served ones: chicken (*Hähnchen*),
pork (*Schwein*) and beef (*Rind*).

#### Neural net
Although the model learns sequential data, this project uses a fully-connected multi-layer-perceptron, to map this correlation, and leaves out
recurrent neural networks. The below picture shows the network architecture based on the day of week and previous two meals as input vector. 
Thus nets input layer hold eleven neurons and has three output neurons with softmax activation, for classification purpose. Further the net
consists of two hidden layers with 14 neurons in each, where every neuron as an ReLU activation.

![net](docs/net.png)

#### One vs. all
![one vs all](docs/one_vs_all.png)

#### Train and test model
