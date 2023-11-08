package ModelingApproach;

import java.util.ArrayList;
import java.util.List;

public class Platoon {
    private List<Soldier> soldiers;

    public Platoon() {
        soldiers = new ArrayList<>();
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

}
