package chess2;

public class Knight extends Piece {
	public Knight(Position position, Color color) {
		super(position, color);
	}
	
	private void knightMoveAdd(int col, int row, MoveSet moves) {
		if (col >= 1 && col <= 8 && row >= 1 && row <= 8) {
			Position pos = new Position(col, row);
			Piece p = board.getPieceAt(pos);
			if (p == null || p.getColor() != color) {
				moves.add(new Move(this, pos));
			}
		}
	}

	@Override
	MoveSet availableMoves() {
		int col = position.getCol();
		int row = position.getRow();
		MoveSet moves = new MoveSet();
		
		knightMoveAdd(col + 1, row + 2, moves);
		knightMoveAdd(col - 1, row + 2, moves);
		knightMoveAdd(col + 1, row - 2, moves);
		knightMoveAdd(col - 1, row - 2, moves);
		knightMoveAdd(col + 2, row + 1, moves);
		knightMoveAdd(col - 2, row + 1, moves);
		knightMoveAdd(col + 2, row - 1, moves);
		knightMoveAdd(col - 2, row - 1, moves);
		
		return moves;
	}

	@Override
	String toTypeString() {
		return "♞";
	}
	
	@Override
	String toChar() {
		return "♞";
	}
}
