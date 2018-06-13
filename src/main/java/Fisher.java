import Jama.Matrix;
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


    public Fisher(List<Leaf> leafAcer, List<Leaf> leafQuercus) {
        this.leafAcer = leafAcer;
        this.leafQuercus = leafQuercus;
        numberOfFeatures = leafAcer.get(0).getFeatures().size();

        initFields();

        for (int i = 0; i < numberOfFeatures; i++) {
            countMeanAndStandardDeviation(leafAcer, i);
            countMeanAndStandardDeviation(leafQuercus, i);
        }
    }

    public FisherCoefficient getMaxFisherCoefficient(int howManyFeatures) {
        if (howManyFeatures > 1)
            return getMaxFisherCoefficientForKfeatures(howManyFeatures);
        else
            return getMaxFisherCoefficientForOneFeature();
    }

    private FisherCoefficient getMaxFisherCoefficientForOneFeature() {

        List<FisherCoefficient> fisherCoefficients = determinateFisherCoefficient();
        fisherCoefficients.sort(FisherCoefficient::compareTo);
        return fisherCoefficients.get(0);

    }

    public FisherCoefficient getMaxFisherCoefficientForKfeatures(FisherCoefficient fisherCoefficient, int howManyFeatures) {

        List<FisherCoefficient> fishers = null;
        FisherCoefficient temp = fisherCoefficient;
        for (int i = 1; i < howManyFeatures; i++) {

            if (fishers != null)
                temp = fishers.get(0);
            fishers = new ArrayList<>();
            for (int j = 0; j < numberOfFeatures; j++) {
                if (temp.getIdFeatures().contains(j))
                    continue;
                temp.getIdFeatures().add(j);
                Integer[][] combinationFeatures = new Integer[1][temp.getIdFeatures().size()];
                for (int k = 0; k < temp.getIdFeatures().size(); k++)
                    combinationFeatures[0][k] = temp.getIdFeatures().get(k);

                fishers.addAll(countFisherForCombination(combinationFeatures, temp.getIdFeatures().size()));
                temp.getIdFeatures().remove(temp.getIdFeatures().size() - 1);
            }

            fishers.sort(FisherCoefficient::compareTo);
        }

        return fishers.get(0);
    }

    private List<FisherCoefficient> countFisherForCombination(Integer[][] combinationFeatures, int howManyFeatures) {
        double[][] mAcer, mQuercus, xAcer, xQuercus, uAcer, uQuercus;
        List<FisherCoefficient> fisherCoefficients = new ArrayList<>();
        for (int i = 0; i < combinationFeatures.length; i++) {
            mAcer = new double[howManyFeatures][1];
            mQuercus = new double[howManyFeatures][1];
            xAcer = new double[howManyFeatures][leafAcer.size()];
            xQuercus = new double[howManyFeatures][leafQuercus.size()];
            uAcer = new double[howManyFeatures][leafAcer.size()];
            uQuercus = new double[howManyFeatures][leafQuercus.size()];

            for (int j = 0; j < howManyFeatures; j++) {

                mAcer[j][0] = meanAcer.get(combinationFeatures[i][j]);
                mQuercus[j][0] = meanQuercus.get(combinationFeatures[i][j]);

                for (int k = 0; k < leafAcer.size(); k++) {
                    uAcer[j][k] = meanAcer.get(combinationFeatures[i][j]);
                    xAcer[j][k] = leafAcer.get(k).getFeatures().get(combinationFeatures[i][j]).getValue();
                }

                for (int l = 0; l < leafQuercus.size(); l++) {
                    uQuercus[j][l] = meanQuercus.get(combinationFeatures[i][j]);
                    xQuercus[j][l] = leafQuercus.get(l).getFeatures().get(combinationFeatures[i][j]).getValue();
                }
            }

            Matrix matrixMA = new Matrix(mAcer);
            Matrix matrixMB = new Matrix(mQuercus);
            Matrix nominator = matrixMA.minus(matrixMB);

            Matrix xA = new Matrix(uAcer);
            Matrix xB = new Matrix(xQuercus);
            Matrix uA = new Matrix(uAcer);
            Matrix uB = new Matrix(uQuercus);

            fisherCoefficients.add(determinateFisherCoeficcient(xA, uA, xB, uB, nominator, howManyFeatures, combinationFeatures[i]));
        }
        return fisherCoefficients;
    }

    private FisherCoefficient getMaxFisherCoefficientForKfeatures(int howManyFeatures) {
        Integer[][] combinationFeatures = Combination.generate(64, howManyFeatures);
        List<FisherCoefficient> fisherCoefficients = countFisherForCombination(combinationFeatures, howManyFeatures);
        fisherCoefficients.sort(FisherCoefficient::compareTo);
        return fisherCoefficients.get(0);
    }

    private void initFields() {
        standardDeviation = new StandardDeviation();
        meanAcer = new ArrayList<>();
        meanQuercus = new ArrayList<>();
        standardDeviationAcer = new ArrayList<>();
        standardDeviationQuercus = new ArrayList<>();
    }

    private double lengthOfVector(Matrix matrix, int howManyFeatures) {
        double sum = 0.0;
        for (int n = 0; n < howManyFeatures; n++) {
            sum = Math.pow(matrix.get(n, 0), 2);
        }
        return Math.sqrt(sum);
    }

    private Matrix createCovarianceMatrix(Matrix x, Matrix u, int n) {
        Matrix temp1 = x.minus(u);
        Matrix temp2 = temp1.transpose();
        Matrix temp3 = temp1.times(temp2);
        return temp3.times(1.0 / n);
    }

    private void countMeanAndStandardDeviation(List<Leaf> leafs, int i) {
        double sumFeatures = 0.0;
        double mean;
        double[] features = new double[leafs.size()];
        for (int j = 0; j < leafs.size(); j++) {
            sumFeatures = sumFeatures + leafs.get(j).getFeatures().get(i).getValue();
            features[j] = leafs.get(j).getFeatures().get(i).getValue();
        }
        mean = sumFeatures / leafs.size();
        if (leafs.get(0).getName().contains("Acer")) {
            meanAcer.add(mean);
            standardDeviationAcer.add(standardDeviation.evaluate(features));
        } else {
            meanQuercus.add(mean);
            standardDeviationQuercus.add(standardDeviation.evaluate(features));
        }
    }

    private List<FisherCoefficient> determinateFisherCoefficient() {
        double nominator, denominator;
        List<FisherCoefficient> fisherCoefficients = new ArrayList<>();
        for (int i = 0; i < numberOfFeatures; i++) {
            nominator = Math.abs(meanAcer.get(i) - meanQuercus.get(i));
            denominator = standardDeviationAcer.get(i) + standardDeviationQuercus.get(i);

            fisherCoefficients.add(new FisherCoefficient(nominator / denominator, i));
        }
        return fisherCoefficients;
    }

    private FisherCoefficient determinateFisherCoeficcient(Matrix xA, Matrix uA, Matrix xB, Matrix uB, Matrix nominator, int howManyFeatures, Integer[] combinationFeatures) {
        return new FisherCoefficient(countFisherValue(xA, uA, xB, uB, nominator, howManyFeatures), combinationFeatures);
    }

//    private FisherCoefficient determinateFisherCoeficcient(Matrix xA, Matrix uA, Matrix xB, Matrix uB, Matrix nominator, int howManyFeatures, List<Integer> idFeatures) {
//        return new FisherCoefficient(countFisherValue(xA, uA, xB, uB, nominator, howManyFeatures), idFeatures);
//    }

    private double countFisherValue(Matrix xA, Matrix uA, Matrix xB, Matrix uB, Matrix nominator, int howManyFeatures) {
        Matrix sA = createCovarianceMatrix(xA, uA, leafAcer.size());
        Matrix sB = createCovarianceMatrix(xB, uB, leafQuercus.size());
        return lengthOfVector(nominator, howManyFeatures) / (sA.det() + sB.det());
    }
}