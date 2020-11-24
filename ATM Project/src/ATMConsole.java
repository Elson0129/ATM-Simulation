import java.util.ArrayList;
import java.util.Scanner; 

//This is the text-based implementation 
public class ATMConsole {

	public static void main(String[] args) {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("John", 1001, 1234, 0.00, 0.00));
		accounts.add(new Account("Joe", 1002, 4321, 0.00, 0.00));
		accounts.add(new Account("Bobby", 1003, 1290, 0.00, 0.00));
		accounts.add(new Account("Sasha", 1004, 5678, 0.00, 0.00));
		accounts.add(new Account("Ahmad", 1005, 6962, 0.00, 0.00));
		
		
		
		ATMStateMachine sm = new ATMStateMachine(accounts);
		
		
		Scanner in = new Scanner(System.in);
		
		boolean terminate = false; 
		System.out.println("This is the text-based implementation of the ATM Project."
				+ "\nThe input represents the text field, so type in the numbers in input."
				+ "\ncmd represents the button inputs.");
		System.out.println("To Exit, type Q");
		System.out.println(sm.transition("","initialize"));
		while(!terminate) {
		
			System.out.print("Input > ");
			
			String input = in.nextLine();
			System.out.print("CMD > ");
			String cmd = in.nextLine();
			
			if(cmd.equalsIgnoreCase("Q")) {
				terminate = true; 
			} else { 
				System.out.println(sm.transition(input, cmd));
			}
		}
		
	}

}
