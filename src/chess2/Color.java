package chess2;

public enum Color {
	WHITE("W"), BLACK("B");
	
	private String letter;
	
	Color(String letter) {
		this.letter = letter;
	}
	
	@Override
	public String toString() {
		return letter;
	}
	
	public static Color other(Color c) {
		if (c == BLACK) {
			return WHITE;
		} else {
			return BLACK;
		}
	}
}
