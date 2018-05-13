import java.util.List;

public class Main {

    public static void main(String[] argv) {
        String fileName = "Maple_Oak.txt";

        ReaderData readerData = new ReaderData(fileName);
        readerData.read();
        readerData.divideByName();

        List<Leaf> leafAcer = readerData.getLeafAcer();
        List<Leaf> leafQuercus = readerData.getLeafQuercus();

        Fisher fisher = new Fisher(leafAcer, leafQuercus);
        final long startTime = System.currentTimeMillis();
        FisherCoefficient maxFisherCoefficient = fisher.getMaxFisherCoefficient(5);
        final long endTime = System.currentTimeMillis();
        System.out.println(maxFisherCoefficient.toString());
        System.out.println((endTime - startTime) / 1000.0);

    }
}
