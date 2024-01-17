![image](https://github.com/GwgwP/AI-Projects/assets/140728504/2b8194d5-3dfd-4663-a0f1-38e6b4a1b7a9)

# AI-Projects - first project : Family at a bridge 
Developers:

- Georgia Petsa 3200155
- Dimosthenis Plavos 3200156


# Code explanation

## Class State 
<details>
  <summary> click to expand</summary>

The `State` class is a representation of each state in the problem, embodying various attributes and methods to manage state transitions. Here's a breakdown:

## Attributes

- **rights**:
  - An array representing Family members standing on the right side of the bridge.
- **lefts**:
  - An array representing Family members standing on the left side of the bridge.
- **cost**:
    - the actual (real) cost from the root until this state.
- **torch**:
  - A boolean variable indicating the torch's location (True for right, False for left).
- **operator**:
    - A list that tracks which family members moved from one side of the bridge to the other.
- **dimension**:
  - Represents the total number of people at the beginning of the problem (user's input).
  - The dimensions of `lefts` and `rights` arrays are the same as the `dimension` variable.
- **heuristicCost**:
  - Reflects the heuristic cost of the state, representing the cost from the current node to the final state.

- **father**:
  - References the State (node) that led to the current state.

## Functionality

- **Initialization**:
  - Creates an instance of the `State` class with arrays for both sides of the bridge, torch location, dimension, and heuristic cost.

- **Attributes Overview**:
  - `rights` and `lefts` represent the current configuration of Family members on each side of the bridge.
  - `operator` keeps track of the movement of Family members.
  - `cost` the real cost.
  - `torch` signifies the current torch location.
  - `dimension` captures the initial number of people in the problem.
  - `heuristicCost` stores the cost estimation from the current state to the final state.
  - `father` points to the preceding state.


## Constructor

The `State` class features a constructor designed to initialize instances based on user input. Key points include:

- **Input Parameters**:
  - Takes a `dimension` parameter representing the total number of people.
  - Accepts a randomized boolean variable indicating whether to generate random family members.

- **Randomized Generation**:
  - If `randomized` is set:
    - Checks if `dimension` is less than or equal to 10.
      - If true, creates unique family members.
      - If not, creates unique family members with multiple different "child" tags.
  - If `randomized` is not set, creates the standard family configuration as given from the known example.

## Copy Constructor

A **Copy Constructor** is implemented to facilitate the creation of a new state as a copy of an existing one, when generating children. This ensures the preservation of state characteristics without direct reference to the original instance.

## Heuristic Manager Method

The `Heuristic Manager` orchestrates the evaluation of two heuristics and determines the best state based on these heuristic values.

- **Purpose**:
  - Manages two heuristics and returns the state with the best heuristic score.

- **Steps**:
  1. **Initialization**:
      - Initializes variables for minimum (`min`) and actual (`f`) costs.

  2. **Child Iteration**:
      - Iterates through every child of the current state.
      - Calls a different heuristic based on the current side of the bridge.

  3. **Choosing Max Heuristic**:
      - Chooses the maximum heuristic value because it is more accurate, considering that heuristics always should underestimate the real cost.

  4. **Updating Heuristic Cost**:
      - Sets the heuristic cost of the current state to the maximum heuristic found.

  5. **Finding Best State**:
      - Identifies the best state by minimizing the total cost (`f`).


## ======== Heuristic1 ========
It is acceptable and consistent.
### Overview

- **Purpose**:
  - Calculated when the torch is on the right side, requiring only one crossing.

### Steps

1. **No Crossing Restriction**:
   - Removes the restriction of a maximum of 2 people crossing the bridge.

2. **All-Family Crossing**:
   - Allows all family members to cross, and the cost until reaching a final state is the time of the member with the maximum time.

## ======== Heuristic2 ========
It is acceptable and consistent.
### Overview

- **Purpose**:
  - Calculated when the torch is on the left side, necessitating two crossings.

### Steps

1. **No Crossing Restriction**:
   - Eliminates the restriction of a maximum of 2 people crossing the bridge.

2. **All-Family Crossing and Return**:
   - Facilitates the crossing of all family members.
   - Defines the cost (`maxR`) as the time of the member requiring the maximum time.
   - Ensures someone returns and takes back the remaining family members.
   - Determines the cost (`minL`) from the people on the left side with the minimum time.
   - Returns the final heuristic cost as the sum of `minL` and `maxR`.

## ======== Heuristic3 ========
It is acceptable and consistent.
#### NOTE
Heuristic3 is another way of calculation the heuristic cost when the torch is on the right side.
Between Heuristic1 and Heuristic3, we choose the heuristic which does the best approaching to the 
real cost. However, for a high number of iterations, we do not recommend the usage of Heuristic3, because
it increases the execution time (e.g. for 200 members, the execution time with heuristic3 was 45 sec, and without it 2,5 sec.). We added it to show that we can choose among many heuristic functions
the best one.

### Overview


- **Purpose**:
    - Calculated when the torch is on the right side.

### Steps

1. **Not the "Max crossing time counts" restriction**:
    - We choose the minimum cost of each combination.

2. **We take the maximum combination**:
    - We take the best case combinations that could happen, but we choose the min value between the 2 persons.


## getChildren 
The `getChildren` method is responsible for generating and returning a list of child states. Here's an overview:

### Steps

1. **Initialization**:
   - Initializes a list named `children` to store generated child states.
   - Creates a copy of the current state.

2. **Combination Generation**:
   - Generates all possible combinations of family members ({father, mother}, {father, child}, etc.).

3. **Crossing Logic (Torch at Right)**:
   - If the torch is on the right side, enforces the constraint of exactly 2 people crossing.
   - For each combination:
     - Sets the father of the current node.
     - Moves the members according to the combination using the `moveLeft` method.
     - Sets the operator to the moved members and adds this modified child to the list.
     - Restores the initial state of the child for creating remaining combinations.

4. **Crossing Logic (Torch at Left)**:
   - If the torch is on the left side, ensures that only 1 person moves.
   - Similar logic as above but uses the `moveRight` method.

## moveLeft
The `moveLeft` method is designed to move two family members from the right side to the left side. 

### Steps

1. **Selecting Members**:
   - Identifies which two members from the right side want to move.

2. **Finding Empty Spot**:
   - Searches for an empty spot on the left side to move the family members.

3. **Moving Family Members**:
   - Moves the family members to the left side.

4. **Swapping Torch**:
   - Swaps the torch, indicating a change in the side of the bridge.
5. **Cost**
    - increases the cost based on the one who needs the most crossing time

## moveRight Method
The `moveRight` method is analogous to `moveLeft` but is designed for a single member to return from the left side to the right side. It follows the same logic and is called when the torch is on the left side during the child state generation process in `getChildren`.

## isFinal
If everyone is on the right side, we have a final state.

## equals

The `equals` method is employed for checking whether two states are identical. It is primarily used within the `closedSet` to ensure uniqueness. Here's an overview:

- **Purpose**:
  - Checks if two states are the same by comparing the family members on the right and left sides.

- **Steps**:
  1. **Initialization**:
      - Creates sets to store unique family member IDs for both the current and the other state.

  2. **Adding Unique Family Member IDs**:
      - Adds the unique family member IDs from the initial state to the set.
      - Performs the same operation for the second state.

  3. **Comparison**:
      - Checks if the sets of unique family member IDs are the same for both the right and left arrays.

  4. **Result**:
      - Returns `true` if the family members on the right and left sides are the same; otherwise, returns `false`.

## toString
How we print every state.

## compareTo
for comparing which state is better we look at the min heuristic cost (used from the Astar when doing the sorting).
</details>

## class Family
<details>
  <summary> click to expand</summary>

The `Family` class is a Java class that represents a family member of the problem.
It encapsulates the properties and behaviors of a family member, ensuring that each family has a unique identifier and can be represented as a string.

## Attributes:
- name: 
    - Represents the name of the family.
- crossingTime: 
    - Represents the time it takes for the family to cross the bridge.
- id: 
    - Represents a unique identifier for each family member.
- usedIds: 
    - A static set that keeps track of used IDs to ensure uniqueness.
random: 
    - A static instance of the Random class for generating random IDs.

## Constructor:

Takes the family name and crossing time as parameters.
Calls the generateUniqueId method to assign a unique ID to the family.

## Methods:

 - generateUniqueId: 
    - Generates a unique ID for the family using a random number until a unique one is found.

- Getter and setter methods for accessing and modifying the family name and crossing time.

- getId: Returns the unique ID of the family.
- toString: Overrides the toString method to provide a string representation of the family (returns the family name).
</details>

## Class Main
<details>
  <summary> click to expand</summary>  

The `Main` class in Java serves as the entry point for the program. Here is a brief summary of its functionality:

## Main Method

- **Initialization**: Creates an instance of the `State` class, representing the initial state for the A* algorithm.

- **Algorithm Execution**: Initializes an instance of the `AstarAlgorithm` class and executes the A* algorithm to find a solution.

- **Path Printing**: If a solution is found, it prints the path from the initial state to the goal state. If not, it prints that the algorithm could not find a solution.


- **Timing**: Measures and prints the total search time in seconds.


### Note

- The class utilizes the A* algorithm (`AstarAlgorithm`) and works with instances of the `State` class to find a solution path and measure the search time.
- The initial state can be either a predefined one (`new State(5, false)`) or a randomly generated state (`new State(500, true)`).
- The solution path is printed in reverse order from the goal state to the initial state.
</details>

## Class AstarAlgorithm  
<details>
  <summary> click to expand</summary>

The `AstarAlgorithm` class in Java implements the A* algorithm for searching and finding a solution path.

## Class Structure

The `AstarAlgorithm` class includes:

- **Attributes**:
  - `frontier`: A list to store the states yet to be explored.
  - `closedSet`: A set to keep track of states that have already been explored.

- **Constructor**:
  - Initializes the `frontier` as an empty list and `closedSet` as an empty HashSet.

- **Astar Method**:
  - The main A* algorithm implementation.
  - Takes an initial state as a parameter and returns the terminal state (goal) if found.

## Astar Method

- **Initialization**:
  - Adds the initial state to the frontier.


- **Exploration Loop**:
  - Continues exploring until the frontier is empty or a specified limit is reached.
  - The limit is the addition of the values of the crossing time * 2.
  - In case A* algorithm failed, if the final cost occured to be higher than the limit
  - we definitely have a problem.
  - Otherwise, we could use a limit of iterations.
  ```java
  if (bestState.getCost() > limit) break;
  ```

- **Exploration Steps**:
  1. Removes the first state from the frontier.

  2. Checks if the current state is the final state; if so, returns the state.

  3. Expands the current state's children and selects the best state based on heuristics.
  
  4. Adds the best state to the frontier if it's not in the closed set.

- **Termination**:
  - If the frontier becomes empty or the iteration limit is reached, returns `null` indicating no solution.

This class manages the A* algorithm's exploration process, maintaining a frontier of states and a closed set to avoid redundant exploration. The heuristic-based selection of states guides the search toward an optimal solution.
</details>
