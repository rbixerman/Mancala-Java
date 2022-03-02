package mancala.api.models;

public record PitDTO(int index, int nrOfStones) {

    public int getIndex() {
        return index;
    }

    public int getNrOfStones() {
        return nrOfStones;
    }

}
