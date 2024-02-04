![image](https://sprcdn-assets.sprinklr.com/674/8b955864-7307-4d41-8ded-c194170f5305-2729152590.jpg)

# IMDb reviews classification report 
### Project Developers
Meet the brilliant minds behind this project:

- **Georgia Petsa**
  - *a.m:* 3200155
  - *email:* p3200155@aueb.gr

- **Dimosthenis Plavos**
  - *a.m:* 3200156
  - *email:* p3200156@aueb.gr

### Imports
<details>
  <summary> click to expand</summary>
<p> 
Here we make all the necessary imports from data libraries and machine learning libraries
</p>
</details>

### Fetching data from IMDb
<details>
  <summary>click to expand</summary>

- Code given from the labs
- The `data_fetch` function loads the IMDb sentiment analysis dataset using TensorFlow. It converts the numerical data into human-readable text, including special tokens for padding, beginning of sentence, and out-of-vocabulary words. This function is essential for preprocessing and obtaining the training and testing data for sentiment analysis tasks.
</details>

### Vectorizing examples
<details>
  <summary> click to expand</summary>

The `vectorize_examples` function utilizes CountVectorizer from scikit-learn to convert text data into binary vectors. It accepts a predefined `vocabulary` and the training data, producing binary feature vectors. This function transforms textual input into a format suitable for machine learning models.
</details>

### Information Gain
<details>
  <summary> click to expand</summary>

- Code given from the labs
- The `calculate_ig` function computes the Information Gain (IG) for a binary feature in a dataset with corresponding class labels (positive and negative category). It employs the concept of entropy to quantify the uncertainty in the classes. By evaluating the impact of the binary feature on reducing this uncertainty, IG measures the effectiveness of the feature in classifying examples. (e.x the information gain of word "bad" should be high).
</details>

### Vocabulary Creation Process
<details>
  <summary> click to expand</summary>

The `create_vocabulary` function is responsible for generating a the vocabulary for the reviews based on Information Gain (IG) criteria.

**In detail:**

1. **Word Frequency Calculation:**
   - Iterate through each review in the training data (`x_train`).
   - Count the frequency of each distinct word using a dictionary (`words_frequency_dict`).

2. **Removal of Special Words:**
   - Remove specific words like `[bos]`, `[pad]`, and `[oov]` from the dictionary.

3. **Sorting by Frequency:**
   - Sort the remaining words based on their frequency in descending order.
   - Exclude the top `n` and bottom `k` words from consideration.
   - Keep `m` of them

4. **Information Gain Calculation:**
   - Vectorize the remaining words using the `vectorize_examples` function (`calculate_ig` takes a binary feature).
   - Calculate Information Gain for each word by invoking the `calculate_ig` function.
   - Store the results in a new dictionary (`IG_dict`).

5. **Sorting by IG:**
   - Sort the words based on Information Gain in descending order.
   - keep the `l` most useful words, creating the final vocabulary.

By calculating the IG we improve feature selection for the next machine learning models.
</details>

## Naive Bayes Classifier
<details>
  <summary> click to expand</summary>

The `NaiveBayesCustom` class implements a simple Naive Bayes classifier.
### Initialization
- The class is initialized with attributes for storing the prior probabilities of classes (`class0_prob` and `class1_prob`) and feature probabilities (`feature_probs`).

### Training (`fit` method)
- The `fit` method trains the model using binary training data (`x_train_binary`) and corresponding labels (`y_train`).
- It calculates the prior probabilities of classes and the likelihood of features given each class.
- Laplace smoothing with a factor of 1 is applied to handle unseen features.

For optical representation lets assume that the training examples are these:
```python
X = np.array([
    [1, 0, 1, 0],  # Feature set for example 1
    [0, 1, 1, 1],  # Feature set for example 2
    [1, 1, 0, 0],  # Feature set for example 3
    [0, 0, 1, 1],  # Feature set for example 4
])

y = np.array([0, 1, 0, 1])  # Labels (0 for negative, 1 for positive)

```
```python
# Select samples belonging to class 0,1
        X_0 = []
        X_1 = []  

        for i in range(x_train_binary.shape[1]):
            if y_train[i] == 0:
                X_0.append(x_train_binary[i])
            else:
                X_1.append(x_train_binary[i])
            
        # Convert lists to numpy arrays
        X_0 = np.array(X_0)
        X_1 = np.array(X_1)

```
this code snippet would contain at X_0 2 of the prior examples. That is:
```python
[1, 0, 1, 0],
[1, 1, 0, 0]
```
Then, calculate the Laplace-smoothed probabilities for each feature being 1 given class 0:
```python
self.feature_probs[0] = (X_0.sum(axis=0) + 1) / (len(X_0) + 2)
# [(2+1)/4,(1+1)/4,(1+1)/4,(0+1)/4]
```
In the end the smooth probabilities for class 0 would be
```pytoh 
[3/4, 2/4, 2/4, 1/4]
```

### Prediction (`predict` method)
- The `predict` method predicts class labels for binary test data (`x_test_binary`). It returns a list 0s and 1s meaning the test data belong to category 0 or 1.
- It calculates the log probabilities for each class based on the learned model.
- The class with the higher log probability is chosen as the predicted class for each test instance.
- In case of a tie, the class with the higher prior probability is selected.

This Naive Bayes implementation is suitable for binary classification tasks and incorporates Laplace smoothing to enhance robustness.
#### In detail:

The following code calculates log probabilities in the Naive Bayes classifier:

```python
feature_prob_0 = np.log(self.feature_probs[0])
feature_prob_1 = np.log(self.feature_probs[1])
```
Here, feature_prob_0 and feature_prob_1 store the logarithms of the feature probabilities for class 0 and class 1, respectively, addressing small probabilities and preventing numerical underflow.
```python
feature_prob_0 = np.sum(feature_prob_0 * x_test + np.log(1 - np.exp(feature_prob_0) * x_test), axis=0)
feature_prob_1 = np.sum(feature_prob_1 * x_test + np.log(1 - np.exp(feature_prob_1) * x_test), axis=0)
```
These lines compute the log probabilities of features being 0 and 1 for a given test instance (x_test). The formula incorporates the logarithm of the sum of the product of feature probabilities and the test instance. The np.log(1 - np.exp(...)) term is applied to address numerical instability associated with small probabilities.
```python
sum_prob0 = np.log(self.class0_prob) + feature_prob_0
sum_prob1 = np.log(self.class1_prob) + feature_prob_1
```

Lastly, sum_prob0 and sum_prob1 represent the log probabilities of the test instance belonging to class 0 and class 1, respectively. These values are used in conditional checks to determine the predicted class based on the Naive Bayes model.

(these calculations ensure the stable computation of log probabilities, a crucial step in the prediction process of the Naive Bayes classifier).

</details>


## Custom Logistic Regression
<details>
  <summary> click to expand</summary>

The `CustomLogisticRegression` class implements a logistic regression classifier with a stochastic gradient ascent optimization method. 
### Initialization
- The class is initialized with parameters: λ (`regularizator`),  η (`learning_rate`), and the number of iterations (`n_iterations`).
- Weights are set to `None` initially.

### Sigmoid Function
- The `sigmoid` function computes the sigmoid (logistic) function.

### Training (`fit` method)
- The `fit` method trains the model using binary training data (`x_train_binary`) and corresponding labels (`y_train`).
- The code includes shuffling of training data, regularization, and stopping criteria to prevent overfitting.
- It shuffles the training data and iterates through examples, updating weights using stochastic gradient ascent.
- The training process aims to maximize the log-likelihood of the data.
- The training loop stops if the model achieves a set number of iterations (`n_iterations`) or if there is no improvement in accuracy for a certain number of `epochs` (33).
- The best weights and iteration with the highest accuracy are stored.
- After training, the final weights of the model are accessible through the `weights` attribute.

### Prediction (`predict` method)
- The `predict` method predicts class labels for binary test data (`x_test_binary`).
- It uses the learned weights from fit method to calculate the dot product and checks the sign of the dot product to classify instances.
</details>


### Hyperparameters
#### Regularizer
<details>
  <summary> click to expand</summary>
  
  - The `find_regularizer` function is designed to identify the optimal regularization parameter for the custom logistic regression model. It is used to prevent overfitting by adding a "penalty" term to the model's cost function. 
  This function performs an iterative search within the specified regularization range (0 to 1) to find the regularization parameter that results in the highest accuracy on a validation dataset.
</details>

#### Learning rate 
<details>
  <summary> click to expand</summary>
  
  - The `learning_rate` is fixed at 0.001 
</details>

### Bidirectional GRU RNN class
<details>
<summary> click to expand</summary>

- `Embedding Layer`: The emb_size parameter determines the size of the word embeddings, and it initializes the embedding layer with pre-trained weights.
- `GRU Layers`: The architecture consists of multiple - Bidirectional GRU layers (num_layers). These layers capture sequential dependencies bidirectionally.
- `Dropout`: Dropout is applied to regularize the model and prevent overfitting.
- `Output Layer`: The model ends with a Dense layer with a sigmoid activation function.

**Model: Bidirectional GRU model**

*Algorithmic Explanation:*
- `Input Layer:` The method starts by defining an input layer for text data.
- `Tokenization and Embedding:` It tokenizes the input text using the provided vectorizer and embeds the tokens using an embedding layer.
- `Bidirectional GRU Layers:` The method then constructs Bidirectional GRU layers, with each layer processing sequences bidirectionally.
- `Dropout Layer:` Dropout is applied to the output of the GRU layers for regularization.
- `Output Layer:` The final output is obtained through a Dense layer with a sigmoid activation function.
- `Model Compilation:` The resulting model is compiled with binary cross-entropy loss and some metrics.

**fit Method**

*Algorithmic Explanation:*
- `Model Compilation:` The method starts by compiling the Bidirectional GRU model, specifying the loss function, optimizer, and evaluation metrics.
- `Training:` It then fits the model to the provided training data (x_train_b and y_train_b), performing the specified number of epochs.

**predict Method**

*Algorithmic Explanation:*

- `Prediction:` The method takes the trained Bidirectional GRU model and predicts the labels for the provided test data (x_test_b).
- `Rounding:` The predicted probabilities are rounded to 0 or 1, making them suitable for binary classification.
</details>



### Curves
<details>
  <summary> click to expand</summary>

  - The `learning_curves` function is designed to visualize the learning curves of the predictive models by calculating and plotting various performance metrics at different training set sizes. It takes a machine learning predictor and evaluates its performance on training and testing sets while gradually increasing the size of the training set in multiple iterations.
  - **Algorithm:** Initialize variables and lists for storing performance metrics.
Divide the training set into subsets with increasing sizes, determined by the n_splits parameter.
For each iteration:
Train the predictor on the current subset of the training set.
Make predictions on both the current subset and the entire testing set.
Calculate various performance metrics (accuracy, precision, recall, F1 score) for both the training and testing sets.
Append the metrics to their respective lists.
Update the reporting table and display it using pandas.
Plot learning curves for `accuracy`, `precision`, `recall`, and `F1 score`.
Return the lists of performance metrics for each iteration.

*info:* the lists returned are being used by `make_comparisons` to compare the results of 2 different machine learning algorithms. 

*Accuracy:* correct decisions/total decisions

*Precision:* How many of the instances classified in the class (true positives + false positives) are true members of the class (true positives).
Precision = TP/(TP+FP)

*Recall:* How many of the true members of a class (true positives + false negatives) are classified in the class (true positives). 
Recall = TP/(TP+FN)

*F1:* Combination of precision and recall (weighted harmonic mean).

### Loss Plot
The provided `loss_plot` function generates a visual representation of the training and validation loss over epochs, aiding in the assessment of model performance.


### Comparisons

**Description:**
The `make_comparisons` function is designed to visualize the differences in performance metrics between two different machine learning models at various training set sizes. It takes as input the performance metrics obtained from the learning_curves function (mentioned before) for two different models, the corresponding training sizes, and additional parameters for customization. The differences in accuracy, precision, recall, and F1 score between the two models are calculated and presented in a heatmap

**Algorithm:**
Calculate the differences in performance metrics between the two models for both the training and testing sets.
Create a 2D array (`diff_scores`) with the calculated difference values.
Plot a heatmap using seaborn to visually represent the differences in performance metrics at different training set sizes.
Customize the plot title based on the specified comparison scenario. Then, display the heatmap.
</details>

## Fetching data before Training the models:
 This code fetches the data from IMDb, prepares them for the above machine learning models by creating a vocabulary by using `create_vocabulary` mentioned above, and then vectorizing the text examples into binary feature vectors using the created vocabulary.

### Training and Testing Naive Bayes
<details>
<summary> click to expand</summary>

#### 1a. Running Custom Naive Bayes.
this code snippet trains our custom Naive Bayes classifier on binary vectorized text data and provides a detailed classification report for both the training and testing sets. 

#### 1b. Printing Curves and table for Custom Naive Bayes
#### 2a. Training and Testing Bernoulli Naive Bayes (from sickit-learn)
this code snippet trains the Naive Bayes classifier on binary vectorized text data and provides a detailed classification report for both the training and testing sets.

#### 2b. Printing Curves and table for Bernoulli Naive Bayes (from sickitlearn)

#### 3. Comparisons:
here we print the heatmap for our Custom Naive Bayes and BernoulliNB

</details>

### Training and Testing Logistic Regression
<details>
<summary> click to expand</summary>

#### 1a. Running Custom Logistic Regression
this code snippet trains our custom Logistic regression classifier, and finds out the best regularization factor (λ). 

*How?*  It involves splitting the training data into training and development sets, then searching for the optimal regularization parameter using the `find_regularizer` function.

#### 1b. Printing Curves and table for Logistic Regression with Stochastic Gradient Ascent
#### 2a. Training and Testing Logistic Regression (from sickit-learn)
this code snippet trains the Logistic Regression classifier on binary vectorized text data and provides a detailed classification report for both the training and testing sets.

#### 2b. Printing Curves and table for Bernoulli Naive Bayes (from sickitlearn)

#### 3. Comparisons:
here we print the heatmap for our Logistic Regression classifier and LogisticRegression
</details>

### Training the RNN model
<details>
<summary> click to expand</summary>

1. Preparing the data for the RNN model by estimating the sequence length, determining the appropriate sequence length for padding, and creating a text vectorizer. Let's break down the steps:
      - Explanation: The code calculates the average length of all reviews in the training data (x_train).

2. Embedding layer creates word embeddings by mapping integer encoded sequences to dense vectors, so we first convert the examples (sequences) to integer vectors

3. After these, we train our RNN model and we make the predictions for the sentiment analysis.
</details>

### Testing the RNN model
<details>
<summary> click to expand</summary>

1. We print the curves and table for accuracy, precision, recal and F1 score of our model.
2. We print the loss plot of our model.
</details>

### Comparing the models
<details>
<summary> click to expand</summary>

1. We print the comparisons (heatmap) for Custom Naive Bayes vs RNN .
2. We print the comparisons (heatmap) for Custom Logistic Regression vs RNN.
</details>





