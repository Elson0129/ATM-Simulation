
public class Account {

	private String custName; 
	protected int custId;
	protected int custPin; 
	protected double savingsBal;
	protected double checkingBal; 
	
	public Account (String custName, int custId, int custPin, double savingsBal, double checkingBal) {
		this.custName = custName;
		this.custId = custId;
		this.custPin = custPin;
		this.savingsBal = savingsBal;
		this.checkingBal = checkingBal;
	}
	
	public String getCustName() { 
		return custName;
	}
	
	public int getCustId() {
		return custId;
	}
	
	public int getCustPin() {
		return custPin;
	}
	
	public double getSavingsBal() { 
		return savingsBal;
	}
	
	public double getCheckingBal() { 
		return checkingBal;
	}
	
	
	public Operation withdrawalFromChecking(double amt) {
		if(checkingBal < amt) {
			return new Operation(false, "Insufficient Funds");
		}
		else {
			checkingBal = checkingBal - amt;
			return new Operation(true, "Transaction Successful");
		}
	}
	
	public Operation withdrawalFromSavings(double amt) {
		if(savingsBal < amt) {
			return new Operation(false, "Insufficient Funds");
		} 
		else {
			savingsBal = savingsBal - amt;
			return new Operation(true, "Transaction Successful");
		}
	}
	
	public Operation depositIntoChecking(double amt) {
		if(amt > 0) {
			checkingBal = checkingBal + amt;
			return new Operation(true, "Transaction Successful");
		} 
		else {
			
			return new Operation(false, "Invalid Deposit amount");
		}
	}
	
	public Operation depositIntoSavings(double amt) {
		if(amt > 0) {
			savingsBal = savingsBal + amt;
			return new Operation(true, "Transaction Successful");
		} 
		else {
			
			return new Operation(false, "Invalid Deposit amount");
		}
	}
}
