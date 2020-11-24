import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
public class KeyPad  {

	private JTextField displayField;
	private JButton clearButton;
	private JButton aButton;
	private JButton bButton; 
	private JButton cButton;
	private JPanel panel;
	private JTextArea outputArea;
	

	private ATMStateMachine sm; 
	
	
	public KeyPad() {
		
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account("John", 1001, 1234, 1000.00, 1000.00));
		accounts.add(new Account("Joe", 1002, 4321, 0.00, 200.00));
		accounts.add(new Account("Bobby", 1003, 1290, 1000.00, 10000.00));
		accounts.add(new Account("Sasha", 1004, 5678, 2450.00, 500.00));
		
		sm = new ATMStateMachine(accounts);
		
		JFrame frame = new JFrame("ATM");
		JPanel miniPanel = new JPanel();
		miniPanel.setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,3));
		
	
		newButton("7", buttons);
		newButton("8", buttons);
		newButton("9", buttons);
		newButton("4", buttons);
		newButton("5", buttons);
		newButton("6", buttons);
		newButton("1", buttons);
		newButton("2", buttons);
		newButton("3", buttons);
		newButton(".", buttons);
		newButton("0", buttons);
		clearButton = new JButton("CE");
		buttons.add(clearButton);
		
			
		displayField = new JTextField(20);
		
		miniPanel.add(displayField, BorderLayout.NORTH);
		miniPanel.add(buttons, BorderLayout.CENTER);
		
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new BorderLayout());
		JPanel confirmButtons = new JPanel();
		confirmButtons.setLayout(new GridLayout(0,3));
			
	
		aButton = new JButton("A");		
		confirmButtons.add(aButton);
		
		bButton = new JButton("B");
		confirmButtons.add(bButton);
		
		cButton = new JButton("C");
		confirmButtons.add(cButton);
		
		outputArea = new JTextArea();
		detailPanel.add(outputArea, BorderLayout.CENTER);
		detailPanel.add(confirmButtons, BorderLayout.SOUTH);
		
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.add(miniPanel);
		panel.add(detailPanel);
		
		
	    
	
	
		class ClearButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {
				displayField.setText("");
				
			}
		}
		ActionListener listener = new ClearButtonListener();
		clearButton.addActionListener(new ClearButtonListener());
		
		class AButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				outputArea.setText(sm.transition(displayField.getText(), "A"));
				
				displayField.setText("");
				
				
				
			}
			
		}
		aButton.addActionListener(new AButtonListener());
		
		
		class BButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
	
				outputArea.setText(sm.transition(displayField.getText(), "B"));
				displayField.setText("");
				
			}
			
		}
		bButton.addActionListener(new BButtonListener());
		
		
		class CButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				outputArea.setText(sm.transition(displayField.getText(), "C"));
				displayField.setText("");
				
				
			}
			
		}
		cButton.addActionListener(new CButtonListener());

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack(); 
		frame.setVisible(true);
			
		
	}

	
	public void initialize() {
		outputArea.setText(sm.transition("", "initialize"));
	}
	

	
	private void newButton(final String label, JPanel panel) {
		class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(label.equals(".") && displayField.getText().indexOf(".") != -1) 
					return;
				displayField.setText(displayField.getText() + label);
				
			}
			
		}
		JButton button = new JButton(label);
		panel.add(button);
		ActionListener listener = new ButtonListener();
		button.addActionListener(listener);
	}
	
	public double getValue() {
		return Double.parseDouble(displayField.getText());
	}
	
	public void clear() {
		displayField.setText("");
	}
	

}
