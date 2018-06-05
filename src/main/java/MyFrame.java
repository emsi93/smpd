
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class MyFrame extends JFrame {


    final static String FISHER = "FISHER";
    final static String NN = "NN";
    final static String NM = "NM";

    static final int FPS_MIN = 10;
    static final int FPS_MAX = 50;

    private JFileChooser fc;
    private JButton openFile;
    private JButton openFile2;
    private JButton openFile3;
    private JButton process;
    private JButton process2;
    private JButton process3;
    private TextArea textArea;
    private TextArea textArea2;
    private TextArea textArea3;
    private JLabel labelForValuesList;
    private JComboBox valuesList;
    private JComboBox valuesList2;
    private JComboBox valuesList3;
    private List<Leaf> leafAcer;
    private List<Leaf> leafQuercus;
    private List<Leaf> records;
    private Integer[] values = { 1,2,3,4,5,6,7,8,9,10 };
    private Integer[] values2 = { 1,3,5,7,9,11 };
    private Integer[] values3 = { 1,3,5,7,9,11 };
    private JSlider slider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, 25);
    private JSlider slider2 = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, 25);

    private int howManyFeatures;
    private FisherCoefficient fisherCoefficient;
    private int k;
    private int kk;
    private int sizeOfTestList;
    private int sizeOfTestList2;




    public MyFrame() {
        super("SMPD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(FISHER, buildContent());
        tabbedPane.add(NN, buildContent2());
        tabbedPane.add(NM, buildContent3());
        getContentPane().add(tabbedPane);

        setSize(500,200);
        setLocation(50,50);
        setVisible(true);
    }


    public void run(){
        valuesList.setSelectedIndex(0);
        howManyFeatures = (Integer) valuesList.getSelectedItem();


        valuesList2.setSelectedIndex(0);
        k = (Integer) valuesList2.getSelectedItem();

        valuesList3.setSelectedIndex(0);
        kk = (Integer) valuesList3.getSelectedItem();



        valuesList.addActionListener(e -> {
            howManyFeatures = (Integer) valuesList.getSelectedItem();
        });

        valuesList2.addActionListener(e -> {
            k = (Integer) valuesList2.getSelectedItem();
        });

        valuesList3.addActionListener(e -> {
            kk = (Integer) valuesList3.getSelectedItem();
        });

        openFile.addActionListener(e -> {
            openFile();
            process.setEnabled(true);
        });

        openFile2.addActionListener(e -> {
            openFile();
            process2.setEnabled(true);
            sizeOfTestList = (int) (records.size()*( 25 / 100.0));
            textArea2.setText(String.valueOf(sizeOfTestList));
        });

        openFile3.addActionListener(e -> {
            openFile();
            process3.setEnabled(true);
            sizeOfTestList2 = (int) (records.size()*( 25 / 100.0));
            textArea3.setText(String.valueOf(sizeOfTestList));
        });

        process.addActionListener(e -> {
            fisherCoefficient = null;
            Fisher fisher = new Fisher(leafAcer, leafQuercus);
            final long startTime = System.currentTimeMillis();
            fisherCoefficient = fisher.getMaxFisherCoefficient(howManyFeatures);
            final long stopTime = System.currentTimeMillis();
            textArea.setText(fisherCoefficient.toString() + "\n" + (stopTime - startTime) / 1000.0) ;
        });

        process2.addActionListener(e -> {
            Collections.shuffle(records);
            List<Leaf> testList = records.subList(0,sizeOfTestList);
            List<Leaf> trainingList = records.subList(sizeOfTestList, records.size());
            NearestNeighborClassifier classifier = new NearestNeighborClassifier(testList, trainingList);
            int dopasowanie = classifier.assignToClass(k);
            double wynik = dopasowanie / (testList.size()*1.0);
            textArea2.setText(String.valueOf(sizeOfTestList) + "\n" + "dopasowanie: " + String.valueOf(wynik*100.0) + "%");

        });

        process3.addActionListener(e -> {
            Collections.shuffle(records);
            List<Leaf> testList = records.subList(0,sizeOfTestList2);
            List<Leaf> trainingList = records.subList(sizeOfTestList2, records.size());
            SmallestAverageClassifier classifier = new SmallestAverageClassifier(testList, trainingList);
            double dopasowanie = classifier.assignToClass(kk);
            double wynik = dopasowanie / testList.size();
            textArea3.setText(String.valueOf(sizeOfTestList2) + "\n" + "dopasowanie: " + String.valueOf(wynik*100.0) + "%");

        });

        slider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()){
                sizeOfTestList =  (int)(records.size() * ( source.getValue() / 100.0 ));
                textArea2.setText(String.valueOf(sizeOfTestList));
            }
        });
        slider2.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()){
                sizeOfTestList2 =  (int)(records.size() * ( source.getValue() / 100.0 ));
                textArea3.setText(String.valueOf(sizeOfTestList2));
            }
        });
    }

    private void init(){
        fc = new JFileChooser();
        openFile = new  JButton("Open file");
        openFile2 = new  JButton("Open file");
        openFile3 = new  JButton("Open file");
        process = new  JButton("Run");
        process2 = new JButton("Run");
        process3 = new JButton("Run");
        process.setEnabled(false);
        process2.setEnabled(false);
        process3.setEnabled(false);
        textArea = new TextArea(2, 5);
        textArea2 = new TextArea(2, 5);
        textArea3 = new TextArea(2, 5);
        valuesList = new JComboBox(values);
        valuesList2 = new JComboBox(values2);
        valuesList3 = new JComboBox(values3);
        labelForValuesList = new JLabel("k:");

    }

    private JPanel buildContent(){
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelNorth = new JPanel(new FlowLayout());
        panel.add(panelNorth, BorderLayout.NORTH);

        JPanel panelSouth = new JPanel(new BorderLayout());
        panel.add(panelSouth, BorderLayout.SOUTH);

        panelNorth.add(openFile);
        panelNorth.add(labelForValuesList);
        panelNorth.add(valuesList);
        panelNorth.add(process);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panel.add(panelCenter, BorderLayout.CENTER);

        panelCenter.add(textArea);

        JPanel panelWest = new JPanel(new BorderLayout());
        panel.add(panelWest, BorderLayout.WEST);

        JPanel panelEast = new JPanel(new BorderLayout());
        panel.add(panelEast, BorderLayout.EAST);
        return panel;
    }
    private JPanel buildContent2(){
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelNorth = new JPanel(new FlowLayout());
        panel.add(panelNorth, BorderLayout.NORTH);

        JPanel panelSouth = new JPanel(new BorderLayout());
        panel.add(panelSouth, BorderLayout.SOUTH);

        panelNorth.add(openFile2);

        panelNorth.add(slider);
        panelNorth.add(labelForValuesList);
        panelNorth.add(valuesList2);
        panelNorth.add(process2);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panel.add(panelCenter, BorderLayout.CENTER);

        panelCenter.add(textArea2);

        JPanel panelWest = new JPanel(new BorderLayout());
        panel.add(panelWest, BorderLayout.WEST);

        JPanel panelEast = new JPanel(new BorderLayout());
        panel.add(panelEast, BorderLayout.EAST);
        return panel;

    }


    private JPanel buildContent3() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelNorth = new JPanel(new FlowLayout());
        panel.add(panelNorth, BorderLayout.NORTH);

        JPanel panelSouth = new JPanel(new BorderLayout());
        panel.add(panelSouth, BorderLayout.SOUTH);

        panelNorth.add(openFile3);

        panelNorth.add(slider2);
        panelNorth.add(labelForValuesList);
        panelNorth.add(valuesList3);
        panelNorth.add(process3);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panel.add(panelCenter, BorderLayout.CENTER);

        panelCenter.add(textArea3);

        JPanel panelWest = new JPanel(new BorderLayout());
        panel.add(panelWest, BorderLayout.WEST);

        JPanel panelEast = new JPanel(new BorderLayout());
        panel.add(panelEast, BorderLayout.EAST);
        return panel;

    }

    private void openFile(){
        int returnVal = fc.showOpenDialog(this);
        File file = fc.getSelectedFile();

        if (returnVal == JFileChooser.APPROVE_OPTION && file.getName().contains(".txt")) {

            ReaderData readerData = new ReaderData(file.getPath());
            readerData.read();
            records = readerData.getLeafs();
            readerData.divideByName();
            leafAcer = readerData.getLeafAcer();
            leafQuercus = readerData.getLeafQuercus();
        } else {
            System.out.println("ZLY PLIK");
            return;
        }
    }
}
