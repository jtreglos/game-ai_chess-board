package chess2;

public class Rook extends Piece {
	public Rook(Position position, Color color) {
		super(position, color);
	}

	@Override
	MoveSet availableMoves() {
		MoveSet moves = movesForRow();
		moves.addAll(movesForCol());
		
		return moves;
	}

	@Override
	String toTypeString() {
		return "♜";
	}

	@Override
	String toChar() {
		return "♜";
	}
}
