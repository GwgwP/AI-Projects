import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class State
{
    private Family[] lefts; 
    private Family[] rights;
    private int cost ; //cost till here 

    private boolean torch; //true means that the torch is at the starting side 
    
    private Family[] operator = new Family[2]; // we accept at most 2 people on the bridge at the same time.

   

    private int dimension;

    private State father = null;


    State(int dimension, boolean randomized)
    {
        //if we are in the initial state we dont have any operator that led to this initial state.
        operator[0] = null;
        operator[1] = null;

        if(randomized)
        {
            // give random family names and times
            
            // List of family members

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
            
            if(dimension <=10 ) //we dont have duplicates (ex 2 fathers)
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
            

            this.cost = 0;
            this.dimension = dimension;
            torch = true;
                        
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
            //the example at the project
            this.cost = 0;
            this.dimension = 3;
            torch = true;
                        
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
           // this.rights[3] = father;
            //this.rights[4] = grandfather;     
            // //the example at the project
            // this.cost = 0;
            // this.dimension = 5;
            // State.torch = true;
                        
            // this.rights = new Family[this.dimension];
            // this.lefts = new Family[this.dimension];
           
            // Family son1 = new Family("Son1", 1) ;
            // Family son2 = new Family("Son2", 3) ;
            // Family mother = new Family("Mother", 6) ;
            // Family father = new Family("Father", 8) ;
            // Family grandfather = new Family("Grandfather", 12) ;            

            // this.rights[0] = son1;
            // this.rights[1] = son2;
            // this.rights[2] = mother;
            // this.rights[3] = father;
            // this.rights[4] = grandfather;            
        }
    }

    // constructor for creating copy of the state.
    private State(Family[] fam_r, Family[] fam_l, int g, State father, Family[] oper)
    {
        this.setRights(fam_r);
        this.setLefts(fam_l);
        this.cost=g;
        this.father = father;
        this.operator = oper;
    }


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

    //TODO CHECK IF NEEDED
    int getDimension() 
	{
        return this.dimension;
    }

    void setDimension(int dimension) 
	{
        this.dimension = dimension;
    }

    public State getFather()
	{
        return this.father;
    }

    private void setFather(State father)
	{
        this.father = father;
    }
    
    ArrayList<State> getChildren()
    {
        ArrayList<State> children = new ArrayList<>();
        State child = new State(this.rights, this.lefts, this.cost, this.father, this.operator); //copy constructor
        
        ArrayList<List<Family>> combos = new ArrayList<>();
        if(torch)
        {   
            combos = generateCombinations(child.rights);

            for (List<Family> combination : combos) {
                // Process the combination
                child.setFather(this);
                child.moveLeft(combination);
                children.add(child);
                child = new State(this.rights, this.lefts, this.cost, this.father, this.operator); //restore the initiail state of the child
            }
        }
        else
        {
            combos = generateCombinations(child.lefts);
            for (List<Family> combination : combos) {
                // Process the combination
                child.setFather(this);
                child.moveRight(combination);
                children.add(child);
                child = new State(this.rights, this.lefts, this.cost, this.father, this.operator); //restore the initiail state of the child
            }
        }

        return children;
    }

    // Generating every possible combination of the family members. (Ex {Father, son}, {son, mother} etc)
    private ArrayList<List<Family>> generateCombinations(Family[] sourceSide) {
        ArrayList<List<Family>> combinations = new ArrayList<>();
        int n = sourceSide.length;
        
        for (int i = 0; i < n; i++) {
            // one person moving
            if(sourceSide[i] == null) continue;
            combinations.add(List.of(sourceSide[i]));

            for (int j = i + 1; j < n; j++) {
                // Consider two people moving
                if(sourceSide[i] == null) 
                {
                    i++;
                    break;
                }
                if(sourceSide[j] == null) continue;
                combinations.add(List.of(sourceSide[i], sourceSide[j]));
            }
        }
       
        return combinations;
    }

    void moveLeft(List<Family> members_to_move)
    {        
        this.setOperator(members_to_move);
        // Move at most 2 family members from the right side to the left side.
        int count = 0;
        Family member1 = members_to_move.get(0);
        Family member2 = null;

        if(members_to_move.size()==2)
        {
            member2 = members_to_move.get(1);
        }


        //finding the members to move from the Family array
        int k = 0;
        int l = 0;
        for (int i = 0; i < rights.length; i++) 
        {
            if(this.rights[i]!=null)
            {
                if (this.rights[i].getId() == member1.getId()) k = i;

                if(member2!=null )
                {
                    if (this.rights[i].getId() == member2.getId()) l = i;
                }
            }              
        }

        // Find an empty spot on the left side to move the family member1
        for (int j = 0; j < lefts.length; j++)
        { 
            if (lefts[j] == null) 
            {
                this.lefts[j] = this.rights[k];
                this.rights[k] = null;           //remove family member who moved left
                count++;
                break;
            }
        }
        //Find an empty spot on the left side to move the family member2 if it exists
        if(members_to_move.size()==2)
        {
            for (int j = 0; j < lefts.length; j++)
            { 
                if (this.lefts[j] == null) 
                {
                    this.lefts[j] = this.rights[l];
                    this.rights[l] = null;           //remove family member who moved left
                    break;
                }
            }
        }
        this.setLefts(lefts);
        this.setRights(rights);

        torch = false; // Move the torch to the left side

        int a=0;
        int b=0;

        if ( this.operator[0]!= null)
        {
            a = this.operator[0].getCrossingTime();
        }
        if(this.operator[1]!= null)
        {
            b = this.operator[1].getCrossingTime();
        }        
        this.increaseCost(Math.max(a, b));
    }

    void moveRight(List<Family> members_to_move)
    {
        this.setOperator(members_to_move);
       // Move at most 2 family members from the left side to the right side.
        int count = 0;
        Family member1 = members_to_move.get(0);
        Family member2 = null;

        if(members_to_move.size()==2)
        {
            member2 = members_to_move.get(1);
        }

        int k = 0;
        int l = 0;
        
        for (int i = 0; i < lefts.length; i++) 
        {
            if(lefts[i]!=null)
            {
                if (lefts[i].getId() == member1.getId()) k = i;

                if(member2!=null )
                {
                    if (lefts[i].getId() == member2.getId()) l = i;
                } 
            }          
        }

        // Find an empty spot on the right side to move the family member1
        for (int j = 0; j < rights.length; j++)
        { 
            if (rights[j] == null) 
            {
                rights[j] = lefts[k];
                lefts[k] = null;           //remove family member who moved left
                count++;
                break;
            }
        }
        //Find an empty spot on theright side to move the family member2 if it exists
        if(members_to_move.size()==2)
        {
            for (int j = 0; j < rights.length; j++)
            { 
                if (rights[j] == null) 
                {
                    rights[j] = lefts[l];
                    lefts[l] = null;           //remove family member who moved left
                    break;
                }
            }
        }
        this.setLefts(lefts);
        this.setRights(rights);

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
        increaseCost(Math.max(a, b)); //increase the cost
    }
    

    public Family[] getOperator() {
        return operator;
    }

    public void setOperator(List<Family> oper) {
        this.operator[0] = oper.get(0);
        if (oper.size()>1) {
            this.operator[1] = oper.get(1);
        }
    }


    
    public int getCost() {
        return cost;
    }

    public void increaseCost(int cost) {
        this.cost += cost;
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
    sb.append("\t\t\t\t\t\tCost: ").append(this.cost);
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
