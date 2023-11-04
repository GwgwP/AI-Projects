import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

 public class AstarAlgorithm
{
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;
    
    AstarAlgorithm()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }

    // periorismoi
    /***
     * 1) max 2 atoma
     * 2) xronos toy megalyterou
     * 3) metakinisi me fako (ar-deksia)
     * 
     *  idees
     * 
     * 3) syndyasasmoi ceiling (n / 2 ) 
     * 3) posoi einai aristera & posoi einai deksia  
     * 4) an eisai aristera 2n
     * 5) an eisai deskia 2n-3
     * 
     */

    State Astar(State initialState)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0)
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
                ArrayList<State> children = new ArrayList<>();
                children = currentState.getChildren();
                State best = currentState.HeuristicManager(children);
                this.frontier.add(best);
                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(this.frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }


}
