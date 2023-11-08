public class Main
{
    public static void main(String[] args)
    {

        State initState = new State(5, false); // Create a State instance
        System.out.println("\t\t\t\t\t\tinitial state");
        System.out.println(initState); // Print the state to the console
       
        for (State st : initState.getChildren())
        {
            System.out.println(st);
        }
    }
}
