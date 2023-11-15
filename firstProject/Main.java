
public class Main
{
    public static void main(String[] args)
    {

        State initState = new State(5, false); // Create a State instance

        AstarAlgorithm astar = new AstarAlgorithm();
        astar.Astar(initState);
    }
}
