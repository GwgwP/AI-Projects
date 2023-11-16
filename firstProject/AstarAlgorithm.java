// import java.awt.Taskbar.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

 public class AstarAlgorithm
{
    private List<State> frontier;
    private HashSet<State> closedSet;
    
    AstarAlgorithm()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }


    State Astar(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);

        int count = 0;
        // step 2: check for empty frontier.
        while(!this.frontier.isEmpty() && count <=500)
        {
            count++;
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if(currentState.isFinal()) return currentState;

            // step 5: if the node is not in the closed set, put the children at the frontier.
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                ArrayList<State> children = new ArrayList<>();
                children = currentState.getChildren();

                State bestState = currentState.HeuristicManager(children);

                this.frontier.add(bestState);

                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }
}