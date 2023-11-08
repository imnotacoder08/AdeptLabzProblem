package ModellingApproachOptimised;

import java.util.Scanner;

public class AgeOfWarModel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your platoon (e.g., Spearmen#10;Militia#30;FootArcher#20):");
            String myInput = sc.nextLine();
            System.out.println("Please enter Opponent Platoon: ");
            String opponentInput = sc.nextLine();
            StrategyGenerator strategyGenerator = new StrategyGenerator();
            Soldier[] winnerPlatoon = strategyGenerator.winningSequence(myInput, opponentInput);
            for (int i = 0; i < winnerPlatoon.length; i++) {
                if (i == winnerPlatoon.length - 1) {
                    Soldier name = winnerPlatoon[i];
                    System.out.print(name.getType() + "#" + name.getCount());
                    break;
                }
                Soldier name = winnerPlatoon[i];
                System.out.print(name.getType() + "#" + name.getCount() + ";");
            }
            System.out.println();
            System.out.print("Exit: Y/N  ");
            String exitStatus = sc.nextLine().toUpperCase();
            if (exitStatus.equals("Y")) break;
        }
        sc.close();
    }


}

