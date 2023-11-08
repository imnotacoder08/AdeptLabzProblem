package ModelingApproach;

import java.util.*;

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
    public int calculateBattleOutcome(String myUnit, Integer myCount, String opponentUnit, Integer opponentCount) {
        if (myUnit.equals(opponentUnit)) {
            return myCount.compareTo(opponentCount);
        }
        Integer effectiveOpponentCount = opponentCount;
        Set<String> yourAdvantages = unitAdvantages.get(myUnit);
        Set<String> opponentAdvantages = unitAdvantages.get(opponentUnit);
        if (yourAdvantages.contains(opponentUnit)) {
            effectiveOpponentCount = opponentCount / 2;
        } else if (opponentAdvantages.contains(myUnit)) {
            effectiveOpponentCount = opponentCount * 2;
        }
        return (myCount > effectiveOpponentCount) ? 1 : -1;
    }
}
