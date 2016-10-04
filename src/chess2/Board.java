package chess2;

import java.util.*;
import processing.core.*;

public class Board {
	private Game game;
	private Map<Position, Piece> pieces;
	private static Board board_instance=null;
	
	public static Board getBoard() {
		if (board_instance == null) {		
			board_instance = new Board();
		}
		
		return board_instance;
	}
	
	private Board() {
		pieces = new HashMap<Position, Piece>();
	}
	
	public Board init() {
		String[] white_pieces = {"WR:a1", "WR:h1", "WN:b1", "WN:g1", "WB:c1", "WB:f1", "WQ:d1", "WK:e1", "Wp:a2", "Wp:b2", "Wp:c2", "Wp:d2", "Wp:e2", "Wp:f2", "Wp:g2", "Wp:h2"};
		String[] black_pieces = {"BR:a8", "BR:h8", "BN:b8", "BN:g8", "BB:c8", "BB:f8", "BQ:d8", "BK:e8", "Bp:a7", "Bp:b7", "Bp:c7", "Bp:d7", "Bp:e7", "Bp:f7", "Bp:g7", "Bp:h7"};
		Piece piece;

		pieces.clear();
		
		for(String p : white_pieces) {
			piece = Piece.initFromString(p);
			pieces.put(piece.getPosition(), piece);
		}
		
		for(String p : black_pieces) {
			piece = Piece.initFromString(p);
			pieces.put(piece.getPosition(), piece);
		}
		
		return this;
	}
	
	public void movePiece(Move move, boolean to_init) {
		Piece piece = move.getPiece();
		Position destination;
		Position current_pos = piece.getPosition();
		
		if (to_init) {
			destination = move.getInitPosition();
		} else {
			destination = move.getDestination();
		}
		
		if (pieces.containsKey(current_pos)) {
			pieces.remove(current_pos);
		}
		
		piece.moveTo(destination);
		pieces.put(destination, piece);
	}
	
	public void movePiece(Move move) {
		movePiece(move, false);
	}
	
	public void revertMove(Move move) {
		movePiece(move, true);
	}
	
	public void addPiece(Piece piece) {
		pieces.put(piece.getPosition(), piece);
	}
	
	public Piece getPieceAt(Position position) {
		return pieces.get(position);
	}
	
	public boolean hasPieceAt(Position position) {
		return pieces.containsKey(position);
	}
	
	public MoveSet allLegalMoves(Color color) {
		MoveSet moves = new MoveSet();
		
		for(Piece p : pieces.values()) {
			if (p.getColor() == color) {
				moves.addAll(p.legalMoves());
			}
		}
		
		return moves;
	}
	
	public MoveSet allAvailableMoves(Color color) {
		MoveSet moves = new MoveSet();
		for(Piece p : pieces.values()) {
			if (p.getColor() == color) {
				moves.addAll(p.availableMoves());
			}
		}
		
		return moves;
	}
	
	public boolean isCheck(Move move) {
		boolean is_check = false;
		
		Position dest = move.getDestination();
		Piece piece = move.getPiece();
		Piece captured = null;
		
		if (hasPieceAt(dest)) {
			captured = getPieceAt(dest);
		}
		movePiece(move);
		
		is_check = isCheck(piece.getColor());
		
		revertMove(move);
		
		if (captured != null) {
			pieces.put(captured.getPosition(), captured);
		}
		
		return is_check;
	}
	
	public boolean isCheck(Color color) {
		boolean is_check = false;
		Color other_color = Color.other(color);
		
		for(Move m : allAvailableMoves(other_color).get()) {
			if (isKing(color, m.getDestination())) {
				is_check = true;
				break;
			}
		}
		
		return is_check;
	}
	
	private boolean isKing(Color color, Position pos) {
		Piece piece_at_pos = pieces.get(pos);
		
		return (piece_at_pos != null && (piece_at_pos instanceof King) && piece_at_pos.getColor() == color);
	}
	
	private void hilightTile(Position pos) {
		PApplet p = game.getPapplet();
		int col = pos.getCol();
		int row = pos.getRow();
		int tile_size = game.getTileSize();
		
		p.noFill();
		p.stroke(220, 0, 0);
		p.strokeWeight(2);
		p.rectMode(PApplet.CORNER);
		
		p.rect(game.getX(col), game.getY(row), tile_size, tile_size);
	}
	
	private void drawTile(int col, int row) {
		PApplet p = game.getPapplet();
		
		if (col % 2 != row % 2) {
			p.fill(120);
		} else {
			p.fill(180);
		}
		
		p.stroke(220);
		p.strokeWeight(2);
		p.rectMode(PApplet.CORNER);
		
		p.rect(game.getX(col), game.getY(row), game.getTileSize(), game.getTileSize());
	}
	
	public void draw(Game game) {
		this.game = game;
		Piece selected_piece = game.getSelectedPiece();
		
		for(int col=1; col<=8; col++) {
			for(int row=1; row<=8; row++) {
				drawTile(col, row);
			}
		}
		
		if (selected_piece != null) {
			hilightTile(selected_piece.getPosition());
			for(Move m : selected_piece.legalMoves().get()) {
				hilightTile(m.getDestination());
			}
		}
		
		for(Piece piece : pieces.values()) {
			piece.draw(game);
		}
	}
}
