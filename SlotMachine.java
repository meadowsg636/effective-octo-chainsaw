
public class SlotMachine {
	
	    public SlotMachine() {
	    }

	    public void SlotMachine(){
	    }

	    public double pull(int x) {
	        int result = (int)(Math.random() * 100000.0D);
	        
	        double money = 0.0D;
	        if (x == 1) {
	            money = 0.25D;
	        } else if (x == 2) {
	            money = 0.5D;
	        } else {
	            if (x != 3) {
	                return -1.0D;
	            }

	            money = 0.75D;
	        }

	        
	        //This is the problem
	        if (x==3 && result <= 100) {
	            return money * 2000.0D;
	            //Why is this here. This makes the chance much higher.
	        } else if (result == 0) {
	            return money * 2000.0D;
	        } else if (result <= 50) {
	            return money * 200.0D;
	        } else if (result <= 500) {
	            return money * 100.0D;
	        } else if (result <= 2000) {
	            return money * 2.0D;
	        } else {
	            return result <= 20000 ? money * 1.0D : 0.0D;
	        }
	        
	    }
	}

