package ModellingApproachOptimised;

import java.util.List;

public interface I_StrategyGenerator {
    /**
     * Responsible to find the winning platoon order.
     **/

    Soldier[] winningSequence(String myInput, String opponentInput);
}
