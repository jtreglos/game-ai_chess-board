package chess2;

public class Bishop extends Piece {
	public Bishop(Position position, Color color) {
		super(position, color);
	}

	@Override
	MoveSet availableMoves() {
		return movesForDiags();
	}

	@Override
	String toTypeString() {
		return "♝";
	}
	
	@Override
	String toChar() {
		return "♝";
	}
}
