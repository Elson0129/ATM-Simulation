
import java.util.ArrayList;
import java.util.Scanner; 

public class ATM {

	private static ArrayList<Account> accounts;
	private static Account currAccount;

	
	public boolean login(int inputId, int inputPin) {
		for(int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			if(inputId == account.custId && inputPin == account.custPin) {	
				currAccount = account;
				return true;	
			}
		}
		return false;
	}
	
	public Operation withdrawalFromChecking(double amt) {
		return currAccount.withdrawalFromChecking(amt);
	}
	
	public static void main(String[] args) {
	
		ATM atm = new ATM();
		KeyPad keyPad = new KeyPad();
		keyPad.initialize();
		
		
		
	}

}
