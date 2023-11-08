package ModellingApproachOptimised;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BattleSimulator implements I_BattleSimulator {
    static {
        unitAdvantages.put("Militia", new HashSet<>(Arrays.asList("Spearmen", "LightCavalry")));
        unitAdvantages.put("Spearmen", new HashSet<>(Arrays.asList("LightCavalry", "HeavyCavalry")));
        unitAdvantages.put("LightCavalry", new HashSet<>(Arrays.asList("FootArcher", "CavalryArcher")));
        unitAdvantages.put("HeavyCavalry", new HashSet<>(Arrays.asList("Militia", "FootArcher", "LightCavalry")));
        unitAdvantages.put("CavalryArcher", new HashSet<>(Arrays.asList("Spearmen", "HeavyCavalry")));
        unitAdvantages.put("FootArcher", new HashSet<>(Arrays.asList("Militia", "CavalryArcher")));
    }

    @Override
    public int calculateAdvantage(Soldier myUnit, Soldier opponentUnit) {
        // Calculate the advantage of one unit over another
        if (myUnit.getType().equals(opponentUnit.getType())) {
            return myUnit.getCount() - opponentUnit.getCount();
        }

        Set<String> myAdvantages = unitAdvantages.get(myUnit.getType());
        Set<String> opponentAdvantages = unitAdvantages.get(opponentUnit.getType());

        if (myAdvantages.contains(opponentUnit.getType())) {
            return myUnit.getCount() * 2 - opponentUnit.getCount();
        } else if (opponentAdvantages.contains(myUnit.getType())) {
            return myUnit.getCount() - opponentUnit.getCount() * 2;
        }

        return myUnit.getCount() - opponentUnit.getCount();
    }
}
