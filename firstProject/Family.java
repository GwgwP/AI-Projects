import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Family {
    private String name;
    private int crossingTime;
    private int id;
    private static final Set<Integer> usedIds = new HashSet<>();
    private static final Random random = new Random();

    public Family(String name, int crossingTime) {
        this.name = name;
        this.crossingTime = crossingTime;
        generateUniqueId();
    }

    private void generateUniqueId() {
        do {
            id = random.nextInt(100000);
        } while (usedIds.contains(id));
        usedIds.add(id);
    }

    public String getName() {
        return name;
    }

    public int getCrossingTime() {
        return crossingTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCrossingTime(int crossingTime) {
        this.crossingTime = crossingTime;
    }

    public int getId() {
        return id;
    }
}

