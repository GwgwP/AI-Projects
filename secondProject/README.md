# IMDB reviews classification report 
### Project Developers
Meet the brilliant minds behind this project:

- **Georgia Petsa**
  - *a.m:* 3200155
  - *email:* p3200155@aueb.gr

- **Dimosthenis Plavos**
  - *a.m:* 3200156
  - *email:* p3200156@aueb.gr
---
### The code is organized as it follows 
----
## imports
<details open>
  <summary> click to expand</summary>
<p> 
Here we make all the necessary imports from data libraries and machine learning libraries
</p>
</details>

## Fetching data from imdb
<details open>
  <summary>click to expand</summary>

- Code given from the labs
- The `data_fetch` function loads the IMDb sentiment analysis dataset using TensorFlow. It converts the numerical data into human-readable text, including special tokens for padding, beginning of sentence, and out-of-vocabulary words. This function is essential for preprocessing and obtaining the training and testing data for sentiment analysis tasks.
</details>

## Vectorizing examples
<details open>
  <summary> click to expand</summary>

The `vectorize_examples` function utilizes CountVectorizer from scikit-learn to convert text data into binary vectors. It accepts a predefined `vocabulary` (see above) and the training data, producing binary feature vectors. This function transforms textual input into a format suitable for machine learning models.
</details>

## Information Gain
<details open>
  <summary> click to expand</summary>

- Code given from the labs
- The `calculate_ig` function computes the Information Gain (IG) for a binary feature in a dataset with corresponding class labels (positive and negative category). It employs the concept of entropy to quantify the uncertainty in the classes. By evaluating the impact of the binary feature on reducing this uncertainty, IG measures the effectiveness of the feature in classifying examples. (e.x the information gain of word "bad" should be high) The function considers the distribution of classes and the occurrences of feature values, providing a valuable metric for feature selection in machine learning applications, particularly in decision tree algorithms.
</details>

# Vocabulary Creation Process
<details open>
  <summary> click to expand</summary>

The `create_vocabulary` function is responsible for generating a the vocabulary for the reviews based on Information Gain (IG) criteria. Here's a breakdown of the code:

1. **Word Frequency Calculation:**
   - Iterate through each review in the training data (`x_train`).
   - Count the frequency of each distinct word using a dictionary (`words_frequency_dict`).

2. **Removal of Special Words:**
   - Remove specific words like `[bos]`, `[pad]`, and `[oov]` from the dictionary.

3. **Sorting by Frequency:**
   - Sort the remaining words based on their frequency in descending order.
   - Exclude the top `n` and bottom `k` words from consideration.

4. **Information Gain Calculation:**
   - Vectorize the remaining words using the `vectorize_examples` function (`calculate_ig` takes a binary feature).
   - Calculate Information Gain for each word by invoking the `calculate_ig` function.
   - Store the results in a new dictionary (`IG_dict`).

5. **Sorting by IG:**
   - Sort the words based on Information Gain in descending order.
   - keep the `l` most useful words, creating the final vocabulary.

By calculating the IG we improve feature selection for the next machine learning models.
</details>

# Naive Bayes Classifier
<details open>
  <summary> click to expand</summary>

The `NaiveBayesCustom` class implements a simple Naive Bayes classifier.
### Initialization
- The class is initialized with attributes for storing the prior probabilities of classes (`class0_prob` and `class1_prob`) and feature probabilities (`feature_probs`).

### Training (`fit` method)
- The `fit` method trains the model using binary training data (`x_train_binary`) and corresponding labels (`y_train`).
- It calculates the prior probabilities of classes and the likelihood of features given each class.
- Laplace smoothing with a factor of 1 is applied to handle unseen features.

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


# Custom Logistic Regression
<details open>
  <summary> click to expand</summary>

The `CustomLogisticRegression` class implements a logistic regression classifier with a stochastic gradient ascent optimization method. 
### Initialization
- The class is initialized with parameters: λ (`regularizator`),  η (`learning_rate`), and the number of iterations (`n_iterations`).
- Weights are set to `None` initially.

### Sigmoid Function
- The `sigmoid` function computes the sigmoid (logistic) function, a crucial part of logistic regression.

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

This custom logistic regression implementation is designed for binary classification tasks with a focus on optimizing performance and robustness.
</details>