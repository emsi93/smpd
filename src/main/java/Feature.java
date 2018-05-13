public class Feature {


    private int id;
    private double value;

    public Feature (){};

    public Feature(int id, Double value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
