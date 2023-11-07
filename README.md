# AI-Projects
A repository for Artificial Intelligence projects
## class STATE
This is a class that represents every state of the problem.
Each state has:
- 3 arrays: 
    - <b>rights</b> : represents which Family members are standing at the right side of the bridge
    - <b>lefts</b> : represents which Family members are standing at the left side of the bridge.
    - <b>operator</b>: keeps track of which family members moved from the one side of the bridge to the other. (dim: max 2 because the tree trunk can hold maximum 2 people at a time).
- torch
    - a boolean variable that represents where is the torch at this moment. (True means rigth & false left)
- dimension
    - how many people we have at the begining of the problem. (User's input). The dimension of lefts & right is the same as the dimension variable.
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

### getChildren
We initialize an List from States named children.
We create a new copy of the current state (so the changes will take place in this copy). 
- fisrt we generate all the possible combinations that could happen ({father, mother}, {father, child} etc).   
- if the torch is at the right side:
    - for each combination we set their father to the current node, we moveLeft() every combination and we add this changed child to the list of the children. Then we restore the initial state of the child so we can create the remaining combinations.
- if the torch is left, same logic. 

### moveLeft
When someone is moved, first of all we set the operator of this child (node) to the members who moved. We can have 1 or 2 members moving, that's why member2 is initialized to null. 
When we are moving left we need to find which to people from the src side (right) want to move. Then we are trying to find an empty spot at the left side to move the family members.
Then, the family members have been moved so we swap the torch.

### isFinal
If everyone is at the right side, we have a final state.

### equals
Used for closedSet to check if we have a same States.

### toString
How we print every state.


