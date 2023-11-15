import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    {

        State initState = new State(5, false); // Create a State instance (the one given)
        //State initState = new State(500, true); // Create a State instance (a random one)
       
        AstarAlgorithm astar = new AstarAlgorithm();
        
        long start = System.currentTimeMillis();
        State terminalState = astar.Astar(initState);
        long end = System.currentTimeMillis();

        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
			// print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
			path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
			// reverse the path and print.
            Collections.reverse(path);
            for(State st: path)
            {
                System.out.println(st);;
            }
            System.out.println();
            System.out.println("Search time: " + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
        }

    }
}
