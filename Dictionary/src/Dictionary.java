import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * Ö÷Àà
 * @author anonymousXY
 *
 */
public class Dictionary {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
		
			@Override
			public void run() {
				MainWindows mainWindows = new MainWindows();
				mainWindows.setVisible(true);
				mainWindows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		
	}

}
