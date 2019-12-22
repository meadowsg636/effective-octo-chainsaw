import java.text.NumberFormat;

public class SlotMachineAssignment {

    public static final int NUMBER_OF_PULLS = 10000;

    public static void main(String[] args) {
		printResults(testSlotMachine(NUMBER_OF_PULLS, 1));
		printResults(testSlotMachine(NUMBER_OF_PULLS, 2));
		printResults(testSlotMachine(NUMBER_OF_PULLS, 3));
        alwaysLoseTest(1);
        alwaysLoseTest(2);
        alwaysLoseTest(3);
        jackpotChanceTest(1);
        jackpotChanceTest(2);
        jackpotChanceTest(3);
    }

    public static void jackpotChanceTest(int numQuarters) {
        SlotMachineResults results = testSlotMachine(NUMBER_OF_PULLS, numQuarters);
        double jackpotChance = (double) results.scores[5] / NUMBER_OF_PULLS;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(5);
        nf.setMaximumFractionDigits(5);
        System.out.println("With " + numQuarters + " quarters, chance of jackpot is " + nf.format(jackpotChance));
    }

    public static void alwaysLoseTest(int numQuarters) {
        System.out.println(numQuarters + " quarters:");
        System.out.println("  Always lose in the long term? " + alwaysLoseTestLongTerm(numQuarters));
        System.out.println("  Always lose with a single pull? " + alwaysLoseTestSinglePull(numQuarters));
    }

    public static boolean alwaysLoseTestSinglePull(int numQuarters) {
        for (int i = 0; i < NUMBER_OF_PULLS; i++) {
            SlotMachineResults singlePullResults = testSlotMachine(1, numQuarters);
            if (singlePullResults.totalWinnings >= singlePullResults.totalCost) {
                return false;
            }
        }
        return true;
    }

    public static boolean alwaysLoseTestLongTerm(int numQuarters) {
        SlotMachineResults longTermResults = testSlotMachine(NUMBER_OF_PULLS, numQuarters);
        return longTermResults.totalWinnings < longTermResults.totalCost;
    }
    
    public static SlotMachineResults testSlotMachine(int numPulls, int numQuarters) {
        SlotMachineResults results = new SlotMachineResults();
        results.numPulls = numPulls;
        results.numQuarters = numQuarters;
		SlotMachine johnnysOneArmBandit = new SlotMachine();
		
		//"Pulls" the slot machine with number of quarters specified amount of times
		for (int i = 0; i < numPulls; i++) {
            double singlePullWinnings = johnnysOneArmBandit.pull(numQuarters);
            results.totalWinnings += singlePullWinnings;
			if (singlePullWinnings == 0) {
				results.scores[0]++;
			} else if (singlePullWinnings == .25 * numQuarters) {
                results.scores[1]++;
			} else if (singlePullWinnings == .50 * numQuarters) {
                results.scores[2]++;
			} else if (singlePullWinnings == 25 * numQuarters) {
                results.scores[3]++;
			} else if (singlePullWinnings == 50 * numQuarters) {
                results.scores[4]++;
			} else if (singlePullWinnings == 500 * numQuarters) {
                results.scores[5]++;
			}
		}
        results.totalCost = 0.25 * numQuarters * numPulls;
		return results;
    }
    //This will print the results of each pull with the specified amount of quarters, the specified number of times
    public static void printResults(SlotMachineResults results) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        System.out.println("Number of pulls: " + results.numPulls);
        System.out.println("Quarters per pull: " + results.numQuarters);
        System.out.println("Total spent: " + nf.format(results.totalCost));
        System.out.println("Total winnings: " + nf.format(results.totalWinnings));
        System.out.println("Profit/Loss: " + nf.format(results.totalWinnings - results.totalCost));
        System.out.println("Losses " + results.scores[0]);
        System.out.println("Free Plays " + results.scores[1]);
        System.out.println("Doubles " + results.scores[2]);
        System.out.println("Mini Jackpots " + results.scores[3]);
        System.out.println("Bonuses " + results.scores[4]);
        System.out.println("Jackpots " + results.scores[5]);
        System.out.println("------------------");
    }
    
    //This will hold the results of each test Allowing for it to be accessed easily
    public static class SlotMachineResults {

        public int numPulls;
        public int numQuarters;
        public int[] scores = new int[6];
        public double totalWinnings;
        public double totalCost;

    }
}


