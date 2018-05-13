import java.util.ArrayList;
import java.util.List;

public class Leaf {

    private String name;
    private List<Double> features;


    public Leaf(String name){
        this.name = name;
        features = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getFeatures() {
        return features;
    }

    public void setFeatures(List<Double> features) {
        this.features = features;
    }

    public void addFeature(Double feature){
        features.add(feature);
    }
}
