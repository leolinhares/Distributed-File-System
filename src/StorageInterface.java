import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface StorageInterface extends Remote{
    String createResource(String filename) throws RemoteException;
    String requestResource(String filename) throws RemoteException;
}
