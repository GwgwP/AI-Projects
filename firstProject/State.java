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

    /**
     * State's Constructor
     * @param dimension (number of family members - taken only if randomized is set)
     * @param randomized
     */
    State(int dimension, boolean randomized)
    {
        //if we are in the initial state we don't have any operator that led to this initial state.
        this.operator.add(null);
        this.father = null;

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

    /**
     *  constructor for creating copy of the state.
     * @param fam_r
     * @param fam_l
     * @param cost
     * @param father
     * @param oper
     * @param heuristicCost
     */
    private State(Family[] fam_r, Family[] fam_l, int cost, State father, List<Family> oper, int heuristicCost)
    {
        this.setRights(fam_r);
        this.setLefts(fam_l);
        this.cost=cost;
        this.father = father;
        this.operator = oper;
        this.heuristicCost = heuristicCost;
    }

    /**
     * @param children
     * @return  the best of the heuristic functions.
     */ 
    public State HeuristicManager(ArrayList<State> children){

        State bestState=null;

        int min = Integer.MAX_VALUE;
        int f = Integer.MAX_VALUE;
       
        
        for (State st : children){
            int res1 = 0;
            int res2 = 0;

            ArrayList<Integer> costs = new ArrayList<Integer>();
            
            if (torch) { //right -> left
                res1 = st.heuristic1();
            }
            else{ //left -> right
                res2 = st.heuristic2();
            }
            costs.add(res1);
            costs.add(res2);
      
            

            int max = costs.get(0);
            
            

            for (Integer k:costs){
                if (k>max) max=k;
            }
            

            st.heuristicCost = max;
            f =  st.cost + max;

            if (f<min){
                min  = f;
                bestState = st;
            }
        }   
        return bestState;
    }


    /**
     * Heuristic 1 - suppose that NOT only 2 people can cross the bridge,
     * also they don't need someone from the left side to come and take them with the torch
     * so the cost for everyone on the right side to pass to the left side
     * is the max value of the rights.
     * @return max
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


    /**
     * Heuristic 2 - suppose that NOT only 2 people can cross the bridge,
     * but they need someone from the left side to come and take them with the torch.
     * choose min from the left side, max from the right side, so that we don't overestimate.
     * @return the computed cost
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


    /**
     *
     * @param lefts array
     */
    public void setLefts(Family[] lefts) {
        this.dimension = lefts.length;
        this.lefts = new Family[this.dimension];

        System.arraycopy(lefts, 0, this.lefts, 0, this.dimension);
    }

    /**
     *
     * @return lefts[] array
     */
    public Family[] getLefts() {
        return lefts;
    }

    /**
     *
     * @param rights array
     */
    public void setRights(Family[] rights) {
        this.dimension = rights.length;
        this.rights = new Family[this.dimension];

        for(int i = 0; i < this.dimension; i++)
        {
            this.rights[i]= rights[i];
        }
    }

    /**
     *
     * @return rights[] array
     */
    public Family[] getRights() {
        return rights;
    }

    /**
     *
     * @return dimension
     */
    public int getDimension()
	{
        return this.dimension;
    }

    /**
     *
     * @param dimension number of family members
     */
    void setDimension(int dimension) 
	{
        this.dimension = dimension;
    }

    /**
     *
     * @return the father of the state
     */
    public State getFather()
	{
        return this.father;
    }

    /**
     *
     * @return a list with all the children of a state
     */
    public ArrayList<State> getChildren(){
        ArrayList<State> children = new ArrayList<>();
        State child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //copy constructor
        
        ArrayList<List<Family>> combos = new ArrayList<>();
        if(torch)
        {   
            combos = generateCombinations(child.rights);
            for (List<Family> combination : combos) 
            {
                if(combination.size()==2) //when we are on the right side we want 2 people to move left
                {
                    // Process the combination
                    child.father = this;
                    child.moveLeft(combination);
                    child.operator = combination;
                    children.add(child);
                    child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //restore the initiail state of the child
                }
            }
        }
        else
        {   
            combos = generateCombinations(child.lefts);
            for (List<Family> combination : combos) {
                if (combination.size() == 1) { //when we are on the left side we want one person to move right
                    child.father = this;
                    child.moveRight(combination);
                    child.operator = combination;
                    children.add(child);
                    child = new State(this.rights, this.lefts, this.cost, this.father, this.operator, this.heuristicCost); //restore the initiail state of the child
                }
            }   
        }
        return children;
    }

    /**
     * Generating every possible combination of the family members. (Ex {Father, son}, {son, mother} , {son} etc)
     * @param sourceSide lefts[] or right[] array
     * @return all combinations
     */
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

    /**
     *
     * @param members_to_move 2 members from right array
     */
    void moveLeft(List<Family> members_to_move) {
        
        // Move exactly 2 family members from the right side to the left side.
    
        Family member1 = members_to_move.get(0);
        Family member2 = members_to_move.get(1);
    
        // Find the indexes of the members to move from the Family array
        int k = findFamilyIndex(member1, rights);
        int l = findFamilyIndex(member2, rights);
    
        // Find an empty spot on the left side to move family member1
        int j = findEmptySpot(lefts); //we are sure that there will be an empty spot because we have 2 arrays of the same dimension
        lefts[j] = rights[k];
        rights[k] = null;  // Remove family member who moved left
    
    
        // Find an empty spot on the left side to move family member2
        j = findEmptySpot(lefts);
        lefts[j] = rights[l];
        rights[l] = null;  // Remove family member who moved left
    
        torch = false;  // Move the torch to the left side
    
        int a = member1.getCrossingTime();
        int b = member2.getCrossingTime();
    
        this.increaseCost(Math.max(a, b));
    }

    /**
     *
     * @param members_to_move 1 member from left array
     */
    void moveRight(List<Family> members_to_move) {        
        
        // Move exact 1 family member from the left side to the right side.
    
        Family member1 = members_to_move.get(0);
    
        // Find the indices of the members to move from the Family array
        int k = findFamilyIndex(member1, lefts);

        // Find an empty spot on the right side to move family member1
        int j = findEmptySpot(rights);
    
        rights[j] = lefts[k];
        lefts[k] = null;  // Remove family member who moved right

    
        torch = true;  // Move the torch to the left side
    
        this.increaseCost(member1.getCrossingTime());
    }

    /**
     * Helper for finding the index of a Family member in the array
     * @param member the family member we are searching for
     * @param array lefts or rights
     * @return the index of the array
     */
    private int findFamilyIndex(Family member, Family[] array) {
        for (int i = 0; i < this.dimension; i++) 
        {
            if (array[i] != null && array[i].getId() == member.getId()) return i;
        }
        return -1; // it will never reach here
    }

    /**
     * Find an empty spot in the Family array
     * @param array lefts or rights
     * @return the index
     */
    private int findEmptySpot(Family[] array) {
        for (int i = 0; i < this.dimension; i++) {
            if (array[i] == null) return i;
        }
        return -1;  // it will never reach here
    }


    /**
     *
     * @return operator that led to the current state
     */
    public List<Family> getOperator() {
        return operator;
    }


    /**
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     *
     * @param cost of the move
     */
    public void increaseCost(int cost) {
        this.cost += cost;
    }

    /**
     * Determines if we are in a final state or not.
     * @return boolean.
     */
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


    /**
     * override this for proper hash set comparisons.
     * for the closed set.
     * @param obj State
     * @return if 2 states are equal
     */
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

    /**
     * method used for printing and graphically representing every state.
     * @return the printing
     */
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

    /**
     *
     * @param o the object to be compared.
     * @return Compare based on heuristicCost in ascending order
     */
    @Override
    public int compareTo(State o) {
        return Integer.compare(this.heuristicCost, o.heuristicCost);
    }
}