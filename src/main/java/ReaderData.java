import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReaderData {

    private String fileName;
    private List<String> records;
    private List<Leaf> leafAcer;
    private List<Leaf> leafQuercus;
    private List<Leaf> leafs;

    public ReaderData(String fileName){
        this.fileName = fileName;
    }

    public void read(){
        records = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(it -> records.add(it));

        } catch (IOException e) {
            e.printStackTrace();
        }

        split();
    }

    private void split(){
        leafs = new ArrayList<>();
        for(int i=1; i<records.size();i++){
            String[] parts = records.get(i).split(",");
            String name = parts[0];

            Leaf leaf = new Leaf(name);
            for(int j=1; j<parts.length; j++)
                    leaf.addFeature(Double.parseDouble(parts[j]));
            leafs.add(leaf);
        }
    }

    public void divideByName(){
        leafAcer = new ArrayList<>();
        leafQuercus = new ArrayList<>();

        for(int i = 0; i< leafs.size(); i++){
           if(leafs.get(i).getName().contains("Acer"))
               leafAcer.add(leafs.get(i));
           else
               leafQuercus.add(leafs.get(i));
       }
    }

    public List<Leaf> getLeafAcer() {
        return leafAcer;
    }

    public List<Leaf> getLeafQuercus() {
        return leafQuercus;
    }

    public List<Leaf> getLeafs() {
        return leafs;
    }
}

