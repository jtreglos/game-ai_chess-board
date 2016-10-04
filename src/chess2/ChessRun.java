package chess2;

import processing.core.*;

public class ChessRun extends PApplet {
	private Game game;

	public void setup() {
		game = new Game(this);
	}
	
	public void settings() {
		size(1024, 768);
	}

	public void draw() {
		background(128);
		game.draw();
	}
	
	public void mouseClicked() {
		game.mouseClicked();
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { chess2.ChessRun.class.getName() });
	}
}
