![image](https://github.com/GwgwP/AI-Projects/assets/140728504/ae40c978-41e9-477d-84a2-a12774a7100b)

# Athens University of Economics, Department of Computer Science
## Course: Artificial Intelligence
### Academic Year: 2023–24
### Instructor: I. Androutsopoulos

## First Project - AI (Grade: 10/10)
<details>
<summary>River Crossing Problem</summary>

**Crossing the River.** In this problem, a family needs to cross a river during the night by walking on a log that connects the two banks. The log can support only two people at a time. The family also has only one lamp, which must be carried by one of the individuals walking on the log. Each family member takes a different amount of time to cross the river walking on the log, and this time is constant for each person in either direction.

For example, the grandmother takes the same amount of time each time she crosses, but the grandfather takes a different (yet constant) time. When two family members cross together, the crossing time is that of the slower member. The goal is to find the optimal solution, determining the order and pairs (or individuals in each move) in which the family members should move, to minimize the total crossing time.

Your program should attempt to find the optimal solution based on the number of family members (N) and the time each member takes to cross the river (given as input). You can experiment with different values of N and crossing times and report approximately how much time your program takes to find a solution, depending on the computer used.

Additional examples and variations of the game can be found at the following links:
- [YouTube Video](https://www.youtube.com/watch?v=Ppx7-Y9_ub0)
- [Math Game Time](http://www.mathgametime.com/games/bridge-crossing)

At the start of your program, you can define a maximum allowed total time, making it easier to stop exploring paths that exceed this limit. When your program fails to find a solution within the allowed time, it should simply report that it couldn't find a solution.

Your program should use the A* algorithm with heuristics, which you will describe in the document you submit, explaining your choices. It should be able to find a solution at least for the case presented in the video [YouTube Link](https://www.youtube.com/watch?v=Ppx7-Y9_ub0), where the family consists of the following members with their respective crossing times: Child 1 (time 1), Child 2 (time 3), Mom (time 6), Dad (time 8), Grandfather (time 12). The total crossing time of the solution should be less than or equal to 30.


</details>

## Second Project - Machine Learning
<details>
<summary>Sentiment Analysis for IMDb dataset</summary>



## Part A (60%)
Implement in Java, C++, or Python (or another language permitted by your tutors) two or three (depending on the size of your team) of the following machine learning algorithms. These algorithms should be capable of classifying texts into two (distinct) categories, for example, positive/negative sentiment.

1. Naive Bayes Classifier, using either the Bernoulli multivariate form (as in the slides of the 16th lecture) or the polynomial form (refer to the references at the end of the slides of the 16th lecture).
   
2. Random Forest using ID3 or its variation (e.g., producing trees with a maximum depth specified as a hyperparameter).

3. AdaBoost with decision trees of depth 1, meaning each "tree" will only inquire about the value of a single feature, specifically the one leading to the maximum information gain in the training data of that "tree."

4. Logistic Regression with stochastic gradient ascent, incorporating regularization (refer to slides of the 18th lecture).

Represent each text by a feature vector with values 0 or 1, indicating which words from a vocabulary are present in the text. The vocabulary should include the m most frequent words in the training data, excluding the n most common and the k rarest words in the training data, where m, n, and k are hyperparameters. Optionally, you may enhance the Naive Bayes and Logistic Regression classifiers by adding feature selection using information gain or other methods.

Demonstrate the learning capabilities of your implementations using the "Large Movie Review Dataset," also known as the "IMDB dataset" (see [IMDB Dataset](https://ai.stanford.edu/~amaas/data/sentiment/), [Keras IMDB Dataset](https://keras.io/api/datasets/imdb/)). Include in your report the results of experiments conducted with your implementations on this dataset, showing at least:

- Learning curves and corresponding tables indicating the accuracy percentage on the training data and test data.
 
    **Note 1:** In AdaBoost, during probability calculations for training examples, assume that an example with weight β appears β times in the training examples, even if β is not an integer. This is a function of the number of training examples used in each experiment iteration.

    **Note 2:** The other algorithms already incorporate feature selection methods.

## Part B (20%)
Compare the performance of your implementations with that of other available implementations (e.g., Weka or Scikit-learn) of the same machine learning algorithms implemented in Part A or other machine learning algorithms (e.g., Scikit-learn MLP implementations). Construct the same curves and tables as in Part A. You should compare at least two or three machine learning algorithms (depending on the size of your team).

You can use pre-processing implementations for text (e.g., text splitting into words) and feature selection, as well as ready-made libraries for creating graphs with curves.

## Part C (20%)
Compare the results of Parts A and B with the results of an MLP with a sliding window and/or an RNN implemented in Tensorflow/Keras, representing words with word embeddings. Construct the same curves and tables as in Parts A and B. Also, create curves showing the variation in loss on training and development examples as a function of the number of epochs.

Further clarification will be provided during tutorials. The deadline for submitting the assignment will be announced on e-class. Please carefully read the document with the general instructions for course assignments (see course documents on e-class).

</details>
