import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.List;

public class Combination {

    public static Integer[][] generate(int numberOfFeatures, int k) {

        ICombinatoricsVector<Integer> vector = Factory.createVector(generateList(numberOfFeatures));
        Generator<Integer> generator = Factory.createSimpleCombinationGenerator(vector, k);

        List<ICombinatoricsVector<Integer>> perms = generator.generateAllObjects();
        Integer[][] features = new Integer[perms.size()][k];
        for (int i = 0; i < perms.size(); i++) {
            for (int j = 0; j < k; j++) {
                features[i][j] = perms.get(i).getValue(j);
            }
        }

        return features;
    }


    private static Integer[] generateList(int numberOfFeatures) {
        Integer[] array = new Integer[numberOfFeatures];
        for (int i = 0; i < numberOfFeatures; i++)
            array[i] = i;

        return array;
    }
}
