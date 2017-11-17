package primerhombus.main;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		int size;
		while(true) {
			try { 
				size = Integer.parseInt(JOptionPane.showInputDialog("Enter an odd number greater than one as the lenght of the window."));
				if(size <= 1) {
					throw new NumberFormatException();
				}
				if(size % 2 == 0) {
					throw new NumberFormatException();
				}
				break;
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		@SuppressWarnings("unused")
		PrimeRhombus primeRhombus = new PrimeRhombus(size, "The Prime Rhombus");
	}

}
