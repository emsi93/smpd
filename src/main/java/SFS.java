import java.util.List;

public class SFS {


    private Fisher fisher;

    public SFS(List<Leaf> leafAcer, List<Leaf> leafQuercus) {

        fisher = new Fisher(leafAcer, leafQuercus);

    }

    public FisherCoefficient getMaxFisherCoefficient(int n) {
        FisherCoefficient fisherCoefficient = fisher.getMaxFisherCoefficient(1);
        if (n == 1)
            return fisherCoefficient;
        else
            return fisher.getMaxFisherCoefficientForKfeatures(fisherCoefficient, n);
    }
}
