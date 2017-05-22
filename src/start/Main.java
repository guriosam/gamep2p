package start;

import javax.swing.JFrame;

import entities.Arena;

public class Main {

	public static void main(String[] args) {

		try {
			
			JFrame janela = new JFrame("ONLINE SHOOTER");
			janela.setContentPane(new Arena());
			janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			janela.setResizable(false);
			janela.setLocation(600, 200);
			janela.pack();
			janela.setVisible(true);
			
			//Arena arena = new Arena();
			//arena.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
