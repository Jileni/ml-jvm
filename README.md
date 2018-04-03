# Deep learning in Java's virtual machine
This project builds a deep neural net with the popular machine learning library [DeepLearning4J](https://deeplearning4j.org/).
Its neural net tries to predict canteen menus for today based on the day of week and the previous meals.

#### Data
The training and test data set were extracted with [JSOUP](https://jsoup.org/) from the official 
[students service Leipzig](https://www.studentenwerk-leipzig.de/mensen-cafeterien/speiseplan)
web page as plain, unstructured HTML. The model is so far as simplified, that it only 
holds the meat type for a given menu. Moreover these types were reduced to the three frequently served ones: chicken 
(*Hühnchen*), pork (*Schwein*) and beef (*Rind*). You can find my collected and already extracted data set as CSV in 
[data/menu.csv](https://github.com/erohkohl/ml-jvm/blob/master/data/menu.csv). Alternatively you can download the 
current meal plan on your own and use my 
[de.htwk.ml.util.Html](https://github.com/erohkohl/ml-jvm/blob/master/src/main/java/de/htwk/ml/util/Html.java) 
helper class to parse the data.

#### Neural net
Although the model learns sequential data, this project uses a fully-connected multi-layer-perceptron, to map this 
correlation, and leaves the modern approach with recurrent neural networks out. The picture below shows the network 
architecture based on the day of week (*Montag, Dienstag,* ...) and previous two meals as one-hot encoded input vector.
Thus nets input layer holds eleven neurons and it has three output neurons with softmax activation, for classification 
purpose. Further the net consists of two hidden layers with 14 neurons in each, where every neuron has an ReLU activation. 
The Java class 
[de.htwk.ml.deep.Network](https://github.com/erohkohl/ml-jvm/blob/master/src/main/java/de/htwk/ml/deep/Network.java) 
implements this model with DeepLearning4J.

![net](docs/net.png)

#### Train and test model
To train and test this model, simply run the JUnit test 
[de.htwk.ml.deep.NetworkTest](https://github.com/erohkohl/ml-jvm/blob/master/src/test/java/de/htwk/ml/deep/NetworkTest.java) 
in your IDE or with Maven.

#### One vs. all
For classification neural nets apply an *one vs all* vote, where all neurons in their output layers take part. Therefore 
the weights and biases of output layer neurons form one linear separator in hyper space for each class and compare one 
neurons decision against all others, like the picture below shows for the 2d space. Thus the softmax function serves a 
probability for each class based on neurons vote.

![one vs all](docs/one_vs_all.png)

#### Results
All in all after 20,000 iterations of fitting weights and biases my model reaches an accuracy of over 97% on the training
set and predicts three of five meals of the test set correctly.