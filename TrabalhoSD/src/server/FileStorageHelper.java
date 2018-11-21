package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileStorageHelper {

    private static final String FILE_NAME = "D:\\Faculdade\\6º Período\\sd\\facomsd\\TrabalhoSD\\src\\server\\server_log.txt";

    private File file;
    private static FileStorageHelper fileStorageHelper;

    private FileStorageHelper() {
        init();
    }

    private void init() {
        file = new File( FILE_NAME );
    }

    static FileStorageHelper getInstance() {
        if ( fileStorageHelper == null ) fileStorageHelper = new FileStorageHelper();

        return fileStorageHelper;
    }

    private < T > void saveLogData( List< T > list ){
        if ( list != null ) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream( file );
                ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );

                for (T item : list) {
                    objectOutputStream.writeObject(item);
                }

                objectOutputStream.close();
                fileOutputStream.close();

            } catch (IOException e) { System.out.println("SAVE DATA ERROR: " + e.getMessage()); }
        }
    }

    < T > void saveLogData(T item){
        if ( item != null ) {
            List< T > list = recoverLogData();
            list.add( item );
            saveLogData( list );
        }
    }

    < T > List< T > recoverLogData() {
        List< T > list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream( file );
            ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream );

            boolean isRead = true;
            do {
                T item = ( T ) objectInputStream.readObject();

                if ( item != null ) list.add( item );
                else isRead = false;

            } while( isRead );

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) { System.out.println("RECOVER DATA ERROR: " + e.getMessage()); }

        return list;
    }
}
