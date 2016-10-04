package chess2;

public class Queen extends Piece {
	public Queen(Position position, Color color) {
		super(position, color);
	}

	@Override
	MoveSet availableMoves() {
		MoveSet moves = movesForRow();
		moves.addAll(movesForCol());
		moves.addAll(movesForDiags());
		
		return moves;
	}

	@Override
	String toTypeString() {
		return "♛";
	}

	@Override
	String toChar() {
		return "♛";
	}
}
