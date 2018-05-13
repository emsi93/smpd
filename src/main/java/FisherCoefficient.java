import java.util.Arrays;

public class FisherCoefficient implements Comparable<FisherCoefficient>{

    private Integer[] idFeatures;
    private Double value;

    public FisherCoefficient(double value, Integer... idFeature) {
        this.idFeatures = idFeature;
        this.value = value;
    }

    public Double getValue() {
        return value;
    }


    @Override
    public int compareTo(FisherCoefficient o) {
        return o.getValue().compareTo(getValue());
    }

    @Override
    public String toString() {
        return "FisherCoefficient{" +
                "idFeatures=" + Arrays.toString(idFeatures) +
                ", value=" + value +
                '}';
    }
}
