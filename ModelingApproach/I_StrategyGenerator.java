package ModelingApproach;

import java.util.List;

public interface I_StrategyGenerator {
    /**
     * Responsible to find the winning platoon order.
     **/
    List<List<Soldier>> generatePermutations(List<Soldier> platoonOrder);

    int simulateBattles(List<Soldier> order, Platoon opponentPlatoon);

    List<Soldier> winningSequence(String myInput, String opponentInput);
}
