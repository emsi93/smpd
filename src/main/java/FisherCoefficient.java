import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FisherCoefficient implements Comparable<FisherCoefficient>{

    private List<Integer> idFeatures = new ArrayList<>();
    private Double value;

    public FisherCoefficient(double value, Integer... idFeature) {
        this.idFeatures.addAll(Arrays.asList(idFeature));
        this.value = value;
    }

    public FisherCoefficient(double value, List<Integer> idFeatures){
        this.idFeatures.addAll(idFeatures);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public List<Integer> getIdFeatures() {
        return idFeatures;
    }

    public void setIdFeatures(List<Integer> idFeatures) {
        this.idFeatures = idFeatures;
    }

    @Override
    public int compareTo(FisherCoefficient o) {
        return o.getValue().compareTo(getValue());
    }

    @Override
    public String toString() {
        return "FisherCoefficient{" +
                "idFeatures=" + idFeatures +
                ", value=" + Float.parseFloat(String.valueOf(value)) +
                '}';
    }
}
