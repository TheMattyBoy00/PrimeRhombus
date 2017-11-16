package primerhombus.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RunThread extends JPanel implements Runnable {
	
	public long[][] grid = new long[1001][1001];
	public int x = 501;
	public int y = 501;
	private long value = 1;
	private List<int[]> coordsToDisplay = new ArrayList<int[]>();
	private boolean isGenerating = true;
	private PrimeRhombus rhombus;
	private int cursorX = 501;
	private int cursorY = 501;
	
	int moveTimer = 5;
	
	public RunThread(PrimeRhombus rhombus) {
		this.rhombus = rhombus;
	}

	@Override
	public void run() {
		while(true) {
			try {
				this.grid[x][y] = this.value++;
				if(PrimeRhombus.isPrime(this.grid[x][y])) {
					this.coordsToDisplay.add(new int[] {x, y});
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				this.isGenerating = false;
				break;
			}
			if(x <= 501 && y == 501) {
				this.y--;
			}
			else if(x >= 501 && y < 501) {
				this.x++;
				this.y++;
			}
			else if(x > 501 && y >= 501) {
				this.x--;
				this.y++;
			}
			else if(x <= 501 && y > 501) {
				this.x--;
				this.y--;
			}
			else if(x < 501 && y <= 501) {
				this.x++;
				this.y--;
			}
			this.repaint();
		}
		while(true) {
			if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_UP) && this.cursorY > 1) {
				this.cursorY--;
			}
			if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_DOWN) && this.cursorY < 1000) {
				this.cursorY++;
			}
			if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_LEFT) && this.cursorX > 1) {
				this.cursorX--;
			}
			if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_RIGHT) && this.cursorX < 1000) {
				this.cursorX++;
			}
			if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_O)) {
				this.cursorX = 501;
				this.cursorY = 501;
			}
			if(!this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_W) && !this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_S) && !this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_A) && !this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_D)) {
				this.moveTimer = 0;
			}
			else {
				if(moveTimer-- <= 0) {
					if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_W) && this.cursorY > 1) {
						this.cursorY--;
					}
					if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_S) && this.cursorY < 1000) {
						this.cursorY++;
					}
					if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_A) && this.cursorX > 1) {
						this.cursorX--;
					}
					if(this.rhombus.getKeyListener().isKeyPressed(KeyEvent.VK_D) && this.cursorX < 1000) {
						this.cursorX++;
					}
					this.moveTimer = 5;
				}
			}
			this.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		for(int i = 0; i < this.coordsToDisplay.size(); i++) {
			g.drawRect(this.coordsToDisplay.get(i)[0], this.coordsToDisplay.get(i)[1], 0, 0);
		}
		g.drawString("Status: " + (isGenerating ? "generating - " + ((int)(((double)this.value / 500001.0D) * 100)) + "% complete..." : "done!"), 10, 990);
		if(!this.isGenerating) {
			g.drawString("Coordinates: (" + this.cursorX + ", " + this.cursorY + ")", 10, 20);
			g.drawString("Value: " + this.grid[this.cursorX][this.cursorY], 10, 35);
			g.drawString("This is " + (PrimeRhombus.isPrime(this.grid[this.cursorX][this.cursorY]) ? "" : "not ") + "a prime number.", 10, 50);
			g.setColor(Color.RED);
			g.drawRect(this.cursorX - 1, this.cursorY - 1, 2, 2);
		}
		else {
			g.setColor(Color.BLUE);
			g.drawRect(this.x - 1, this.y - 1, 2, 2);
		}
	}

}
