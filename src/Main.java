import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static Map<Integer, int[]> map = new HashMap<>();


    public static void main(String[] args) throws FileNotFoundException {
        load();
    }

    public static void load() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("src/br/com/leolinhares/replica.txt"));
        int i = 0;
        while(in.hasNextLine()){
            String[] fields = in.nextLine().split(",");
            int [] replicas = Stream.of(fields).mapToInt(Integer::parseInt).toArray();
            map.put(i, replicas);
            i++;
        }
        in.close();
    }
}
