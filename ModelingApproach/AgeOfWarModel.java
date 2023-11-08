package ModelingApproach;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AgeOfWarModel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your platoon (e.g., Spearmen#10;Militia#30;FootArcher#20):");
            String myInput = sc.nextLine();
            System.out.println("Please enter Opponent Platoon: ");
            String opponentInput = sc.nextLine();
            StrategyGenerator strategyGenerator = new StrategyGenerator();
            List<Soldier> winnerPlatoon = strategyGenerator.winningSequence(myInput, opponentInput);
            for (int i = 0; i < winnerPlatoon.size(); i++) {
                if (i == winnerPlatoon.size() - 1) {
                    Soldier soldier = winnerPlatoon.get(i);
                    System.out.print(soldier.getType() + "#" + soldier.getCount());
                    break;
                }
                Soldier soldier = winnerPlatoon.get(i);
                System.out.print(soldier.getType() + "#" + soldier.getCount() + ";");
            }
            System.out.println();
            System.out.print("Exit: Y/N  ");
            String exitStatus = sc.nextLine().toUpperCase();
            if (exitStatus.equals("Y")) break;
        }
        sc.close();
    }


}

