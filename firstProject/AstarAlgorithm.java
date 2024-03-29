// import java.awt.Taskbar.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

 public class AstarAlgorithm
{
    private final List<State> frontier;
    private final HashSet<State> closedSet;

    /**
     * A* 's constructor
     */
    AstarAlgorithm()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }


    /**
     *
     * @param initialState the root of the tree
     * @return the final state (if the algorithm has found one) or null if the algorithm couldn't respond.
     */
    State Astar(State initialState, int limit)
    {

        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);

        // step 2: check for empty frontier.
        while(!this.frontier.isEmpty())
        {

            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);

            // step 4: if final state, return.
            if(currentState.isFinal()) return currentState;

            // step 5: if the node is not in the closed set, put the children at the frontier.
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                ArrayList<State> children ;
                children = currentState.getChildren();

                State bestState = currentState.HeuristicManager(children);

                if (bestState.getCost() > limit) break ;
                this.frontier.add(bestState);

                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }
}