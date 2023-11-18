import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        State initState;
        Scanner scanner = new Scanner(System.in);

        displayWelcomeMessage();


        // Take user input
        boolean isRandomized = getValidRandomizedBoolean(scanner);
        if (!isRandomized) {
            initState = new State(5, false);
        }
        else {
            int dimension = getValidDimension(scanner);
            initState = new State(dimension, true);
        }

        // COMMENT OUT LINES 11-23 FOR FASTER CODE EXECUTION AND COMMENT IN LINES 27 OR 28.

        //State initState = new State(15, true); // Create a State instance (the one given)
        //State initState = new State(6, true); // Create a State instance (a random one)
       
        AstarAlgorithm astar = new AstarAlgorithm();

        long start = System.currentTimeMillis();

        int limit =0;
        for (int i=0; i<initState.getDimension(); i++){
            limit += initState.getRights()[i].getCrossingTime();
        }
        limit*=2;

        State terminalState = astar.Astar(initState,limit);
        long end = System.currentTimeMillis();

        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {

			// print the path from beginning to start.
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
                System.out.println(st);
            }
            System.out.println();
            System.out.println("Search time: " + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
        }
    }

    private static boolean getValidRandomizedBoolean(Scanner scanner)
    {
        boolean isRandomized;

        System.out.print("Enter true for randomized, false otherwise: ");
        while (!scanner.hasNextBoolean())
        {
            System.out.println("Invalid input. Please enter true or false.");
            scanner.next(); // consume the invalid input
        }
        isRandomized = scanner.nextBoolean();

        return isRandomized;
    }
    private static int getValidDimension(Scanner scanner)
    {
        int dimension;
        do {

            System.out.print("Enter dimension (positive number >= 1): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a positive number.");
                scanner.next(); // consume the invalid input
            }
            dimension = scanner.nextInt();
        } while (dimension < 1);

        return dimension;
    }
    private static void displayWelcomeMessage() {
        System.out.println("\t************************************************************");
        System.out.println("\t*              Welcome to the River Crossing               *");
        System.out.println("\t*                     Problem Game!                        *");
        System.out.println("\t* -------------------------------------------------------- *");
        System.out.println("\t*            In this game, you will play with the          *");
        System.out.println("\t*                  River Crossing Problem.                 *");
        System.out.println("\t*            finding the optimal solution using            *");
        System.out.println("\t*                   the A* algorithm.                      *");
        System.out.println("\t*                                                          *");
        System.out.println("\t*                  Let's get started!                      *");
        System.out.println("\t**********************************************************\n");
    }
}
