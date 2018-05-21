import java.util.ArrayList;
import java.util.List;

public class NearestNeighborClassifier {

    private List<Leaf> testList;
    private List<Leaf> trainingList;

    public NearestNeighborClassifier(List<Leaf> testList, List<Leaf> trainingList) {
        this.testList = testList;
        this.trainingList = trainingList;
    }

    private double getLength(Leaf a, Leaf b) {
        double sum = 0.0;

        for (int k = 0; k < a.getFeatures().size(); k++) {
            double pointA = a.getFeatures().get(k).getValue();
            double pointB = b.getFeatures().get(k).getValue();

            sum += Math.pow(pointA - pointB, 2);
        }
        return Math.sqrt(sum);
    }

    public int assignToClass(int n) {

        if (n == 1)
            return nn();
        else
            return knn(n);

    }

    private int knn(int n) {
        int dopasowanie = 0;
        for (int i = 0; i < testList.size(); i++) {
            List<NearestNeighborObject> objects = new ArrayList<>();
            for (int j = 0; j < trainingList.size(); j++) {
                objects.add(new NearestNeighborObject(trainingList.get(j), getLength(testList.get(i), trainingList.get(j))));
            }
            objects.sort(NearestNeighborObject::compareTo);
            int dopasowaneAcer = 0;
            int dopasowaneQuercus = 0;
            for (int k = 0; k < n; k++) {
                if (objects.get(k).getTrainingObject().getName().equals("Acer"))
                    dopasowaneAcer++;
                else
                    dopasowaneQuercus++;
            }

            String nameTrainingObject = null;
            if (dopasowaneAcer > dopasowaneQuercus)
                nameTrainingObject = "Acer";
            else
                nameTrainingObject = "Quercus";

            if(testList.get(i).getName().equals(nameTrainingObject))
                dopasowanie++;

        }
        return dopasowanie;
    }

    private int nn() {
        int dopasowanie = 0;
        for (int i = 0; i < testList.size(); i++) {

            double minLength = getLength(testList.get(i), trainingList.get(0));
            int index = 0;

            for (int j = 1; j < trainingList.size(); j++) {
                double length = 0.0;

                length = getLength(testList.get(i), trainingList.get(j));

                if (length < minLength) {
                    minLength = length;
                    index = j;
                }
            }
            if (testList.get(i).getName().equals(trainingList.get(index).getName())) {
                dopasowanie++;
            }
        }
        return dopasowanie;
    }
}
