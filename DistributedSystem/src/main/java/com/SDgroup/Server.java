package com.SDgroup;

import java.io.*;
import java.util.Properties;

class Server{
  public static void main(String argv[]) throws Exception {
    Properties properties = new Properties();
    FileInputStream propsFS = new FileInputStream("src/main/resources/Constants.prop");
    properties.load(propsFS);
    Thread[] entrypoints = new Thread[Integer.parseInt( properties.getProperty("threads"))];
    Integer port = Integer.parseInt(properties.getProperty("port"));
    for (Thread entrypoint : entrypoints) {
      entrypoint = new Thread(new EntryPoint(port));
      entrypoint.start();
    }
  }

  
  
}