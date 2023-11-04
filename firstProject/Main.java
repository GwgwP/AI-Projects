import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {

        State initState = new State(3, false); // Create a State instance
        //State initState = new State(16, true);
        System.out.println("\t\t\t\t\t\tinitial state");
        System.out.println(initState); // Print the state to the console
        //System.out.println(initState.getChildren().get(1));
        //System.out.println(initState.getChildren().get(0).getChildren());
        for (State st : initState.getChildren())
        {
           System.out.println(st);
        
             
        }


    }


}
