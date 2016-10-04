package chess2;

import java.util.*;
import processing.core.*;

public class CaptureSet {
	private List<Piece> pieces;
	
	public CaptureSet() {
		pieces = new ArrayList<Piece>();
	}
	
	public void add(Piece piece) {
		pieces.add(piece);
	}
	
	public void remove(Piece piece) {
		pieces.remove(piece);
	}
	
	public void addAll(CaptureSet c) {
		pieces.addAll(c.pieces);
	}
	
	public boolean contains(Piece piece) {
		return pieces.contains(piece);
	}
	
	public void clear() {
		pieces.clear();
	}
	
	public List<Piece> get() {
		return pieces;
	}
	
	public Piece first() {
		if (pieces.isEmpty()) {
			return null;
		} else {
			return pieces.get(0);
		}
	}
	
	@Override
	public String toString() {
		return pieces.toString();
	}
	
	public int draw(Game game, int y_offset) {
		PApplet p = game.getPapplet();
		int tile_size = game.getTileSize();
		int font_size = 30;
		int x0 = 2 * Game.MARGIN + 8 * tile_size;
		int y0 = y_offset;
		int i = 0;
		int x = x0;
		int y = y0;
		PFont f = p.createFont("HelveticaNeue-Bold", font_size, true);
		p.textFont(f);
		p.textAlign(PApplet.CORNER);
		
		for (Piece piece : pieces) {
			if (piece.getColor() == Color.BLACK) {
				p.fill(80);
			} else {
				p.fill(220);
			}
			x = x0 + (i % 4) * 50;
			y = y0 + (i / 4) * 50;
			p.text(piece.toChar(), x, y);
			i++;
		}
		
		return y + Game.MARGIN;
	}
}
