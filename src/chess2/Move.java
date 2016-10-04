package chess2;

public class Move {
	private Piece piece;
	private Position destination;
	private Position init_position;
	private Piece captured=null;
	private boolean capture=false;
	private boolean check=false;
	private boolean checkmate=false;
	
	public Move(Piece piece, Position destination) {
		this.piece = piece;
		this.init_position = new Position(piece.getPosition());
		this.destination = destination;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Position getDestination() {
		return destination;
	}
	
	public Position getInitPosition() {
		return init_position;
	}
	
	public boolean isLegal() {
		return piece.legalMoves().contains(new Move(piece, destination));
	}
	
	public void setCaptured(Piece piece) {
		captured = piece;
	}
	
	public Piece getCaptured() {
		return captured;
	}
	
	public void setCapture() {
		capture = true;
	}
	
	public void setCheck() {
		check = true;
	}
	
	public boolean setCheckmate() {
		return checkmate;
	}
	
	public boolean getCapture() {
		return capture;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public void getCheckmate() {
		checkmate = true;
	}
	
	public String toString() {
		String sep = "-";
		String ret = "";
		if (capture) sep = "x";
		
		ret = piece.toString(init_position) + sep + destination;
		
		if (checkmate) {
			ret += "#";
		} else if (check) {
			ret += "+";
		}
		
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 32;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Move))
			return false;
		Move other = (Move) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		return true;
	}
}
