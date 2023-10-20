import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class State
{
    private int[] lefts; //who is at the left side at the moment. []
    private int[] rights; //

    private Map<String, Integer> family = new HashMap<String, Integer>(); //ex "father" : 12 means that the father of the family needs12 mins to cross the bridge
    
    private int dimension;



    private State father = null;
    private


    State(int dimension, boolean randomized)
    {
        if(randomized)
        {
            this.dimension = dimension;
            List<Integer> numbers = new ArrayList<>(); // the numbers we will use.
            for(int i= 0; i < this.dimension * this.dimension; i++)
            {
				// 0 to 8...
                numbers.add(i);
            }
            this.tiles = new int[this.dimension][this.dimension];
            Random r = new Random();
            for(int i = 0; i < this.dimension; i++)
            {
                for(int j = 0; j < this.dimension; j++)
                {
                    Collections.shuffle(numbers);
                    this.tiles[i][j] = numbers.remove(r.nextInt(numbers.size()));
                    if(this.tiles[i][j] == 0) // if we have the empty tile, keep its co-ordinates.
                    {
                        this.emptyTileColumn = j;
                        this.emptyTileRow = i;
                    }
                }
            }
        }
        else
        {
            this.dimension = 5;
            this.family.put("Son1",1);
            this.family.put("Son2",3);
            this.family.put("Mother",6);
            this.family.put("Father",8);
            this.family.put("Grandfather",12);

            

            this.rights = new int[this.dimension];
            this.lefts = new int[this.dimension];
            
            for (int i = 0; i<dimension;i++)
            {
                this.rights[i] = Map<K,V>.entry(null, null)
            }

            
        }
    }

    // constructor for creating copy of the state.
    State(int[][] tiles)
    {
        this.setTiles(tiles);
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

    int[][] getTiles() 
	{
        return this.tiles;
    }

    void setTiles(int[][] tiles) 
	{
        this.dimension = tiles.length;
        this.tiles = new int[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                this.tiles[i][j] = tiles[i][j];
                if(this.tiles[i][j] == 0)
                {
                    this.emptyTileRow = i;
                    this.emptyTileColumn = j;
                }
            }
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

    int getEmptyTileColumn() 
	{
        return this.emptyTileColumn;
    }

    void setEmptyTileColumn(int emptyTileColumn) 
	{
        this.emptyTileColumn = emptyTileColumn;
    }

    int getEmptyTileRow() 
	{
        return this.emptyTileRow;
    }

    void setEmptyTileRow(int emptyTileRow) 
	{
        this.emptyTileRow = emptyTileRow;
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
        State child = new State(this.tiles);  // very important to create a copy of current state before each move.
        if(child.moveUp())
        {
			child.setFather(this);
            children.add(child);
        }
        child = new State(this.tiles); // very important to create a copy of current state after each move.
        if(child.moveDown())
        {
			child.setFather(this);
            children.add(child);
        }
        child = new State(this.tiles); // very important to create a copy of current state after each move.
        if(child.moveLeft())
        {
			child.setFather(this);
            children.add(child);
        }
        child = new State(this.tiles); // very important to create a copy of current state after each move.
        if(child.moveRight())
        {
			child.setFather(this);
            children.add(child);
        }
        return children;
    }

    boolean moveUp()
    {
        if(this.emptyTileRow == 0) return false;  // if we are on the top row we can not move

        // exchange 0 with above tile.
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow - 1][this.emptyTileColumn];
        this.emptyTileRow--;
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
        return true;
    }

    boolean moveDown()
    {
        if(this.emptyTileRow == this.dimension - 1) return false; // if we are on the last row we can not move

        // exchange 0 with below tile.
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow + 1][this.emptyTileColumn];
        this.emptyTileRow++;
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
        return true;
    }

    boolean moveLeft()
    {
        if(this.emptyTileColumn == 0) return false; // if we are on the first column we can not move

        // exchange 0 with left tile.
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow][this.emptyTileColumn - 1];
        this.emptyTileColumn--;
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
        return true;
    }

    boolean moveRight()
    {
        if(this.emptyTileColumn == this.dimension - 1) return false; // if we are on the last column we can not move

        // exchange 0 with left tile.
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = this.tiles[this.emptyTileRow][this.emptyTileColumn + 1];
        this.emptyTileColumn++;
        this.tiles[this.emptyTileRow][this.emptyTileColumn] = 0;
        return true;
    }

    boolean isFinal()
    {
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                if((i == this.dimension -1) && (j == this.dimension - 1))
                {
                    // if we are in last row AND last column, check for 0.
                    // if not 0, it's not final state.
                    if(this.tiles[i][j] != 0) return false;
                }
                else
                {
                    // check for increasing order of tiles.
                    // e.g., for i=j=0, this.tiles[0][0] should be equal to 0 + 0 + 1 = 1.
                    if(this.tiles[i][j] != (this.dimension * i) + j + 1) return false;
                }
            }
        }
        return true;
    }


    // override this for proper hash set comparisons.
    @Override
    public boolean equals(Object obj)
    {
        if(this.dimension != ((State)obj).dimension) return false;
        if(this.emptyTileRow != ((State)obj).emptyTileRow) return false;
        if(this.emptyTileColumn != ((State)obj).emptyTileColumn) return false;

        // check for equality of numbers in the tiles.
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                if(this.tiles[i][j] != ((State)obj).tiles[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    // override this for proper hash set comparisons.
    @Override
    public int hashCode()
    {
        return this.emptyTileRow + this.emptyTileColumn + this.dimension + this.identifier();
    }


    int identifier()
    {
        int result = 0;
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                // a unique sum based on the numbers in each state.
                // e.g., for i=j=0 in the fixed initial state --> 3^( (0*0) + 0) * 8 = 1 + 8 = 9
                // for another state, this will not be the same
                result += Math.pow(this.dimension, (this.dimension * i) + j) * this.tiles[i][j];
            }
        }
        return result;
    }
}
