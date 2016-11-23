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
    public String createFile(String filename, String contents){
        int partition = 0;
        String result = null;

        try {
            partition = hash(filename).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Partition " + partition);
        int[] storageNodes = map.get(partition);

        try{
        Registry registry = LocateRegistry.getRegistry(null);
            for (int node: storageNodes) {
                // Establish connection with the storage nodes
                StorageInterface storageStub = (StorageInterface) registry.lookup("StorageInterface" + node);
                result = storageStub.createFile(filename, contents);
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String readFile(String filename) {
        int partition = 0;
        String result = null;

        try {
            partition = hash(filename).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Partition " + partition);
        int[] storageNodes = map.get(partition);

        try{
            Registry registry = LocateRegistry.getRegistry(null);
            for (int node: storageNodes) {
                // Establish connection with the storage nodes
                StorageInterface storageStub = (StorageInterface) registry.lookup("StorageInterface" + node);
                result = storageStub.readFile(filename);
                break;
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void load() throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("replica.txt"));
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
    }

    public static void main(String[] args) throws FileNotFoundException {
        load();
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
