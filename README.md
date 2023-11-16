# AI-Projects
A repository for Artificial Intelligence projects
## first project
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