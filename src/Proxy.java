import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by leolinhares on 21/11/16.
 */
public class Proxy implements ProxyInterface{

    private int proxyID;
    private static Map<Integer, int[]> map = new HashMap<>();

    public Proxy(int id) throws RemoteException {
        proxyID = id;
        System.out.println("Proxy "+ proxyID +" Initialized");
    }

    @Override
    public String createFile(String filename) throws Exception {
        int partition = hash(filename).intValue();
        System.out.println(partition);
        int[] storageNodes = map.get(partition);

        Registry registry = LocateRegistry.getRegistry(null);

        for (int node: storageNodes) {
            System.out.println(node);
            // Establish connection with the storage nodes
            StorageInterface storageStub = (StorageInterface) registry.lookup("StorageInterface"+node);
            storageStub.createFile(filename);
        }
        return null;
    }

    @Override
    public String readFile(String filename) {

        return null;
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

    public static BigInteger hash(String filename) throws Exception{
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(filename.getBytes(), 0, filename.length());
        BigInteger b = new BigInteger(1,m.digest());
        return b.mod(new BigInteger("3"));
//        System.out.println(b.mod(new BigInteger("3"))); // ID da partição
//        System.out.println("MD5: " + b.toString(16)); // Hash
    }

    public static void main(String[] args) {
        System.out.println("Digite o numero do proxy: ");
        Scanner in = new Scanner(System.in);
        try{
            Proxy proxy = new Proxy(in.nextInt());
            ProxyInterface stub = (ProxyInterface) UnicastRemoteObject.exportObject(proxy,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ProxyInterface"+proxy.proxyID, stub);

            System.out.println("Proxy ready "+proxy.proxyID+" \n\nLog:\n");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
}
