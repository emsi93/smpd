
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.util.List;

public class MyFrame extends JFrame {

    private JFileChooser fc;
    private JButton openFile;
    private JButton process;
    private TextArea textArea;
    private JLabel labelForValuesList;
    private JComboBox valuesList;
    private List<Leaf> leafAcer;
    private List<Leaf> leafQuercus;
    private Integer[] values = { 1,2,3,4,5,6,7,8,9,10 };
    private int howManyFeatures;
    private FisherCoefficient fisherCoefficient;


    public MyFrame() {
        super("SMPD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
        getContentPane().add(buildContent());

        setSize(500,200);
        setLocation(50,50);
        setVisible(true);
    }

    public void run(){
        valuesList.setSelectedIndex(0);
        howManyFeatures = (Integer) valuesList.getSelectedItem();
        valuesList.addActionListener(e -> {
            howManyFeatures = (Integer) valuesList.getSelectedItem();
        });


        openFile.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(this);
            File file = fc.getSelectedFile();

            if (returnVal == JFileChooser.APPROVE_OPTION && file.getName().contains(".txt")) {

                ReaderData readerData = new ReaderData(file.getPath());
                readerData.read();
                readerData.divideByName();

                leafAcer = readerData.getLeafAcer();
                leafQuercus = readerData.getLeafQuercus();
                process.setEnabled(true);
            } else {
                System.out.println("ZLY PLIK");
                return;
            }
        });

        process.addActionListener(e -> {
            fisherCoefficient = null;
            Fisher fisher = new Fisher(leafAcer, leafQuercus);
            final long startTime = System.currentTimeMillis();
            fisherCoefficient = fisher.getMaxFisherCoefficient(howManyFeatures);
            final long stopTime = System.currentTimeMillis();
            textArea.setText(fisherCoefficient.toString() + "\n" + (stopTime - startTime) / 1000.0) ;
        });
    }

    private void init(){
        fc = new JFileChooser();
        openFile = new  JButton("Open file");
        process = new  JButton("Run");
        process.setEnabled(false);
        textArea = new TextArea(2, 5);
        valuesList = new JComboBox(values);
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
}
