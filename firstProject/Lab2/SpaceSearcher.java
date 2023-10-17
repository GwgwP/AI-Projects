import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher
{
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    SpaceSearcher()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }

    State BFS(State initialState)
    {
        // same as slides pseudocode.
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0)
        {
            // step 3: get the first node out of the frontier.
            State currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if (currentState.isFinal())
            {
                return currentState;
            }
            // step 5: put the children at the END of the frontier (queue).
            this.frontier.addAll(currentState.getChildren(0));
        }

        return null;
    }

    State BFSClosedSet(State initialState)
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
            if (currentState.isFinal())
            {
                return currentState;
            }
            // step 5: if the node is not in the closed set, put the children at the END of the frontier (queue).
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.getChildren(0));
            }
        }

        return null;
    }

    State DFSClosedSet(State initialState)
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
            if (currentState.isFinal())
            {
                return currentState;
            }
            // step 5: if the node is not in the closed set, put the children at the START of the frontier (stack).
            // else go to step 2.
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(0, currentState.getChildren(0)); // first argument denotes position of adding.
            }

        }

        return null;
    }

    State BestFS(State initialState, int heuristic)
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
            // step 5: put the children at the frontier
            this.frontier.addAll(currentState.getChildren(heuristic));
            // step 6: sort the frontier based on the heuristic score to get best as first
            Collections.sort(this.frontier);
        }
        return null;
    }

    State BestFSClosedSet(State initialState, int heuristic)
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
                this.frontier.addAll(currentState.getChildren(heuristic));
                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(this.frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }

    // hill climbing
    State HillClimbing(State initialState, int heuristic)
    {
        // step 1: make initial state be the current one.
        State currentState = initialState;
        while(true)
        {
            // step 2: evaluate the children of the current
            ArrayList<State> children = currentState.getChildren(heuristic);
            State bestChild = Collections.min(children); // neighbor with best score
            // step 3: if we do not have a child that has a better score, return the current state
            if(bestChild.getScore() >= currentState.getScore()) // if we have worse score, return
            {
                return currentState;
            }
            // step 4: make the best scored child be the current state
            currentState = bestChild;
            // continue...
        }
    }

    // hill climbing with random restarts. return the best solution.
    // n is the number of runs
    State HillClimbingRR(int n, int heuristic)
    {
        ArrayList<State> bestStates = new ArrayList<>(); // list with solutions of every run
        for(int i = 0; i < n; i++)
        {
            State initialState = new State(3, true); // different initial
            State s = this.HillClimbing(initialState, heuristic);
            bestStates.add(s);
        }
        return Collections.min(bestStates); // best of bests
    }

}
