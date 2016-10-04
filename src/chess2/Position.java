package chess2;

import java.util.regex.*;

public class Position {
	private int col, row;
	private static Pattern init_pattern = Pattern.compile("^([a-h])([1-8])$");
	
	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public Position(Position position) {
		col = position.col;
		row = position.row;
	}
	
	public void setPosition(Position position) {
		col = position.col;
		row = position.row;
	}
	
	public void setPosition(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public static Position toPos(int col, int row) {
		return new Position(col, row);
	}
	
	public static Position parse(String string) {
		int col, row;
		
		Matcher m = init_pattern.matcher(string);
		if (m.matches()) {
			col = (int)m.group(1).charAt(0) - (int)'a' + 1;
			row = Integer.parseInt(m.group(2));
			
			return new Position(col, row);
		} else {
			return null;
		}
		
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public static String toColString(int col) {
		char c = (char)((int)'a' + (col-1));
		return Character.toString(c);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return toColString(col) + row;
	}
}
