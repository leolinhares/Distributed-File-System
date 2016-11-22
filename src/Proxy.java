import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by leolinhares on 21/11/16.
 */
public class Proxy implements ProxyInterface{

    private static UUID proxyID = UUID.randomUUID();

    public Proxy() throws RemoteException {
        System.out.println("Proxy "+ proxyID +" Initialized");
    }

    @Override
    public String createResource(String filename, int id) {
        try {
            // Establish connection with the storage nodes
            Registry registry = LocateRegistry.getRegistry(null);
            StorageInterface stub = (StorageInterface) registry.lookup("StorageInterface"+id);
            stub.createResource(filename);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public String requestResource(String filename, int id) {
        return null;
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
        try{
            Proxy proxy = new Proxy();
            ProxyInterface stub = (ProxyInterface) UnicastRemoteObject.exportObject(proxy,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ProxyInterface", stub);

            System.out.println("Proxy ready\n\nLog:\n");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
}
