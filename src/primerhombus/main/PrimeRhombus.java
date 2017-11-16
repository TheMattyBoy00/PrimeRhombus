package primerhombus.main;

import javax.swing.JFrame;

import primerhombus.io.KeyboardListener;

public class PrimeRhombus {
	
	public final JFrame window = new JFrame();
	private final RunThread thread;
	private final KeyboardListener keyboardlistener;
	
	public PrimeRhombus(int windowX, int windowY, String title) {
		this.window.setSize(windowX, windowY);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setFocusable(true);
		this.window.setLocationRelativeTo(null);
		this.window.setTitle(title);
		this.window.setResizable(false);
		
		this.thread = new RunThread(this);
		this.keyboardlistener = new KeyboardListener();
		this.window.add(thread);
		this.window.addKeyListener(keyboardlistener);
		new Thread(thread).start();
		
		this.window.setVisible(true);
	}
	
	public KeyboardListener getKeyListener() {
		return keyboardlistener;
	}

	public static boolean isPrime(long n) {
		if(n <= 1) {
			return false;
		}
		if(n > 2 && n % 2 == 0) {
			return false;
		}
		for(long i = 2; i < n / 2; i++) {
			if(n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
}
