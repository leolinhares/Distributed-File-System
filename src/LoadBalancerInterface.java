import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface LoadBalancerInterface extends Remote{
    String getProxy() throws RemoteException;
}
