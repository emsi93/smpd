import java.util.List;

public class Main {

    public static void main(String[] argv) {

        MyFrame myFrame = new MyFrame();
        myFrame.run();
//        String fileName = "Maple_Oak.txt";
//
//        ReaderData readerData = new ReaderData(fileName);
//        readerData.read();
//        List<Leaf> leafs = readerData.getLeafs();
//        readerData.divideByName();
//
//        List<Leaf> leafAcer = readerData.getLeafAcer();
//        List<Leaf> leafQuercus = readerData.getLeafQuercus();
//
//        Fisher fisher = new Fisher(leafAcer, leafQuercus);
//        final long startTime = System.currentTimeMillis();
//        FisherCoefficient maxFisherCoefficient = fisher.getMaxFisherCoefficient(3);
//        final long endTime = System.currentTimeMillis();
//        System.out.println(maxFisherCoefficient.toString());
//        System.out.println((endTime - startTime) / 1000.0);

//        SFS sfs = new SFS(leafAcer, leafQuercus);
//        final long startTime = System.currentTimeMillis();
//        FisherCoefficient maxFisherCoefficient = sfs.getMaxFisherCoefficient(3);
//        final long endTime = System.currentTimeMillis();
//        System.out.println(maxFisherCoefficient.toString());
//        System.out.println((endTime - startTime) / 1000.0);

//        Collections.shuffle(leafs);
//        List<Leaf> testList = leafs.subList(0,20);
//        List<Leaf> trainingList = leafs.subList(20, leafs.size());
//
//
//        NearestNeighborClassifier classifier = new NearestNeighborClassifier(testList, trainingList);
//        double dopasowanie = classifier.assignToClass(7);
//
//        double wynik = dopasowanie / testList.size();
//
//        System.out.println(wynik*100);
//
//        System.out.println("KONIEC");

//        SmallestAverageClassifier classifier = new SmallestAverageClassifier(testList, trainingList);
//        double dopasowanie = classifier.assignToClass(2);
//        double wynik = dopasowanie / testList.size();
//        System.out.println(wynik*100);
//        System.out.println("KONIEC");
    }

}
