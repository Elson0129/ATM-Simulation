import java.util.ArrayList;

public class ATMStateMachine {

	private int customerId;
	private int customerPin;
	
	private ArrayList<Account> accounts;
	private Account currAccount;
	
	private boolean isWithdrawal = false;
	private boolean isChecking = false;
	
	
	private String state = "START";
	private final String startState = "START";
	private final String idState = "ENTERING_CUSTOMER_ID";
	private final String pinState = "ENTERING_CUSTOMER_PIN";
	private final String accountState = "ENTERING_ACCOUNT_TYPE";
	private final String transState = "CHOOSING_TRANSACTION_TYPE";
	
	
	public ATMStateMachine(ArrayList<Account> accounts) { 
		this.accounts = accounts;
		
	}
	
	
	public String transition(String entry, String cmd) { 
		if(cmd.equalsIgnoreCase("A")) {
			
			if(state.equals(accountState)) {
				isChecking = true;
			} else if(state.equals("CHOOSING_TRANSACTION_TYPE")) {
				isWithdrawal = true;
			}
			
			return run(null, entry);
			
		} else if(cmd.equalsIgnoreCase("B")) {
			
			if(state.equals(accountState)) {
				isChecking = false;		
				
			} else if(state.equals("CHOOSING_TRANSACTION_TYPE")) {
				isWithdrawal = false;					
			}
			
			return run(null, entry);	
			
		} else if(cmd.equalsIgnoreCase("C")) 
		{
			return goBack();
		} 
		else if(cmd.equalsIgnoreCase("initialize"))
		{
			return run(null, "");
		} 
		else 
		{ 
			return run(null, entry);
		}
		
		
		
	}
	
	
	public String run(String prefix, String input) {
		
		String returnOutput = "";

		if(state.equals(startState)) {
			if(prefix == null) {
				returnOutput = 	"Enter Customer ID\nA = OK\nC = Exit";
				
			} else { 
				returnOutput = prefix + "\nEnter Customer ID\nA = OK\nC = Exit";
			}
			
			
			
			state = idState;
		} else if(state.equals(idState)) {
			
			
			
			try { 
				customerId = Integer.parseInt(input);
				
				
			} catch (Exception e){
			
				state = startState;
						
				return run("ERROR: Invalid ID Entered!", ""); 
				
			}
					
			
			
			if(isAccountId(customerId)) {
				state = pinState;
				
				returnOutput = "Enter Customer PIN\nA = OK\nC = Cancel";
				
			} 
			else {
				returnOutput = "Customer ID not found!\nEnter Customer ID\nA = OK\nC = Exit";

			}
			
			
			
		} else if(state.equals(pinState)) {
			
			try { 
				customerPin = Integer.parseInt(input);
				
				
			} catch (Exception e){
				
				state = startState;
				
				return run("ERROR: Invalid PIN Entered!", ""); 
				
			}
			
			
			if(isAccountPin(customerPin)) {
				state = accountState;
				returnOutput = "Login Successful! Welcome, " + currAccount.getCustName() + "!\nChoose Account Type:\nA = Checking Account\nB = Savings Account\nC = Cancel";
				
			} else 
			{
		
				state = "START";
				return run("Incorrect Credentials!\n", "");
			}
			
		} else if(state.equals(accountState)) {
			
			if(isChecking == false) {
				
				if(prefix == null) {
					returnOutput = "Current Balance: " + currAccount.savingsBal + "\n";
					
				} else  {
					returnOutput = prefix + "\nCurrent Balance: " + currAccount.savingsBal + "\n";			
				}
				
			} else { 
				
				if(prefix == null) {
					returnOutput = "Current Balance: " + currAccount.checkingBal + "\n";
					
				} else  {
					returnOutput = prefix + "\nCurrent Balance: " + currAccount.checkingBal + "\n";
				}
			}
			
			
			returnOutput += "Choose Transaction Type\nA = Withdrawal\nB = Deposit\nC = Cancel";
			
			state = transState;
		} else if(state.equals(transState)) {
			returnOutput = "Enter Amount\nA = OK\nC = Cancel";
			
			
			state = "ENTERING_TRANSACTION_AMOUNT";
		} else if(state.equals("ENTERING_TRANSACTION_AMOUNT")) {
			double amount = Double.parseDouble(input);
		
			
			
			
			Operation operation = null;
			
			if(isChecking == true && isWithdrawal == true) { //withdraw from checking 
			     operation = currAccount.withdrawalFromChecking(amount);
				if(operation.isSuccesful) {
					
					returnOutput = "Transaction Successful!\nDispensing Cash Now...\nUpdated Balance: " + currAccount.checkingBal;
					
				} else {
				
					returnOutput = "ERROR: Insufficient Funds!";
											
				}
				state = accountState;
				
			} else if(isChecking == false && isWithdrawal == true) { //withdraw from saving 
				operation  = currAccount.withdrawalFromSavings(amount);
				if(operation.isSuccesful) {
					
					returnOutput = "Transaction Successful!\nDispensing Cash Now...\nUpdated Balance: " + currAccount.savingsBal;	
				} else { 
					
					returnOutput = "ERROR: Insufficient Funds!";
				}
				
				state = accountState;
				
			} else if(isChecking == true && isWithdrawal == false) { //deposit to checking 
				 operation = currAccount.depositIntoChecking(amount);
				if(operation.isSuccesful) {
					
					returnOutput = "Transaction Successful!\nUpdated Balance: " + currAccount.checkingBal;
				} else { 
					
					returnOutput = "ERROR: Invalid Deposit Amount Entered!";
				}
				
				state = accountState;
				
			} else if(isChecking == false && isWithdrawal == false) { //deposit to savings 
			    operation = currAccount.depositIntoSavings(amount);
				if(operation.isSuccesful) {
					
					returnOutput = "Transaction Successful!\nUpdated Balance: " + currAccount.savingsBal;	
				} else { 
					
					returnOutput = "ERROR: Invalid Deposit Amount Entered!";
				}
			}
			
			state = accountState;
			returnOutput += "\nChoose Account Type:\nA = Checking Account\nB = Savings Account\nC = Cancel";
		
			
			
		}
		
		return returnOutput; 
		
	}
	
	public String goBack() { 
		
		String output = "";
		
		if(state.equals("ENTERING_TRANSACTION_AMOUNT")) 
		{

			state = accountState;
			return run(null, "");
		} 
		else if(state.equals(transState)) 
		{
			state = accountState;
			output = "Choose Account Type:\nA = Checking Account\nB = Savings Account\nC = Cancel";
			
	
		
		} 
		else if(state.equals(accountState)) 
		{
			state = "START";
		    return run(null, "");
		
		} else if(state.equals(pinState)) {
			state = idState;
			return run("Enter Customer ID\nA = OK\nC = Exit", "");
		}
		else if(state.equals(idState)) {
			System.exit(0);

		}
		
		return output;
		
	}
	
	public boolean isAccountId(int inputId) {
		for(int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			if(inputId == account.custId) {
				currAccount = account;
				return true;
			} 
			
		}
		return false;
		
	}
	
	public boolean isAccountPin(int inputPin) {
		
		if(currAccount.custPin == inputPin) {
			return true;
		}
		
		return false;
	}
	
}
