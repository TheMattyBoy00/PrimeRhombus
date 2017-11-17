package primerhombus.main;

import javax.swing.JFrame;

import primerhombus.io.KeyboardListener;

public class PrimeRhombus {
	
	public final JFrame window = new JFrame();
	private final RunThread thread;
	private final KeyboardListener keyboardlistener;
	public final int size;
	
	public PrimeRhombus(int size, String title) {
		this.window.setSize(size + 8, size + 30);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setFocusable(true);
		this.window.setLocationRelativeTo(null);
		this.window.setTitle(title);
		this.window.setResizable(false);
		
		this.size = size;
		this.keyboardlistener = new KeyboardListener();
		this.window.addKeyListener(keyboardlistener);
		this.thread = new RunThread(this);
		this.window.add(thread);
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
