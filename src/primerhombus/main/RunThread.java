package primerhombus.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import primerhombus.io.KeyboardListener;

@SuppressWarnings("serial")
public class RunThread extends JPanel implements Runnable {
	
	public long[][] grid;
	public int x;
	public int y;
	private long value = 1;
	private List<int[]> coordsToDisplay = new ArrayList<int[]>();
	private boolean isGenerating = true;
	private KeyboardListener keyListener;
	private int cursorX;
	private int cursorY;
	private int size;
	private int offsetX;
	private int offsetY;
	
	int moveTimer = 5;
	
	public RunThread(PrimeRhombus rhombus) {
		this.keyListener = rhombus.getKeyListener();
		this.size = rhombus.size;
		this.grid = new long[rhombus.size][rhombus.size];
		this.x = rhombus.size / 2 + 1;
		this.y = rhombus.size / 2 + 1;
		this.cursorX = rhombus.size / 2 + 1;
		this.cursorY = rhombus.size / 2 + 1;
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
			if(x <= this.size / 2 + 1 && y == this.size / 2 + 1) {
				this.y--;
			}
			else if(x >= this.size / 2 + 1 && y < this.size / 2 + 1) {
				this.x++;
				this.y++;
			}
			else if(x > this.size / 2 + 1 && y >= this.size / 2 + 1) {
				this.x--;
				this.y++;
			}
			else if(x <= this.size / 2 + 1 && y > this.size / 2 + 1) {
				this.x--;
				this.y--;
			}
			else if(x < this.size / 2 + 1 && y <= this.size / 2 + 1) {
				this.x++;
				this.y--;
			}
			this.repaint();
		}
		while(true) {
			if(this.keyListener.isKeyPressed(KeyEvent.VK_UP) && this.cursorY > 1) {
				this.cursorY--;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_DOWN) && this.cursorY < this.size - 1) {
				this.cursorY++;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_LEFT) && this.cursorX > 1) {
				this.cursorX--;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_RIGHT) && this.cursorX < this.size - 1) {
				this.cursorX++;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_O)) {
				this.cursorX = this.size / 2 + 1;
				this.cursorY = this.size / 2 + 1;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_PAGE_UP)) {
				this.offsetY += 5;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_PAGE_DOWN)) {
				this.offsetY -= 5;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_HOME)) {
				this.offsetX += 5;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_END)) {
				this.offsetX -= 5;
			}
			if(this.keyListener.isKeyPressed(KeyEvent.VK_Z)) {
				this.offsetX = 0;
				this.offsetY = 0;
			}
			if(!this.keyListener.isKeyPressed(KeyEvent.VK_W) && !this.keyListener.isKeyPressed(KeyEvent.VK_S) && !this.keyListener.isKeyPressed(KeyEvent.VK_A) && !this.keyListener.isKeyPressed(KeyEvent.VK_D)) {
				this.moveTimer = 0;
			}
			else {
				if(moveTimer-- <= 0) {
					if(this.keyListener.isKeyPressed(KeyEvent.VK_W) && this.cursorY > 1) {
						this.cursorY--;
					}
					if(this.keyListener.isKeyPressed(KeyEvent.VK_S) && this.cursorY < this.size - 1) {
						this.cursorY++;
					}
					if(this.keyListener.isKeyPressed(KeyEvent.VK_A) && this.cursorX > 1) {
						this.cursorX--;
					}
					if(this.keyListener.isKeyPressed(KeyEvent.VK_D) && this.cursorX < this.size - 1) {
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
		for(int i = 0; i < this.coordsToDisplay.size(); i++) {
			g.setColor(this.coordsToDisplay.get(i)[0] == this.size / 2 + 1 || this.coordsToDisplay.get(i)[1] == this.size / 2 + 1 ? Color.BLUE : Color.BLACK);
			g.drawRect(this.coordsToDisplay.get(i)[0] + this.offsetX, this.coordsToDisplay.get(i)[1] + this.offsetY, 0, 0);
		}
		g.setColor(Color.BLACK);
		g.drawString("Status: " + (isGenerating ? "generating - " + ((int)(((double)this.value / (this.size * this.size / 2 - 2 * this.size + 9)) * 100)) + "% complete..." : "done!"), 10, /*this.size - 11*/20);
		if(!this.isGenerating) {
			g.drawString("Coordinates: (" + (this.cursorX <= this.size / 2 + 1 ? this.size / 2 - this.cursorX + 2 : this.cursorX - this.size / 2) + ", " + (this.cursorY <= this.size / 2 + 1 ? this.size / 2 - this.cursorY + 2 : this.cursorY - this.size / 2) + ")", 10, 35);
			g.drawString("Value: " + this.grid[this.cursorX][this.cursorY], 10, 50);
			g.drawString("This is " + (PrimeRhombus.isPrime(this.grid[this.cursorX][this.cursorY]) ? "" : "not ") + "a prime number.", 10, 65);
			g.setColor(Color.RED);
			g.drawRect(this.cursorX - 1 + this.offsetX, this.cursorY - 1 + this.offsetY, 2, 2);
		}
		else {
			g.setColor(Color.BLUE);
			g.drawRect(this.x - 1, this.y - 1, 2, 2);
		}
	}

}
