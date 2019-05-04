package server.commons.utils;

public class UserControl {
    private static final int MAX = 2;
    private static int count = 0;

    public static boolean startThread(){
        return MAX > count;
    }

    public static void newUser(){
        ++count;
    }

    public static void userDied(){
        --count;
    }
}
