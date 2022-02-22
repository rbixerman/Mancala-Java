package mancala.api.models;

public class PitDTO {
	int index;

	public int getIndex() {
		return index;
	}

	int nrOfStones;

	public int getNrOfStones() {
		return nrOfStones;
	}

	public PitDTO(int index, int nrOfStones) {
		this.index = index;
		this.nrOfStones = nrOfStones;
	}
}