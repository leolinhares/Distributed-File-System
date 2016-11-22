package br.com.leolinhares;

import java.rmi.Remote;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface ProxyInterface extends Remote{

    String createResource(String filename);
    String requestResource(String filename);

}
