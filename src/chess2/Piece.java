package chess2;

import java.util.*;
import java.util.regex.*;
import processing.core.*;

public abstract class Piece {
	protected Game game;
	protected Board board;
	protected Position position;
	protected Color color;
	
	public Piece(Position position, Color color) {
		this.board = Board.getBoard();
		this.position = new Position(position);
		this.color = color;
	}
	
	public static Piece initFromString(String init_string) {
		Pattern init_pattern = Pattern.compile("^([WB])([KQBNRp])$");
		Matcher m = init_pattern.matcher(init_string);
		Position pos;
		
		if (m.matches()) {
			pos = null;
		} else {
			init_pattern = Pattern.compile("^([WB])([KQBNRp]):([a-h][1-8])$");
			m = init_pattern.matcher(init_string);
			if (!m.matches()) {
				return null;
			}
			pos = Position.parse(m.group(3));
		}
		
		Color color = (m.group(1).equals("B") ? Color.BLACK : Color.WHITE);
		
		
		switch (m.group(2)) {
			case "K":
				return new King(pos, color);
			case "Q":
				return new Queen(pos, color);
			case "B":
				return new Bishop(pos, color);
			case "N":
				return new Knight(pos, color);
			case "R":
				return new Rook(pos, color);
			case "p":
				return new Pawn(pos, color);
			default:
				return null;
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public Position getPosition() {
		return position;
	}
	
	protected MoveSet movesForRow() {
		int col = position.getCol();
		int row = position.getRow();
		MoveSet moves = new MoveSet();
		
		for (int c=col-1; c>=1; c--) {
			Position pos = new Position(c, row);
			
			Piece p = board.getPieceAt(pos);
			if (p == null) {
				moves.add(new Move(this, pos));
			} else {
				if (p.getColor() != color) {
					moves.add(new Move(this, pos));
				}
				break;
			}
		}
		
		for (int c=col+1; c<=8; c++) {
			Position pos = new Position(c, row);
			
			Piece p = board.getPieceAt(pos);
			if (p == null) {
				moves.add(new Move(this, pos));
			} else {
				if (p.getColor() != color) {
					moves.add(new Move(this, pos));
				}
				break;
			}
		}
		
		return moves;
	}
	
	protected MoveSet movesForCol() {
		int col = position.getCol();
		int row = position.getRow();
		MoveSet moves = new MoveSet();
		
		for (int r=row-1; r>=1; r--) {
			Position pos = new Position(col, r);
			
			Piece p = board.getPieceAt(pos);
			if (p == null) {
				moves.add(new Move(this, pos));
			} else {
				if (p.getColor() != color) {
					moves.add(new Move(this, pos));
				}
				break;
			}
		}
		
		for (int r=row+1; r<=8; r++) {
			Position pos = new Position(col, r);
			
			Piece p = board.getPieceAt(pos);
			if (p == null) {
				moves.add(new Move(this, pos));
			} else {
				if (p.getColor() != color) {
					moves.add(new Move(this, pos));
				}
				break;
			}
		}
		
		return moves;
	}
	
	protected MoveSet movesForDiags() {
		int col = position.getCol();
		int row = position.getRow();
		MoveSet moves = new MoveSet();

		for (int c=col-1; c>=1; c--) {
			int delta = col - c;
			int r = row + delta;
			Position pos = new Position(c, r);
			
			Piece p = board.getPieceAt(pos);
			if (r >= 1 && r <= 8) {
				if (p == null) {
					moves.add(new Move(this, pos));
				} else {
					if (p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
					break;
				}
			} else {
				break;
			}
		}
			
		for (int c=col-1; c>=1; c--) {
			int delta = col - c;
			int r = row - delta;
			Position pos = new Position(c, r);
			
			Piece p = board.getPieceAt(pos);
			if (r >= 1 && r <= 8) {
				if (p == null) {
					moves.add(new Move(this, pos));
				} else {
					if (p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
					break;
				}
			} else {
				break;
			}
		}
		
		for (int c=col+1; c<=8; c++) {
			int delta = col - c;
			int r = row + delta;
			Position pos = new Position(c, r);
			
			Piece p = board.getPieceAt(pos);
			if (r >= 1 && r <= 8) {
				if (p == null) {
					moves.add(new Move(this, pos));
				} else {
					if (p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
					break;
				}
			} else {
				break;
			}
		}
		
		for (int c=col+1; c<=8; c++) {
			int delta = col - c;
			int r = row - delta;
			Position pos = new Position(c, r);
			
			Piece p = board.getPieceAt(pos);
			if (r >= 1 && r <= 8) {
				if (p == null) {
					moves.add(new Move(this, pos));
				} else {
					if (p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
					break;
				}
			} else {
				break;
			}
		}
		
		return moves;
	}
	
	public void draw(Game game) {
		this.game = game;
		PApplet p = game.getPapplet();
		int tile_size = game.getTileSize();
		Piece selected_piece = game.getSelectedPiece();
		
		int font_size = (int)(tile_size * 0.8);
		PFont f = p.createFont("HelveticaNeue-Bold", font_size, true);
		int x = game.getX(position.getCol());
		int y = game.getY(position.getRow());

		if (selected_piece != null && this == selected_piece) {
			if (color == Color.BLACK) {
				p.fill(80, 0, 0);
			} else {
				p.fill(220, 0, 0);
			}
		} else {
			if (color == Color.BLACK) {
				p.fill(80);
			} else {
				p.fill(220);
			}
		}
		
		p.textFont(f);
		p.textAlign(PApplet.CENTER);
		p.text(toChar(), x+(tile_size/2), y+(font_size/3)+(tile_size/2));
	}
	
	public void moveTo(Position position) {
		if (position == null) {
			this.position = null;
		} else {
			this.position.setPosition(position);
		}
	}
	
	@Override
	public String toString() {
		return toTypeString() + position;
	}
	
	public String toString(Position position) {
		return toTypeString() + position;
	}
	
	@Override
	public int hashCode() {
		String s = toTypeString();
		final int prime = 33;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Piece))
			return false;
		Piece other = (Piece) obj;
		String s = toTypeString();
		String so = other.toTypeString();
		if (color != other.color)
			return false;
		if (s != so)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	public MoveSet legalMoves() {
		List<Move> available_moves = availableMoves().get();
		MoveSet moves = new MoveSet();
		
		for(Move m : available_moves) {
			if (!board.isCheck(m)) {
				moves.add(m);
			}
		}
		
		return moves;
	}

	abstract MoveSet availableMoves();
	abstract String toTypeString();
	abstract String toChar();
}
