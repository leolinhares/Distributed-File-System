package br.com.leolinhares;

import java.util.UUID;

/**
 * Created by leolinhares on 21/11/16.
 */
public class Storage implements StorageInterface {

    private static UUID storageID = UUID.randomUUID();

    @Override
    public String createResource(String filename) {
        return null;
    }

    @Override
    public String requestResource(String filename) {
        return null;
    }
}
