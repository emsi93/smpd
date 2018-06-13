import java.util.Collections;
import java.util.List;

public class Bootstrap {

    public Bootstrap() {
    }


    public double assignToClass(String classifier, List<Leaf> testList, List<Leaf> trainingList, int k) {

        if (classifier.equals("nn")){
            NearestNeighborClassifier nearestNeighborClassifier = new NearestNeighborClassifier(testList, trainingList);
            return (1.0 * nearestNeighborClassifier.assignToClass(k)) / testList.size();
        } else{
            SmallestAverageClassifier smallestAverageClassifier = new SmallestAverageClassifier(testList, trainingList);
            return (1.0 * smallestAverageClassifier.assignToClass(k)) / testList.size();
        }

    }
}
