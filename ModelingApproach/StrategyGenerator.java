package ModelingApproach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrategyGenerator implements I_StrategyGenerator {
    public List<Soldier> winningSequence(String myInput, String opponentInput) {
        Platoon myPlatoon = parsePlatoon(myInput);
        Platoon opponentPlatoon = parsePlatoon(opponentInput);
        List<Soldier> myPlatoonOrder = myPlatoon.getSoldiers();
        List<List<Soldier>> permutations = generatePermutations(myPlatoonOrder);

        for (List<Soldier> permutation : permutations) {
            int wins = simulateBattles(permutation, opponentPlatoon);
            if (wins >= 3) {
                return permutation;
            }
        }
        System.out.println("There is no chance of winning");
        return new ArrayList<>();
    }

    private static Platoon parsePlatoon(String input) {
        Platoon platoon = new Platoon();
        String[] parts = input.split(";");
        for (String part : parts) {
            String[] tokens = part.split("#");
            String type = tokens[0];
            try {
                Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: Invalid command syntax. Please provide numerical value for number of soldiers");
            }
            Soldier soldier = new Soldier(type, Integer.parseInt(tokens[1]));
            platoon.addSoldier(soldier);
        }
        return platoon;
    }

    public List<List<Soldier>> generatePermutations(List<Soldier> platoonOrder) {
        List<List<Soldier>> permutations = new ArrayList<>();
        permute(platoonOrder, 0, permutations);
        return permutations;
    }

    private static void permute(List<Soldier> platoonOrder, int index, List<List<Soldier>> permutations) {
        if (index == platoonOrder.size()) {
            permutations.add(new ArrayList<>(platoonOrder));
        } else {
            for (int i = index; i < platoonOrder.size(); i++) {
                Collections.swap(platoonOrder, index, i);
                permute(platoonOrder, index + 1, permutations);
                Collections.swap(platoonOrder, index, i);
            }
        }
    }

    public int simulateBattles(List<Soldier> order, Platoon opponentPlatoon) {
        int wins = 0;
        for (int i = 0; i < order.size(); i++) {
            BattleSimulator battleSimulator = new BattleSimulator();
            int outcome = battleSimulator.calculateBattleOutcome(order.get(i).getType(), order.get(i).getCount(),
                    opponentPlatoon.getSoldiers().get(i).getType(), opponentPlatoon.getSoldiers().get(i).getCount());
            if (outcome > 0) {
                wins++;
            }
        }
        return wins;
    }
}
