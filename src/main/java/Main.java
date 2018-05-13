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
        FisherCoefficient maxFisherCoefficient = fisher.getOneMaxFisherCoefficient();
        System.out.println(maxFisherCoefficient.toString());


    }
}
