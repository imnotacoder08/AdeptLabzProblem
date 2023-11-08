package ModellingApproachOptimised;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface I_BattleSimulator {
    /**
     * Responsible for battle outcome on unit level.
     **/
    Map<String, Set<String>> unitAdvantages = new HashMap<>();

    int calculateAdvantage(Soldier myUnit, Soldier opponentUnit);
}
