import java.util.ArrayList;
import java.util.List;

public class Leaf {

    private String name;
    private List<Feature> features;


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

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }

    @Override
    public String toString() {
        return "Leaf{" +
                "name='" + name + '\'' +
                ", features=" + features +
                '}';
    }
}
