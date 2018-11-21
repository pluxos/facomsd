package org.kim.grpc.server.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorageHelper {

    private static final String FILE_NAME = "server_log.txt";

    private File file;

    private static FileStorageHelper fileStorageHelper;

    private FileStorageHelper() { init(); }

    private void init() { file = new File(FILE_NAME); }

    public static FileStorageHelper getInstance() {
        if (fileStorageHelper == null) fileStorageHelper = new FileStorageHelper();

        return fileStorageHelper;
    }

    public <T> void saveLogData(List<T> list){
        if (list != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                for (T item: list) objectOutputStream.writeObject(item);

                objectOutputStream.close();
                fileOutputStream.close();
            }
            catch (FileNotFoundException e) { }
            catch (IOException e) { }
        }
    }

    public <T> void saveLogData(T item){
        if (item != null) {
            List<T> list = recoverLogData();
            list.add(item);
            saveLogData(list);
        }
    }

    public <T> List<T> recoverLogData() {
        List<T> list = new ArrayList<T>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            boolean isRead = true;
            do {
                T item = (T) objectInputStream.readObject();
                if (item != null) list.add(item);
                else isRead = false;
            } while(isRead);

            objectInputStream.close();
            fileInputStream.close();
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        return list;
    }
}
