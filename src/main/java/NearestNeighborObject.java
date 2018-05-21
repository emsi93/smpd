public class NearestNeighborObject implements Comparable<NearestNeighborObject>{

    private Leaf trainingObject;
    private Double length;


    public NearestNeighborObject(Leaf trainingObject, Double length) {
        this.trainingObject = trainingObject;
        this.length = length;
    }

    @Override
    public int compareTo(NearestNeighborObject o) {
        return getLength().compareTo(o.getLength());
    }

    public Leaf getTrainingObject() {
        return trainingObject;
    }

    public Double getLength() {
        return length;
    }

}
