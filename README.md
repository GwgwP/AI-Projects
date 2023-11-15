# AI-Projects
A repository for Artificial Intelligence projects
## class STATE
This is a class that represents every state of the problem.
Each state has:
- 2 arrays: 
    - <b>rights</b> : represents which Family members are standing at the right side of the bridge
    - <b>lefts</b> : represents which Family members are standing at the left side of the bridge.
- operator: List that keeps track of which family members moved from the one side of the bridge to the other.
- torch
    - a boolean variable that represents where is the torch at this moment. (True means rigth & false left)
- dimension
    - how many people we have at the begining of the problem. (User's input). The dimension of lefts & right is the same as the dimension variable.
- heuristicCost
    - every state has its own heuristic cost (that is, the cost from the current node (state) until the final state).
- father
    - the State (node) that led to the current state (current node).

### Constructor 
- Takes as input a dimension (how many people we will have and a randomized boolean variable)
    - if randomized is set we create random family members. The different members are 10 so that's why we have an if(dimension <=10) statement. 
        - if the dimension is <=10 we create unique family mambers
        - if not, we can have multiple fathers for example etc.
    -if not, we create the standard family as it is given.
- <b>Copy contructor</b> for creating a copy of a state whenever we create the children.

### Heuristic Manager
- This is a method for managing the 2 heuristics & returning the best state
- min -> for choosing the min f 
- f -> the actual cost until this state + the heuristic cost
- first of all, we look at every child of the current state and we call a different heuristic based on the side that we are now. 
- we choose the max because this means we have the most accurate heuristic cost (because we always underestimate the real cost until the final state, so the max is more close to the real).
- we set the heuristic cost to the max of the current state that we found  
- finally we keep find the best state as the minimum f because this is the best state to expand.

### Heuristic1
- This heuristic is called when the torch is on the right side, so we need only one "crossing".
- We created this heuristic removing the restriction that at max 2 people can cross the bridge.
- All the family can cross the bridge and the cost until a final state is the time of the member who needs the maximum time.

### Heuristic2
- This heuristic is called when the torch is on the left side, so we need 2 "crossings" (Someone has to go on the right side with the torch and take back the remaining family members).
- We created this heuristic removing the restriction that at max 2 people can cross the bridge.
- All the family can cross the bridge and the cost (maxR) is the time of the member who needs the maximum time.
- Also, someone has to return and take them back and we always take the minimun time (minL) from the people that are on the left side (so we always don't overestimate)
- We return the final heuristic cost that is the sum of miL + maxR.


### getChildren
We initialize an List from States named children.
We create a new copy of the current state (so the changes will take place in this copy). 
- fisrt we generate all the possible combinations that could happen ({father, mother}, {father, child} etc).   
- if the torch is at the right side:
    - we always need exactly 2 people crossing the bridge
    - for each combination we set their father to the current node, we moveLeft() every combination, we set their operator to the moved members and we add this changed child to the list of the children. Then we restore the initial state of the child so we can create the remaining combinations.
- if the torch is left, same logic but this time we choose the combinations that have only 1 person moving. 

### moveLeft
We can have 1 or 2 members moving, that's why member2 is initialized to null. 
When we are moving left we need to find which to people from the src side (right) want to move. Then we are trying to find an empty spot at the left side to move the family members.
Then, the family members have been moved so we swap the torch.
### moveRight
Same logic NEEDS CHECK

### isFinal
If everyone is at the right side, we have a final state.

### equals
Used for closedSet to check if we have a same States.
2 States are the same when we have checked that the people at the rigt and the left side are the same with those of the second's state. We add all the initial's state unique family member's id to a set, we do the same for the second state and we return if the sets of unique family member codes are the same for each array.
  

### toString
How we print every state.

### compareTo
for comparing which state is better we look at the min heuristic cost (used from the Astar when doing the sorting).


