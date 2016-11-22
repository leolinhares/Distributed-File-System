import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface StorageInterface extends Remote{
    String createFile(String filename) throws RemoteException, FileNotFoundException;
}
