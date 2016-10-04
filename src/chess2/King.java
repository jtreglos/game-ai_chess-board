package chess2;

public class King extends Piece {
	public King(Position position, Color color) {
		super(position, color);
	}

	@Override
	MoveSet availableMoves() {
		int col = position.getCol();
		int row = position.getRow();
		Position pos;
		MoveSet moves = new MoveSet();

		for (int c=col-1; c<=col+1; c++) {
			for (int r=row-1; r<=row+1; r++) {
				if (c >= 1 && c <= 8 && r >= 1 && r <= 8 && !(c == col && r == row)) {
					pos = new Position(c, r);
					Piece p = board.getPieceAt(pos);
					if (p == null || p.getColor() != color) {
						moves.add(new Move(this, pos));
					}
				}
			}
		}
		
		return moves;
	}

	@Override
	String toTypeString() {
		return "♚";
	}
	
	@Override
	String toChar() {
		return "♚";
	}
}
