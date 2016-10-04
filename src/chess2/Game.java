package chess2;

import java.util.*;
import processing.core.*;

public class Game {
	private PApplet p;
	private Board board;
	private MoveSet moves;
	private Color turn;
	private CaptureSet cbw;
	private CaptureSet cbb;
	private Piece selected_piece;
	private MoveSet legal_moves;
	private int tile_size = 0; 
	public static final int MARGIN = 60;
	
	
	public Game(PApplet p) {
		board = Board.getBoard().init();
		moves = new MoveSet();
		turn = Color.WHITE;
		cbw = new CaptureSet();
		cbb = new CaptureSet();
		selected_piece = null;
		legal_moves = new MoveSet();
		this.p = p;
		if (p != null) {
			tile_size = (p.height - 3 * MARGIN) / 8;
		}
	}
	
	public PApplet getPapplet() {
		return p;
	}
	
	private Position getPosition() {
		return getPosition(p.mouseX, p.mouseY);
	}
	
	private Position getPosition(int x, int y) {
		int col = ((x - MARGIN) / tile_size) + 1;
		int row = (int)Math.ceil((p.height - y - 2 * MARGIN) / (tile_size * 1.0)) + 1;
		return new Position(col, row);
	}
	
	private boolean mouseInGrid() {
		int grid_size = 8 * tile_size;
		
		return p.mouseX >= MARGIN && p.mouseX <= MARGIN + grid_size && p.mouseY >= 2*MARGIN && p.mouseY <= 2*MARGIN + grid_size;
	}
	
	public void undoLastMove() {
		List<Move> moves_list = moves.get();
		if (!moves_list.isEmpty()) {
			Move move = moves_list.remove(moves_list.size()-1);
			board.revertMove(move);
			changeTurn();
			if (move.getCapture()) {
				Piece captured = move.getCaptured();
				unCapturePiece(captured);
				board.addPiece(captured);
			}
		}
	}
	
	public void mouseClicked() {
		if (mouseInGrid()) {
			selectPosition(getPosition());
		} else if (mouseInUndoButton()) { // Undo last move
			undoLastMove();
		}
	}
	
	public int getX(int col) {
		return MARGIN + (col - 1) * tile_size;
	}
	
	public int getY(int row) {
		return p.height - 2 * MARGIN - (row - 1) * tile_size;
	}
	
	public int getTileSize() {
		return tile_size;
	}
	
	public void drawUndoButton() {
		p.fill(80);
		p.noStroke();
		p.rectMode(PApplet.CORNER);
		p.rect(p.width - MARGIN, 0, MARGIN, p.height);
	}
	
	private boolean mouseInUndoButton() {
		return p.mouseX > p.width - MARGIN;
	}
	
	public void draw() {
		board.draw(this);
		moves.draw(this);
		int offset = cbw.draw(this, 2*MARGIN);
		cbb.draw(this, offset + MARGIN);
		drawUndoButton();
	}
	
	public void unCapturePiece(Piece piece) {
		if (piece.getColor() == Color.BLACK) {
			cbw.remove(piece);
		} else {
			cbb.remove(piece);
		}
	}
	
	public void capturePiece(Piece piece) {
		if (piece.getColor() == Color.BLACK) {
			cbw.add(piece);
		} else {
			cbb.add(piece);
		}
	}
	
	public void changeTurn() {
		selectPiece(null);
		turn = Color.other(turn);
	}
	
	private void applyMove(Move move) {
		Piece piece_in_place = board.getPieceAt(move.getDestination());
		
		if (piece_in_place != null && piece_in_place.getColor() == turn) { // Select another piece
			selectPiece(piece_in_place);
		} else if (legal_moves.contains(move)) { // Is a legal move
			if (selected_piece != null && piece_in_place != null && piece_in_place.getColor() != selected_piece.getColor()) {
				capturePiece(piece_in_place);
				move.setCapture();
				move.setCaptured(piece_in_place);
			}
			board.movePiece(move);
			Color color = move.getPiece().getColor();
			Color other_color = Color.other(color);
			
			if (board.isCheck(other_color)) {
				boolean last_chance = false;
				MoveSet last_chance_moves = board.allAvailableMoves(other_color);
				
				for(Move m : last_chance_moves.get()) {
					if (!board.isCheck(m)) {
						last_chance = true;
						break;
					}
				}
				
				if (last_chance) {
					move.setCheck();
				} else {
					move.setCheckmate();
					// game.setCheckmate();
				}
			}
			
			moves.add(move);
			changeTurn();
		} else { // Move not allowed : cancel selection
			selectPiece(null);
		}
	}
	
	private void selectPiece(Piece piece) {
		selected_piece = piece;
		legal_moves.clear();
		if (piece != null) {
			legal_moves.addAll(piece.legalMoves());
		}
	}
	
	public void selectPosition(Position position) {
		Move move;
		
		if (selected_piece == null) {
			Piece p = board.getPieceAt(position);
			if (p != null && p.getColor() == turn) {
				selectPiece(p);
			}
		} else {
			move = new Move(selected_piece, position);
			applyMove(move);
		}
		
		
	}
	
	public Piece getSelectedPiece() {
		return selected_piece;
	}
	
	public Board getBoard() {
		return board;
	}
}
