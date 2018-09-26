package com.client.impl.test;

import com.client.ClientException;

import java.net.UnknownHostException;

public class DataStoreClient {

    public static void main(String[] args) {
        DataStoreClientTest dataStoreClientTest = new DataStoreClientTest();
        try {
            dataStoreClientTest.testWrite();
            dataStoreClientTest.testRead();
            dataStoreClientTest.testDelete();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
