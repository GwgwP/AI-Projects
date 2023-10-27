public class Family {
    private String name;
    private int crossingTime;

   

    public Family(String name, int crossingTime) {
        this.name = name;
        this.crossingTime = crossingTime;
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
}
