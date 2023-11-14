import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class State implements Comparable<State>
{
    private Family[] lefts; 
    private Family[] rights;
    private int cost ; //cost till here 

    private boolean torch; //true means that the torch is at the starting side 
    
    private List <Family> operator = new ArrayList<>(); // we accept at most 2 people on the bridge at the same time.

    private int dimension;

    private int heuristicCost;

    private State father = null;


    State(int dimension, boolean randomized)
    {
        //if we are in the initial state we dont have any operator that led to this initial state.
        this.operator.add(null);

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
            this.heuristicCost = 0;
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
          
            this.torch = true;
            //this.operator[0] = null;
            //this.operator[1] = null;
           
            this.cost = 0;
            this.heuristicCost = 0;
            this.dimension = 5;
                      
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
    private State(Family[] fam_r, Family[] fam_l, int cost, State father, List<Family> oper, int heuristicCost)
    {
        this.setRights(fam_r);
        this.setLefts(fam_l);
        this.cost=cost;
        this.father = father;
        this.operator = oper;
    }

    //Returns the best of the heuristic functions.
    public State HeuristicManager(ArrayList<State> children){

        State bestState=null;
        int min=Integer.MAX_VALUE;

        for (State st : children){

            ArrayList<Integer> costs = new ArrayList<Integer>();
            costs.add(st.heuristic1());
            costs.add(st.heuristic2());
            // costs.add(st.heuristic3());
            // costs.add(st.heuristic4());

            int max = costs.get(0);
            for (Integer k:costs){
                if (k>max) max=k;
            }
            int g = st.father.cost;
            //
            int f =  g + max;
            if (f<min){
                min  = f;
                bestState = st;
            }
        }   
        return bestState;
    }
        /*  Heuristic 1 - suppose that NOT only 2 people can cross the bridge, 
     also they don't need someone from the left side to come and take them with the torch
     so the cost for everyone on the right side to pass to the left side
     is the max value of the rights.
    */
     private int heuristic1(){
        int max =-1;
        for (Family fam:rights){
            if (fam!=null){
                if (fam.getCrossingTime()>max) max = fam.getCrossingTime();
            }
        }
        return max;
    }

    /*  Heuristic 2 - suppose that NOT only 2 people can cross the bridge, 
     but they need someone from the left side to come and take them with the torch.
     I choose min from the left side, max from the right side, so that i don't overestimate.
    */
     private int heuristic2(){
        int maxR =-1;
        int minL = Integer.MAX_VALUE;
        for (Family fam:rights){
            if (fam!=null){
                if (fam.getCrossingTime()>maxR) maxR = fam.getCrossingTime();
            }
        }
        for (Family fam:lefts){
            if (fam!=null){
                if (fam.getCrossingTime()<minL) minL = fam.getCrossingTime();
            }
        }
        return minL + maxR;
    }

    // /*  Heuristic 3 - If I'm on the left side, i always choose the min value to go back
    // */
    //  private int heuristic3(){
    //     int min = Integer.MAX_VALUE;
    //     for (Family fam:lefts){
    //         if (fam.getCrossingTime()<min) min = fam.getCrossingTime();
    //     }
    //     return min;
    // }

    // /*  Heuristic 4 - If I'm on the right side, i always choose the min combination to go to the left
    // */
    //  private int heuristic4(){
    //     int min1 = Integer.MAX_VALUE;
    //     int min2 = Integer.MAX_VALUE;

    //     for (Family fam : rights) {
    //         int crossingTime = fam.getCrossingTime();

    //         if (rights.length == 1) {
    //         return crossingTime;
    //         }

    //         if (crossingTime < min1) {
    //             min2 = min1;
    //             min1 = crossingTime;
    //         } else if (crossingTime < min2) {
    //             min2 = crossingTime;
    //         }
    //     }
    //     return Math.max(min1, min2); 
    // }


    // // Heuristic 1 - ceil(n/2)
    // private int heuristic1(){
    //     return (int) Math.ceil(crowdRight()/2);
    // }
    // // Heuristic 2 -  Right: 2n-3
    // private int heuristic2a(){
    //     return (int) Math.ceil(2* crowdRight() - 3);
    // }

    // // Heuristic 2 - Left: 2n
    // private int heuristic2b(){
    //     return (int) Math.ceil(2* crowdLeft());
    // }

    // // Heuristic 3 Left
    // private int heuristic3a(){
    //     return crowdLeft();
    // }

    // // Heuristic 3 Right
    // private int heuristic3b(){
    //     return crowdRight();
    // }


    private int crowdLeft(){
        int sum=0;
        for (Family fam:lefts){
            if (fam!=null) sum++;
        }
        return sum;
    }
    private int crowdRight(){
        int sum=0;
        for (Family fam:rights){
            if (fam!=null) sum++;
        }
        return sum;
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
    
    public ArrayList<State> getChildren(){
        System.out.println("Eimai stin GetChildren()");

        ArrayList<State> children = new ArrayList<>();
        State child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //copy constructor
        
        ArrayList<List<Family>> combos = new ArrayList<>();
        System.out.println(child.crowdRight());

        if(torch)
        {   
            //theloume na dhmioyrgei ola ta combos mia fora? h se kathe nea katastash?
            combos = generateCombinations(child.rights);

            for (List<Family> combination : combos) {
                // Process the combination
                child.father = this;
                child.moveLeft(combination);
                child.operator = combination;
                children.add(child);
                //edw xrisimopoiw tis eyretikes gia to heuristic cost
                //state = HeuristicManager...
                //h heuristicCost = ...
                child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //restore the initiail state of the child
            }
        }
        else
        {
            combos = generateCombinations(child.lefts);
            int min = Integer.MAX_VALUE;
            List<Family> mincombination = null;
            for (List<Family> combination : combos) {
                if (combination.size() ==1){
                    if (combination.get(0).getCrossingTime() <min){
                        mincombination  = combination; 
                    }
                }
            }

            // // to combos tha pairnei ta lefts   
            // combos = generateCombinations(child.lefts);
            // for (List<Family> combination : combos) {
            //     // Process the combination

                System.out.println("To mikrotero comb: " + mincombination.get(0).getName());
                child.father = this;
                child.moveRight(mincombination);
                child.operator = mincombination;
                children.add(child);
                child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //restore the initiail state of the child
            // }
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
       
        //================================================  T E S T I N G =================================================//
        System.out.println("To combination poy dhmiourgithike einai to: ");
        for (int i = 0; i < combinations.size(); i++) {
            if (combinations.get(i).size() == 1) System.out.println(combinations.get(i).get(0).getName());
            if (combinations.get(i).size() == 2) System.out.println(combinations.get(i).get(0).getName() + " mazi me " +combinations.get(i).get(1).getName());

        }
        return combinations;
    }

    void moveLeft(List<Family> members_to_move) {  
        
        // Move at most 2 family members from the right side to the left side.
    
        Family member1 = members_to_move.get(0);
        Family member2 = (members_to_move.size() == 2) ? members_to_move.get(1) : null;
    
        // Find the indexes of the members to move from the Family array
        int k = findFamilyIndex(member1, rights);
        int l = (member2 != null) ? findFamilyIndex(member2, rights) : -1;
    
        // Find an empty spot on the left side to move family member1
        int j = findEmptySpot(lefts); //we are sure that there will be an empty spot because we have 2 arrays of the same dimension
        lefts[j] = rights[k];
        rights[k] = null;  // Remove family member who moved left
    
    
        // Find an empty spot on the left side to move family member2 if it exists
        if (member2 != null) {
            j = findEmptySpot(lefts);
            lefts[j] = rights[l];
            rights[l] = null;  // Remove family member who moved left
        }
    
        torch = false;  // Move the torch to the left side
    
        int a = member1.getCrossingTime();
        int b = (member2 != null) ? member2.getCrossingTime() : 0;
    
        this.increaseCost(Math.max(a, b));
    }

    void moveRight(List<Family> members_to_move) {        
        
        // Move at most 2 family members from the right side to the left side.
    
        Family member1 = members_to_move.get(0);
        Family member2 = (members_to_move.size() == 2) ? members_to_move.get(1) : null;
    
        // Find the indices of the members to move from the Family array
        int k = findFamilyIndex(member1, lefts);
        int l = (member2 != null) ? findFamilyIndex(member2, lefts) : -1;
    
        // Find an empty spot on the right side to move family member1
        int j = findEmptySpot(rights);
    
        rights[j] = lefts[k];
        lefts[k] = null;  // Remove family member who moved right

        // Find an empty spot on the right side to move family member2 if it exists
        if (member2 != null) {
            j = findEmptySpot(rights);
            rights[j] = lefts[l];
            lefts[l] = null;  // Remove family member who moved left

        }
    
        torch = true;  // Move the torch to the left side
    
        int a = member1.getCrossingTime();
        int b = (member2 != null) ? member2.getCrossingTime() : 0;
    
        this.increaseCost(Math.max(a, b));
    }
    
    //  Find the index of a Family member in the array
    private int findFamilyIndex(Family member, Family[] array) {
        for (int i = 0; i < this.dimension; i++) 
        {
            if (array[i] != null && array[i].getId() == member.getId()) return i;
        }
        return -1; // it will never reach here
    }
    
    // Find an emptyy spot in the Family array
    private int findEmptySpot(Family[] array) {
        for (int i = 0; i < this.dimension; i++) {
            if (array[i] == null) return i;
        }
        return -1;  // it will never reach here
    }
    

    public List<Family> getOperator() {
        return operator;
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
    //for the closed set.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true; // It's the same instance
        }
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false; // Object is not a State or is null
        }

        State otherState = (State) obj;
        
        // Create sets of unique family member codes for left and right sides
        Set<Integer> leftsUniqueCodes = new HashSet<>();
        Set<Integer> rightsUniqueCodes = new HashSet<>();
        
        for (Family family : lefts) {
            if (family != null) {
                leftsUniqueCodes.add(family.getId());
            }
        }
        
        for (Family family : rights) {
            if (family != null) {
                rightsUniqueCodes.add(family.getId());
            }
        }
        
        // Create sets of unique family member codes for the other state
        Set<Integer> otherLeftsUniqueCodes = new HashSet<>();
        Set<Integer> otherRightsUniqueCodes = new HashSet<>();
        
        for (Family family : otherState.lefts) {
            if (family != null) {
                otherLeftsUniqueCodes.add(family.getId());
            }
        }
        
        for (Family family : otherState.rights) {
            if (family != null) {
                otherRightsUniqueCodes.add(family.getId());
            }
        }
        
        // Check if the sets of unique family member codes are the same
        return leftsUniqueCodes.equals(otherLeftsUniqueCodes) && rightsUniqueCodes.equals(otherRightsUniqueCodes);
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

    @Override
    public int compareTo(State o) {
        // Compare based on heuristicCost in ascending order
        return Integer.compare(this.heuristicCost, o.heuristicCost);
    }


}