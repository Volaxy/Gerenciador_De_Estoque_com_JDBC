package services;

import javax.swing.JOptionPane;

public class InputsService {

	public static void validateNumbers(String... numbers) {
		try {
			for (String string : numbers) {
				Double.parseDouble(string);
			}
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar inserir, os campos devem ser preenchidos com números", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
}
