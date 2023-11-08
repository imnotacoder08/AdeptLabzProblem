package org.example.bruteForce;

import java.util.*;
import java.util.stream.Collectors;

public class AgeOfWar {
    static List<String> opponentOrder = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your platoon (e.g., Spearmen#10;Militia#30;FootArcher#20):");
            String myInput = sc.nextLine();
            System.out.println("Please enter Opponent Platoon: ");
            String opponentInput = sc.nextLine();
            // call the function to get the winning sequence.
            winningSequence(myInput, opponentInput);
            System.out.print("Exit:Y/N  ");
            String exitStatus = sc.nextLine().toUpperCase();
            if (exitStatus.equals("Y")) break;
        }
        sc.close();
    }

    public static void winningSequence(String myInput, String opponentInput) {
        Map<String, Integer> myPlatoons = parsePlatoons(myInput, true);//Store the Platoons and Strength in the Map.
        Map<String, Integer> opponentPlatoons = parsePlatoons(opponentInput,false);//Store the Platoons and Strength in the Map.
        List<String> myPlatoonOrder = new ArrayList<>(myPlatoons.keySet()); // store my platoon in the list
// generating all the permutation of my platoon and check the winning possibility with opponent sequence
        List<List<String>> permutations = generatePermutations(myPlatoonOrder);
// iterate all the permutations
        for (List<String> permutation : permutations) {
// check the permutation for winning
            int wins = simulateBattles(permutation, myPlatoons, opponentPlatoons);
            if (wins >= 3) {
                System.out.println(permutation.stream()
                        .map(unit -> unit + "#" + myPlatoons.get(unit))
                        .collect(Collectors.joining(";")));
                return;
            }
        }

        System.out.println("There is no chance of winning");
    }

    private static Map<String, Integer> parsePlatoons(String input,boolean myOrder) {
        Map<String, Integer> platoons = new HashMap<>();
        String[] parts = input.split(";");
        for (String part : parts) {
            String[] tokens = part.split("#");
            try {
                Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error: Invalid command syntax. Please provide numerical value for number of soldiers");
            }
            if (!myOrder) opponentOrder.add(tokens[0]);
            platoons.put(tokens[0], Integer.parseInt(tokens[1]));
        }
        return platoons;
    }

    private static List<List<String>> generatePermutations(List<String> platoonOrder) {
        List<List<String>> permutations = new ArrayList<>();
        permute(platoonOrder, 0, permutations);
        return permutations;
    }
// recursive function to generate all the permutations
    private static void permute(List<String> platoonOrder, int index, List<List<String>> permutations) {
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

    private static int simulateBattles(List<String> order, Map<String, Integer> myPlatoons, Map<String, Integer> opponentPlatoons) {
        int wins = 0;
        for (int i = 0; i < myPlatoons.size(); i++) {
            String myUnit = order.get(i);

            String opponentUnit = opponentOrder.get(i);
            int outcome = calculateBattleOutcome(myUnit, myPlatoons.get(myUnit), opponentUnit, opponentPlatoons.get(opponentUnit));
            if (outcome > 0) {
                wins++;
            }
        }
        return wins;
    }
// to check the battle outcome with current permutation sequence and opponent
    private static int calculateBattleOutcome(String myUnit, Integer myCount, String opponentUnit, Integer opponentCount) {
        if (myUnit.equals(opponentUnit)) {
            return myCount.compareTo(opponentCount);
        }
        Integer effectiveOpponentCount = opponentCount;
        Set<String> yourAdvantages = getAdvantages(myUnit);
        Set<String> opponentAdvantages = getAdvantages(opponentUnit);
        if (yourAdvantages.contains(opponentUnit)) {
            effectiveOpponentCount = opponentCount / 2;
        } else if(opponentAdvantages.contains(myUnit)){
            effectiveOpponentCount = opponentCount * 2;
        }
        return (myCount > effectiveOpponentCount) ? 1 : -1;
    }
// to store the one platoon's advantage over another
    static Map<String, Set<String>> unitAdvantages = new HashMap<>();
    static boolean hasBeenStored = false;

    private static Set<String> getAdvantages(String unit) {
        if(!hasBeenStored) {
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

