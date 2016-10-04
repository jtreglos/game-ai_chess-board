package chess2;

import java.util.*;
import processing.core.*;

public class MoveSet {
	private List<Move> moves;
	
	public MoveSet() {
		moves = new ArrayList<Move>();
	}
	
	public void add(Move move) {
		moves.add(move);
	}
	
	public void addAll(MoveSet c) {
		moves.addAll(c.moves);
	}
	
	public boolean contains(Move move) {
		return moves.contains(move);
	}
	
	public List<Move> get() {
		return moves;
	}
	
	public void clear() {
		moves.clear();
	}
	
	public Move first() {
		if (moves.isEmpty()) {
			return null;
		} else {
			return moves.get(0);
		}
	}
	
	@Override
	public String toString() {
		return moves.toString();
	}
	
	public void draw(Game game) {
		PApplet p = game.getPapplet();
		PFont f = p.createFont("HelveticaNeue-Bold", 10, true);
		p.textFont(f);
		p.textAlign(PApplet.LEFT);
		p.fill(220);
		p.text(toString(), Game.MARGIN, Game.MARGIN);
	}
}
