package org.example.optimized;

import java.util.*;
import java.util.stream.Collectors;

public class AgeOfWarOptimised {

    // Lists to store the order of units for the user and the opponent
    static List<String> myOrder = new ArrayList<>();
    static List<String> opponentOrder = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            // Prompt the user to enter their platoon
            System.out.println("Enter your platoon (e.g., Spearmen#10;Militia#30;FootArcher#20):");
            String myInput = sc.nextLine();
            System.out.println("Please enter Opponent Platoon: ");
            String opponentInput = sc.nextLine();
            // Call the function to determine the winning sequence
            winningSequence(myInput, opponentInput);
            System.out.print("Exit:Y/N  ");
            String exitStatus = sc.nextLine().toUpperCase();
            if (exitStatus.equals("Y")) break;
            // Clear the lists for the next inputs.
            myOrder.clear();
            opponentOrder.clear();
        }
        sc.close();
    }

    public static void winningSequence(String myInput, String opponentInput) {
        // Parse the platoons provided by the user and opponent
        Map<String, Integer> myPlatoons = parsePlatoons(myInput, true);
        Map<String, Integer> opponentPlatoons = parsePlatoons(opponentInput, false);
        List<String> tempOpponentOrder = new ArrayList<>(opponentOrder);
        List<String> canWinNothing = new ArrayList<>();

        String[] bestSequence = new String[myOrder.size()];
        int i = 0;
        // Find the best platoons to fight
        while (i < myPlatoons.size()) {
            String bestPlatoon = findBestPlatoon(myOrder, opponentOrder, myPlatoons, opponentPlatoons, i++);
            if (bestPlatoon == null) canWinNothing.add(myOrder.get(i - 1));
            else {
                int index = tempOpponentOrder.indexOf(bestPlatoon);
                bestSequence[index] = myOrder.get(i - 1);
                opponentOrder.remove(bestPlatoon);
            }
        }
        int win = 0;
        for (String s : bestSequence) {
            if (s != null) win++;
        }
        if (win >= 3) {
            int k = 0;
            for (int j = 0; j < bestSequence.length; j++) {
                if (bestSequence[j] == null) bestSequence[j] = canWinNothing.get(k++);
            }
            // Display the winning sequence
            System.out.println("Winning Sequence");
            System.out.println(Arrays.stream(bestSequence)
                    .map(unit -> unit + "#" + myPlatoons.get(unit))
                    .collect(Collectors.joining(";")));
        } else {
            System.out.println("There is no chance of winning");
        }
    }

    private static Map<String, Integer> parsePlatoons(String order, boolean myorder) {
        // Parse the platoon orders and store them in a map
        Map<String, Integer> platoons = new HashMap<>();
        String[] parts = order.split(";");
        for (String part : parts) {
            String[] tokens = part.split("#");
            try {
                Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: Invalid command syntax. Please provide a numerical value for the number of soldiers");
            }
            if (myorder) myOrder.add(tokens[0]);
            else opponentOrder.add(tokens[0]);
            platoons.put(tokens[0], Integer.parseInt(tokens[1]));
        }
        return platoons;
    }

    private static String findBestPlatoon(List<String> myPlatoonOrder, List<String> opponentPlatoonOrder,
                                          Map<String, Integer> myPlatoons, Map<String, Integer> opponentPlatoons, int index) {
        // Find the best platoon to fight based on advantages
        String bestPlatoonToFight = null;
        int minAdvantage = Integer.MAX_VALUE; // Initialize to a large value

        String myPlatoon = myPlatoonOrder.get(index);
        int myCount = myPlatoons.get(myPlatoonOrder.get(index));
        for (String opponentPlatoon : opponentPlatoonOrder) {
            int opponentCount = opponentPlatoons.get(opponentPlatoon);
            int advantage = calculateAdvantage(myPlatoon, myCount, opponentPlatoon, opponentCount);
            if (advantage > 0 && advantage < minAdvantage) {
                minAdvantage = advantage;
                bestPlatoonToFight = opponentPlatoon;
            }
        }
        return bestPlatoonToFight;
    }

    private static int calculateAdvantage(String myUnit, int myCount, String opponentUnit, int opponentCount) {
        // Calculate the advantage of one unit over another
        if (myUnit.equals(opponentUnit)) {
            return myCount - opponentCount;
        }

        Set<String> myAdvantages = getAdvantages(myUnit);
        Set<String> opponentAdvantages = getAdvantages(opponentUnit);

        if (myAdvantages.contains(opponentUnit)) {
            return myCount * 2 - opponentCount;
        } else if (opponentAdvantages.contains(myUnit)) {
            return myCount - opponentCount * 2;
        }

        return myCount - opponentCount;
    }

    static Map<String, Set<String>> unitAdvantages = new HashMap<>();
    static boolean hasBeenStored = false;

    private static Set<String> getAdvantages(String unit) {
        // Store unit advantages in a map
        if (!hasBeenStored) {
            unitAdvantages.put("Militia", new HashSet<>(Arrays.asList("Spearmen", "LightCavalry")));
            unitAdvantages.put("Spearmen", new HashSet<>(Arrays.asList("LightCavalry", "HeavyCavalry")));
            unitAdvantages.put("LightCavalry", new HashSet<>(Arrays.asList("FootArcher", "CavalryArcher")));
            unitAdvantages.put("HeavyCavalry", new HashSet<>(Arrays.asList("Militia", "FootArcher", "LightCavalry")));
            unitAdvantages.put("CavalryArcher", new HashSet<>(Arrays.asList("Spearmen", "HeavyCavalry")));
            unitAdvantages.put("FootArcher", new HashSet<>(Arrays.asList("Militia", "CavalryArcher")));
        }
        return unitAdvantages.get(unit);
    }
}
