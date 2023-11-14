import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {

        State initState = new State(5, false); // Create a State instance
        System.out.println("\t\t\t\t\t\tinitial state");
        System.out.println(initState); // Print the state to the console
        ArrayList<State> arr = new ArrayList<>();
        arr = initState.getChildren();
        System.out.println("Megethos children array: " + arr.size());
        
        
        for (State st : arr)
        {
            System.out.println(st);
        }
    }
}
