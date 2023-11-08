package ModellingApproachOptimised;

import java.util.*;

public class StrategyGenerator implements I_StrategyGenerator {
    public Soldier[] winningSequence(String myInput, String opponentInput) {
        Platoon myPlatoon = parsePlatoon(myInput);
        Platoon opponentPlatoon = parsePlatoon(opponentInput);
        Platoon tempOpponentPlatoon = parsePlatoon(opponentInput);
        List<Soldier> canWinNothing = new ArrayList<>();

        Soldier[] bestSequence = new Soldier[myPlatoon.getSoldiers().size()];
        int i = 0;
        // Find the best platoons to fight
        while (i < myPlatoon.getSoldiers().size()) {
            Soldier bestPlatoon = findBestPlatoon(myPlatoon, opponentPlatoon, i++);
            if (bestPlatoon == null) canWinNothing.add(myPlatoon.getSoldiers().get(i - 1));
            else {
                int index = 0;
                for (int j = 0; j < tempOpponentPlatoon.getSoldiers().size(); j++) {
                    if (tempOpponentPlatoon.getSoldiers().get(j).getType().equals(bestPlatoon.getType())) {
                        index = j;
                        break;
                    }
                }
                bestSequence[index] = myPlatoon.getSoldiers().get(i - 1);
                opponentPlatoon.getSoldiers().remove(bestPlatoon);
            }
        }
        int win = 0;
        for (Soldier s : bestSequence) {
            if (s != null) win++;
        }
        if (win >= 3) {
            int k = 0;
            for (int j = 0; j < bestSequence.length; j++) {
                if (bestSequence[j] == null) bestSequence[j] = canWinNothing.get(k++);
            }
            return bestSequence;
        }
        System.out.println("There is no chance of winning");
        return new Soldier[0];
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

    private static Soldier findBestPlatoon(Platoon myPlatoon, Platoon opponentPlatoons, int index) {
        Soldier bestPlatoonToFight = null;
        int minAdvantage = Integer.MAX_VALUE;

        Soldier myPlatoonName = myPlatoon.getSoldiers().get(index);
        for (Soldier opponentPlatoon : opponentPlatoons.getSoldiers()) {
            BattleSimulator battleSimulator = new BattleSimulator();
            int advantage = battleSimulator.calculateAdvantage(myPlatoonName, opponentPlatoon);
            if (advantage > 0 && advantage < minAdvantage) {
                minAdvantage = advantage;
                bestPlatoonToFight = opponentPlatoon;
            }
        }
        return bestPlatoonToFight;
    }

}
