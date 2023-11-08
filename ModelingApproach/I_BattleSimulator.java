package ModelingApproach;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface I_BattleSimulator {
    /**
     * Responsible for battle outcome on unit level.
     **/
    Map<String, Set<String>> unitAdvantages = new HashMap<>();

    int calculateBattleOutcome(String myUnit, Integer myCount, String opponentUnit, Integer opponentCount);
}
