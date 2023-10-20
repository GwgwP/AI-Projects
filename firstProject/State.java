import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.FocusManager;

public class State
{
    private Family[] lefts; 
    private Family[] rights;


    private static boolean torch = true; //true means that the torch is at the starting side 
    
    private int g = 0; //cost till here 
    
    private int dimension;

    private State father = null;


    State(int dimension, boolean randomized)
    {
        if(randomized)
        {
            // this.dimension = dimension;
            // List<Integer> numbers = new ArrayList<>(); // the numbers we will use.
            // for(int i= 0; i < this.dimension * this.dimension; i++)
            // {
			// 	// 0 to 8...
            //     numbers.add(i);
            // }
            // this.tiles = new int[this.dimension][this.dimension];
            // Random r = new Random();
            // for(int i = 0; i < this.dimension; i++)
            // {
            //     for(int j = 0; j < this.dimension; j++)
            //     {
            //         Collections.shuffle(numbers);
            //         this.tiles[i][j] = numbers.remove(r.nextInt(numbers.size()));
            //         if(this.tiles[i][j] == 0) // if we have the empty tile, keep its co-ordinates.
            //         {
            //             this.emptyTileColumn = j;
            //             this.emptyTileRow = i;
            //         }
            //     }
            // }
        }
        else
        {
            this.dimension = 5;
            this.rights = new Family[this.dimension];
            this.lefts = new Family[this.dimension];

            
            Family son1 = new Family("Son1", 1) ;
            Family son2 = new Family("Son2", 3) ;
            Family mother = new Family("Mother", 6) ;
            Family father = new Family("Father", 8) ;
            Family grandfather = new Family("Grandfather", 12) ;
           
            ArrayList <Family> family = new ArrayList<>();
            family.add(son1);
            family.add(son2);
            family.add(mother);
            family.add(father);
            family.add(grandfather);

            this.rights[0] = son1;
            this.rights[1] = son2;
            this.rights[2] = mother;
            this.rights[3] = father;
            this.rights[4] = grandfather;

            
        }
    }

    // constructor for creating copy of the state.
     State(Family[] fam_r, Family[] fam_l)
    {
        this.setRights(fam_r);
        this.setLefts(fam_l);
    }


    void print()
    {
        System.out.println("-------------------------------------");
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                if(this.tiles[i][j] == 0)
                {
                    System.out.print(' ');
                }
                else
                {
                    System.out.print(this.tiles[i][j]);
                }
                if(j < this.dimension - 1)
                {
                    System.out.print('\t');
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    public Family[] getLefts() {
        return lefts;
    }

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
        State child = new State(this.rights, this.lefts);  // very important to create a copy of current state before each move.
       
        if(child.moveLeft())
        {
			// child.setFather(this);
            // children.add(child);
        }
        //child = new State(this.tiles); // very important to create a copy of current state after each move.
        if(child.moveRight())
        {
			// child.setFather(this);
            // children.add(child);
        }
        return children;
    }


    // boolean moveLeft()
    // {
    //     if(this.emptyTileColumn == 0) return false; // if we are on the first column we can not move

    //     // exchange 0 with left tile.
    //     this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow][this.emptyTileColumn - 1];
    //     this.emptyTileColumn--;
    //     this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
    //     return true;
    // }

    // boolean moveRight()
    // {
    //     if(this.emptyTileColumn == this.dimension - 1) return false; // if we are on the last column we can not move

    //     // exchange 0 with left tile.
    //     this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow][this.emptyTileColumn + 1];
    //     this.emptyTileColumn++;
    //     this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
    //     return true;
    // }

    // boolean isFinal()
    // {
    //     for(int i = 0; i < this.dimension; i++)
    //     {
    //         for(int j = 0; j < this.dimension; j++)
    //         {
    //             if((i == this.dimension -1) && (j == this.dimension - 1))
    //             {
    //                 // if we are in last row AND last column, check for 0.
    //                 // if not 0, it's not final state.
    //                 if(this.tiles[i][j] != 0) return false;
    //             }
    //             else
    //             {
    //                 // check for increasing order of tiles.
    //                 // e.g., for i=j=0, this.tiles[0][0] should be equal to 0 + 0 + 1 = 1.
    //                 if(this.tiles[i][j] != (this.dimension * i) + j + 1) return false;
    //             }
    //         }
    //     }
    //     return true;
    // }


    // // override this for proper hash set comparisons.
    // @Override
    // public boolean equals(Object obj)
    // {
    //     if(this.dimension != ((State)obj).dimension) return false;
    //     if(this.emptyTileRow != ((State)obj).emptyTileRow) return false;
    //     if(this.emptyTileColumn != ((State)obj).emptyTileColumn) return false;

    //     // check for equality of numbers in the tiles.
    //     for(int i = 0; i < this.dimension; i++)
    //     {
    //         for(int j = 0; j < this.dimension; j++)
    //         {
    //             if(this.tiles[i][j] != ((State)obj).tiles[i][j])
    //             {
    //                 return false;
    //             }
    //         }
    //     }

    //     return true;
    // }

    // // override this for proper hash set comparisons.
    // @Override
    // public int hashCode()
    // {
    //     return this.emptyTileRow + this.emptyTileColumn + this.dimension + this.identifier();
    // }


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
}
