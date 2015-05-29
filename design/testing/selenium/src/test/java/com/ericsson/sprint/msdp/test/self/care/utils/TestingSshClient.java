package com.ericsson.sprint.msdp.test.self.care.utils;

import java.io.IOException;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.xfer.FileSystemFile;

//https://github.com/shikhar/sshj
//SSHJ examples https://github.com/shikhar/sshj/tree/master/src/main/java/examples

public class TestingSshClient extends SSHClient {
  
  private static String host                  = "5.232.9.94";
  private static String userName              = "ericsson";
  private static String password              = "ericsson";
  private static String logDirectory          = "/opt/drutt/ca/odp/log/";
  private static String logDirectoryForTail   = "/home/ericsson/";
  private static String logName               = "server.log";
  private static String hostKeyVerifier       = "33:05:a6:44:5c:be:69:70:34:55:2b:3d:cf:1a:ad:41";

  private static Session session;
  private static Command cmd;
  
  /*
  public static SSHClient initiateSSH(){
    System.out.println("initiateSSH");
    try {
      sshClient.loadKnownHosts();
      sshClient.addHostKeyVerifier("5b:95:ee:9b:5d:62:e3:60:ca:be:68:f0:cb:05:2f:4d");
      System.out.println("Connecting");
      sshClient.connect(host);
      sshClient.authPassword(userName, password);
    
      System.out.println("Starting session");
      session = sshClient.startSession();
    } catch (Exception e){
        System.out.println("Exception " + e.getMessage());
    }
    System.out.println("End");
    return sshClient;
  }
  */
  
  public TestingSshClient(){
    //sshClient = new SSHClient();
    System.out.println("Initiating ssh session.");
    this.addHostKeyVerifier(hostKeyVerifier);
    try {
      this.loadKnownHosts();
      System.out.println("Connecting");
      this.connect(host);
      this.authPassword(userName, password);
      System.out.println("Starting session.");
      session = this.startSession();
    } catch (Exception e){
        System.out.println("Exception " + e.getMessage());
    }
    System.out.println("Session started.");
  }
  
  public TestingSshClient(String host, String userName, String password){
    System.out.println("initiateSSH");
    this.addHostKeyVerifier("5b:95:ee:9b:5d:62:e3:60:ca:be:68:f0:cb:05:2f:4d");
    try {
      this.loadKnownHosts();
      System.out.println("Connecting");
      this.connect(host);
      this.authPassword(userName, password);
    
      System.out.println("Starting session");
      session = this.startSession();
    } catch (Exception e){
        System.out.println("Exception " + e.getMessage());
    }
    System.out.println("End");
  }
  
  public void followLog(String testCaseName){
    String commandString = "tail -f " + logDirectory + logName 
        + " > " + logDirectoryForTail + "" + testCaseName + "_" + logName;
    System.out.println("Starting followLog with commandString " + commandString);
    try {
        cmd = session.exec(commandString);
        //session.exec(commandString);
        //long timeout = 3000;
        //cmd.wait(timeout);
    } catch (ConnectionException | TransportException e) {
        System.out.println(e.getMessage());
    }
  }
  
  public void stopFollowLog(){
    System.out.println("stopFollowLog.");
    try {
      cmd.close();
    } catch (ConnectionException | TransportException e) {
        System.out.println(e.getMessage());
    }
  }
  
  public void downloadLog(String testCaseName, String reportDirectory){
    System.out.println("Starting downloadLog.");
      try {
        this.newSCPFileTransfer().download(logDirectoryForTail + "" + testCaseName + "_" + logName , 
            new FileSystemFile(reportDirectory + testCaseName + "_" + logName));
        System.out.println("Starting disconnect.");
        this.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
  
  public void downloadZippedLogs(String reportDirectory){
      try {
        String commandString = "zip " + logDirectoryForTail + "test.zip";
        System.out.println("Starting downloadLogEnhanced with commandString " + commandString);
        cmd = session.exec(commandString);
        this.newSCPFileTransfer().download(logDirectoryForTail + "test.zip", 
            new FileSystemFile(reportDirectory + "test.zip"));
        System.out.println("Starting disconnect.");
        this.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  public void closeSSH(){
    try {
      //session.close();
      this.disconnect();
    } catch (IOException e) {
      System.out.println("closeSSH exception" + e.getMessage());
    }
  }
  
  public String getHost() {
    return host;
  }
  public void setHost(String host) {
    this.host = host;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getLogDirectory() {
    return logDirectory;
  }
  public void setLogDirectory(String logDirectory) {
    this.logDirectory = logDirectory;
  }
  public String getLogName() {
    return logName;
  }
  public void setLogName(String logName) {
    this.logName = logName;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public static String getLogDirectoryTail() {
    return logDirectoryForTail;
  }

  public static void setLogDirectoryTail(String logDirectoryTail) {
    TestingSshClient.logDirectoryForTail = logDirectoryTail;
  }
}
