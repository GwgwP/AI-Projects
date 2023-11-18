import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Family implements Comparable<Family>{
    private final String name;
    private final int crossingTime;
    private int id;
    private static final Set<Integer> usedIds = new HashSet<>();
    private static final Random random = new Random();

    /**
     * Family member's constructor
     * @param name of the member
     * @param crossingTime of the member
     */
    public Family(String name, int crossingTime) {
        this.name = name;
        this.crossingTime = crossingTime;
        generateUniqueId();
    }


    /**
     * helper method for creating unique ids
     * for every family member
     */
    private void generateUniqueId() {
        do {
            id = random.nextInt(100000);
        } while (usedIds.contains(id));
        usedIds.add(id);
    }

    /**
     *
     * @return family member's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return family member's time
     */
    public int getCrossingTime() {
        return crossingTime;
    }


    /**
     *
     * @return the id of the family member
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return printing method that presents the name of the member.
     */
    public String toString()
    {
        return name;
    }



    @Override
    public int compareTo(Family otherFamily) {
        // Compare based on crossing times
        return Integer.compare(this.crossingTime, otherFamily.crossingTime);
    }

}

