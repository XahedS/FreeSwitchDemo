package org.example;


import lombok.Value;
import org.example.config.ClientConnector;

public class Main {

    public static void main(String[] args) {
        ClientConnector clientConnector = new ClientConnector("127.0.0.1", 8021, "ClueCon", 10);
        clientConnector.clientConnect();
    }

}