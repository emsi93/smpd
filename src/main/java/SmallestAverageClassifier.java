import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmallestAverageClassifier {

    private List<Leaf> testList;
    private List<Leaf> trainingList;

    public SmallestAverageClassifier(List<Leaf> testList, List<Leaf> trainingList) {
        this.testList = testList;
        this.trainingList = trainingList;
    }

    public int assignToClass(int n) {

        if (n == 1)
            return nm();
        else
            return knm(n);

    }

    private int knm(int n) {

        List<Leaf> leafList = testList;
        leafList.addAll(trainingList);
        Collections.shuffle(leafList);

        List<Leaf> pointsK = new ArrayList<>();
        for (int i = 0; i < n; i++)
            pointsK.add(leafList.remove(0));

        List<Leaf> acerList = new ArrayList<>();
        List<Leaf> quercusList = new ArrayList<>();

        for (int i = 0; i < leafList.size(); i++) {
            if (leafList.get(i).getName().equals("Acer"))
                acerList.add(leafList.get(i));
            else
                quercusList.add(leafList.get(i));
        }
        leafList = null;
        leafList = acerList;
        leafList.addAll(quercusList);


        ArrayList<ArrayList<Double>> lenghts = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i < pointsK.size(); i++) {
            ArrayList<Double> tempLengths = new ArrayList<Double>();
            for (int j = 0; j < leafList.size(); j++) {
                tempLengths.add(getLength(pointsK.get(i), leafList.get(j)));
            }
            lenghts.add(tempLengths);
        }

        List<Integer> indexs = new ArrayList<>();
        for (int i = 0; i < leafList.size(); i++) {
            double min = lenghts.get(0).get(i);
            int indexJ= 0;
            for (int j = 1; j < pointsK.size(); j++) {
                if (lenghts.get(j).get(i) < min){
                    min = lenghts.get(j).get(i);
                    indexJ = j;
                }
            }
            indexs.add(indexJ);
        }




        return 1;


    }

    private int nm() {
        int dopasowanie = 0;
        List<Leaf> acerList = getLeafsByName("Acer");
        List<Leaf> quercusList = getLeafsByName("Quercus");
        trainingList = null;

        Leaf meanAcer = new Leaf("Acer");
        Leaf meanQuercus = new Leaf("Quercus");

        meanAcer.setFeatures(getFeature(acerList));
        meanQuercus.setFeatures(getFeature(quercusList));

        for (int i = 0; i < testList.size(); i++) {

            double lengthBetweenAcer = getLength(testList.get(i), meanAcer);
            double lengthBetweenQuercus = getLength(testList.get(i), meanQuercus);

            String className = getClassName(lengthBetweenAcer, lengthBetweenQuercus);

            if (testList.get(i).getName().equals(className))
                dopasowanie++;
        }

        return dopasowanie;
    }

    private String getClassName(double lengthBetweenAcer, double lengthBetweenQuercus) {
        if (lengthBetweenAcer < lengthBetweenQuercus)
            return "Acer";
        else
            return "Quercus";
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

    private List<Feature> getFeature(List<Leaf> leafList) {
        List<Feature> features = new ArrayList<>();
        for (int i = 0; i < leafList.get(0).getFeatures().size(); i++) {
            double sum = 0.0;
            double meanFeature = 0.0;
            for (int j = 0; j < leafList.size(); j++) {

                sum += leafList.get(j).getFeatures().get(i).getValue();
            }

            meanFeature = sum / leafList.get(0).getFeatures().size();
            features.add(new Feature(i, meanFeature));
        }

        return features;
    }

    private List<Leaf> getLeafsByName(String className) {
        List<Leaf> leafList = new ArrayList<>();
        for (int i = 0; i < trainingList.size(); i++) {
            if (trainingList.get(i).getName().equals(className))
                leafList.add(trainingList.get(i));
        }
        return leafList;
    }
}
