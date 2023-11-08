package ModelingApproach;

public class Soldier {
    private String type;
    private int strength;

    public Soldier(String type, int count) {
        this.type = type;
        this.strength = count;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return strength;
    }
}
