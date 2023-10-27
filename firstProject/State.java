import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class State
{
    private Family[] lefts; 
    private Family[] rights;


    private static boolean torch; //true means that the torch is at the starting side 
    
    private Family[] operator = new Family[2]; // we accept at most 2 people on the bridge at the same time.

    private int g ; //cost till here 
    
    private int dimension;

    private State father = null;


    State(int dimension, boolean randomized)
    {
        //if we are in the initial state we dont have any operator that led to this initial state.
        operator[0] = null;
        operator[1] = null;

        if(randomized)
        {
            //TODO 
            // give random family and times
            // List of family member names

            List<String> familyMembers = new ArrayList<>();
            familyMembers.add("Father");
            familyMembers.add("Mother");
            familyMembers.add("Son");
            familyMembers.add("Daughter");
            familyMembers.add("Grandfather");
            familyMembers.add("Grandmother");
            familyMembers.add("Son2");
            familyMembers.add("Daughter2");
            familyMembers.add("Son3");
            familyMembers.add("Daughter3");

            // Shuffle the list of all family names
            Collections.shuffle(familyMembers);
            
            // Number of family members
    
            
            // Random number generator
            Random random = new Random();

            
            // Create an array to store family members and their crossing times
            Family[] family = new Family[dimension];
            
            if(dimension <=10 )
            {
                // Generate random family members with names and crossing times
                for (int i = 0; i < dimension; i++) 
                {
                    String randomName = familyMembers.get(random.nextInt(familyMembers.size()));
                    int randomCrossingTime = random.nextInt(18) + 1; // Random time between 1 and 18 seconds
                    
                    family[i] = new Family(randomName, randomCrossingTime);
                    
                    // Remove the selected name to avoid duplication
                    familyMembers.remove(randomName);
                }
            }
            else
            {
                // Generate random family members with names and crossing times
                for (int i = 0; i < dimension; i++) 
                {
                    String randomName = familyMembers.get(random.nextInt(familyMembers.size()));
                    int randomCrossingTime = random.nextInt(18) + 1; // Random time between 1 and 18 seconds
                    
                    family[i] = new Family(randomName, randomCrossingTime);

                }
            }
            

            this.g = 0;
            this.dimension = dimension;
            State.torch = true;
                        
            this.rights = new Family[this.dimension];
            this.lefts = new Family[this.dimension];

            int i = 0;
            for (Family mem:family)
            {
                this.rights[i] = mem;
                i++;
            }

        }
        else
        {
            this.g = 0;
            this.dimension = 5;
            State.torch = true;
                        
            this.rights = new Family[this.dimension];
            this.lefts = new Family[this.dimension];
           
            Family son1 = new Family("Son1", 1) ;
            Family son2 = new Family("Son2", 3) ;
            Family mother = new Family("Mother", 6) ;
            Family father = new Family("Father", 8) ;
            Family grandfather = new Family("Grandfather", 12) ;            

            this.rights[0] = son1;
            this.rights[1] = son2;
            this.rights[2] = mother;
            this.rights[3] = father;
            this.rights[4] = grandfather;            
        }
    }

    // constructor for creating copy of the state.
     State(Family[] fam_r, Family[] fam_l, int g)
    {
        this.setRights(fam_r);
        this.setLefts(fam_l);
        this.g=g;
    }


   

    // TODO CHECK IF NEEDED
    public void setLefts(Family[] lefts) {
        this.dimension = lefts.length;
        this.lefts = new Family[this.dimension];

        for(int i = 0; i < this.dimension; i++)
        {
            this.lefts[i]= lefts[i];
        }
    }

    public Family[] getLefts() {
        return lefts;
    }

    // TODO CHECK IF NEEDED
    public void setRights(Family[] rights) {
        this.dimension = rights.length;
        this.rights = new Family[this.dimension];

        for(int i = 0; i < this.dimension; i++)
        {
            this.rights[i]= rights[i];
        }
    }

    public Family[] getRights() {
        return rights;
    }


    int getDimension() 
	{
        return this.dimension;
    }

    void setDimension(int dimension) 
	{
        this.dimension = dimension;
    }


    State getFather()
	{
        return this.father;
    }

    void setFather(State father)
	{
        this.father = father;
    }


    ArrayList<State> getChildren()
    {
        ArrayList<State> children = new ArrayList<>();
        State child = new State(this.rights, this.lefts, this.g);  // very important to create a copy of current state before each move.
       
        if(child.moveLeft())
        {
            //TODO 
			child.setFather(this);
            children.add(child);
        }
        //child = new State(this.tiles); // very important to create a copy of current state after each move.
        if(child.moveRight())
        {
            //TODO
			// child.setFather(this);
            // children.add(child);
        }
        return children;
    }


    boolean moveLeft()
    {
        if (!(torch && rights.length!=0)) return false;
        //if the torch is on the right side and there are still people on the right side then we can move left 
        
        // Move at most 2 family members from the right side to the left side.
        int count = 0;
        for (int i = 0; i < rights.length && count < 2; i++) {
            if (rights[i] != null) { //if there is someone right
                // Find an empty spot on the left side to move the family member
                for (int j = 0; j < lefts.length; j++) {
                    if (lefts[j] == null) {
                        operator[count] = rights[i]; // Update the operator with the family member moving left
                        lefts[j] = rights[i];
                        rights[i] = null;           //remove family member who moved left
                        count++;
                        break;
                    }
                }
            }
        }


        torch = false; // Move the torch to the left side
        int a=0;
        int b=0;
        if ( operator[0]!= null)
        {
            a = operator[0].getCrossingTime();
        }
        if(operator[1]!= null)
        {
            b = operator[1].getCrossingTime();
        }

        this.g += a+b; // Increase the cost
        return true;
    }

    boolean moveRight()
    {
        if (!(!torch && lefts.length!=0)) return false;

        //if the torch is on the left side and there are still people on the left side then we can move Right.
        int count = 0;
        for(int i = 0; i <lefts.length && count<2; i++){
            if (lefts[i] != null){ //if we have people on the left
                for (int j = 0; j<rights.length; j++){
                    operator[count] = lefts[i];
                    rights[j] = lefts[i];
                    lefts[i] = null;
                    count++;
                    break;
                }

            }
        }
        torch = true;
        int a = 0;
        int b = 0;
        if(operator[0]!= null)
        {
            a = operator[0].getCrossingTime();
        }
        if(operator[1]!= null)
        {
            b = operator[1].getCrossingTime();
        }
        this.g += a+b; //increase the cost
        
        return true;
    }

    boolean isFinal()
    {
        // we have a final state when every family member is on the left side. 
        // we can't return lefts.length == dimension because the array is already initialized with the dimension.
        int counter = 0;
        for (Family member : lefts) {
            if (member != null) {
                counter++;
            }
        }
        return (counter == lefts.length);
      
    }


    // override this for proper hash set comparisons.
    //for the closed set probsbly?!
    @Override
    public boolean equals(Object obj)
    {
        //TODO
        return false;
    }

    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("==============================================================================================================\n");
    sb.append("\t\t\t\t\t\tCost: ").append(g);
    sb.append("\n\t\t\t\t\t\tDimension: ").append(dimension).append("\n\n\n");
    
    // Print the left side family members if not null
    sb.append("Left: ");
    if (lefts != null) {
        for (Family member : lefts) {
            if (member != null) {
                sb.append(member.getName()).append(" (").append(member.getCrossingTime()).append("s) | ");
            }
        }
    }
    sb.append("\t\t"); // Add extra space for formatting
    
    // Print the torch location
    sb.append("Torch: ").append(torch ? "--> " : "<-- ");
    
    // Print the right side family members if not null
    sb.append("Right: ");
    if (rights != null) {
        for (Family member : rights) {
            if (member != null) {
                sb.append(member.getName()).append(" (").append(member.getCrossingTime()).append("s) | ");
            }
        }
    }
    sb.append("\n"); // Add extra space for formatting
    
    sb.append("\n==============================================================================================================\n\n\n");
    return sb.toString();
    }


}
