package chess2;

public class Pawn extends Piece {
	public Pawn(Position position, Color color) {
		super(position, color);
	}

	@Override
	MoveSet availableMoves() {
		Position pos;
		int col = position.getCol();
		int row = position.getRow();
		MoveSet moves = new MoveSet();
		
		if (color == Color.WHITE) {
			if (row != 8) {
				pos = new Position(col, row+1);
				if (board.getPieceAt(pos) == null) {
					moves.add(new Move(this, pos));
					
					pos = new Position(col, row+2);
					if (row == 2 && board.getPieceAt(pos) == null) {
						moves.add(new Move(this, pos));
					}
				}
				if (col > 1) {
					pos = new Position(col-1, row+1);
					Piece p = board.getPieceAt(pos);
					if (p != null && p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
				}
				if (col < 8) {
					pos = new Position(col+1, row+1);
					Piece p = board.getPieceAt(pos);
					if (p != null && p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
				}
			}
		} else if (color == Color.BLACK) {
			if (row != 1) {
				pos = new Position(col, row-1);
				if (board.getPieceAt(pos) == null) {
					moves.add(new Move(this, pos));
					
					pos = new Position(col, row-2);
					if (row == 7 && board.getPieceAt(pos) == null) {
						moves.add(new Move(this, pos));
					}
				}
				if (col > 1) {
					pos = new Position(col-1, row-1);
					Piece p = board.getPieceAt(pos);
					if (p != null && p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
				}
				if (col < 8) {
					pos = new Position(col+1, row-1);
					Piece p = board.getPieceAt(pos);
					if (p != null && p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
				}
			}
		}
		
		return moves;
	}

	@Override
	String toTypeString() {
		return "";
	}
	
	@Override
	String toChar() {
		return "♟";
	}
}
