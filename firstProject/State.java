import java.util.ArrayList;

public class State
{
    private Family[] lefts; 
    private Family[] rights;


    private static boolean torch; //true means that the torch is at the starting side 
    
    private int g ; //cost till here 
    
    private int dimension;

    private State father = null;


    State(int dimension, boolean randomized)
    {
        if(randomized)
        {
            //TODO 
            // give random family and times
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


    public Family[] getLefts() {
        return lefts;
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

    public Family[] getRights() {
        return rights;
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
			// child.setFather(this);
            // children.add(child);
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
        //if the torch is on the right side and there are still people on the right side then we can move left 
        return (torch && rights.length!=0 );
    }

    boolean moveRight()
    {
        //if the torch is on the left side and there are still people on the left side then we can move Right.
        return (!torch && lefts.length!=0);
    }

    boolean isFinal()
    {
        return false;
        //TODO CHECK IF FINAL STET
      
    }


    // override this for proper hash set comparisons.
    //for the closed set probsbly?!
    @Override
    public boolean equals(Object obj)
    {
        return false;
    }

    

    // int identifier()
    // {
    //     int result = 0;
    //     for(int i = 0; i < this.dimension; i++)
    //     {
    //         for(int j = 0; j < this.dimension; j++)
    //         {
    //             // a unique sum based on the numbers in each state.
    //             // e.g., for i=j=0 in the fixed initial state --> 3^( (0*0) + 0) * 8 = 1 + 8 = 9
    //             // for another state, this will not be the same
    //             result += Math.pow(this.dimension, (this.dimension * i) + j) * this.tiles[i][j];
    //         }
    //     }
    //     return result;
    // }
    //printing every state
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("==============================================================================================================\n");
    
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
    sb.append("\t\t"); // Add extra space for formatting
    
    sb.append("Cost: ").append(g);
    sb.append("\tDimension: ").append(dimension).append("\n");
    
    sb.append("==============================================================================================================\n");
    
    return sb.toString();
    }


}
