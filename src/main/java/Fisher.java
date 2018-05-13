import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.ArrayList;
import java.util.List;

public class Fisher {

    private List<Leaf> leafAcer;
    private List<Leaf> leafQuercus;
    private int numberOfFeatures;
    private StandardDeviation standardDeviation;
    private List<Double> meanAcer;
    private List<Double> meanQuercus;
    private List<Double> standardDeviationAcer;
    private List<Double> standardDeviationQuercus;
    private List<FisherCoefficient> fisherCoefficients;


    public Fisher(List<Leaf> leafAcer, List<Leaf> leafQuercus) {
        this.leafAcer = leafAcer;
        this.leafQuercus = leafQuercus;
        numberOfFeatures = leafAcer.get(0).getFeatures().size();
        initFields();
    }

    public FisherCoefficient getOneMaxFisherCoefficient() {

        for (int i = 0; i < numberOfFeatures; i++) {
            countMeanAndStandardDeviation(leafAcer, i);
            countMeanAndStandardDeviation(leafQuercus, i);
        }

        determinateFisherCoefficient();
        fisherCoefficients.sort(FisherCoefficient::compareTo);
        return fisherCoefficients.get(0);

    }

    private void initFields(){
        standardDeviation = new StandardDeviation();
        meanAcer = new ArrayList<>();
        meanQuercus = new ArrayList<>();
        standardDeviationAcer = new ArrayList<>();
        standardDeviationQuercus = new ArrayList<>();
        fisherCoefficients = new ArrayList<>();
    }

    private void countMeanAndStandardDeviation(List<Leaf> leafs, int i){
        double sumFeatures = 0.0;
        double mean = 0.0;
        double[] features =  new double[leafs.size()];
        for (int j = 0; j < leafs.size(); j++) {
            sumFeatures = sumFeatures + leafs.get(j).getFeatures().get(i);
            features[j] = leafs.get(j).getFeatures().get(i);
        }
        mean = sumFeatures / leafs.size();
        if(leafs.get(0).getName().contains("Acer")){
            meanAcer.add(mean);
            standardDeviationAcer.add(standardDeviation.evaluate(features));
        }else
        {
            meanQuercus.add(mean);
            standardDeviationQuercus.add(standardDeviation.evaluate(features));
        }
    }

    private void determinateFisherCoefficient(){
        double nominator, denominator;
        for (int i = 0; i < numberOfFeatures; i++) {
            nominator = Math.abs(meanAcer.get(i) - meanQuercus.get(i));
            denominator = standardDeviationAcer.get(i) + standardDeviationQuercus.get(i);

            fisherCoefficients.add(new FisherCoefficient( nominator / denominator, i + 1));
        }
    }
}