package utils;

public class HelloWorldApp {
  public static void main(String[] args) {
    HelloWorldServer s = new HelloWorldServer();
    try {
      s.activate();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
