package org.example;


import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.message.EslMessage;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Client client = new Client();
        try {
            client.connect("127.0.0.1", 8021, "ClueCon", 10);
        } catch (InboundConnectionFailure e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter digit 1 or 2 or 3: ");
        String digitPressed = scanner.nextLine();
        EslMessage response;
        switch (digitPressed) {
            case "1":
                response = client.sendSyncApiCommand("originate", "user/1000 &playback(/usr/share/freeswitch/sounds/en/us/callie/voicemail/8000/vm-record_greeting.wav)");
                break;
            case "2":
                response = client.sendSyncApiCommand("bg_system", "/tmp/myApp.sh");
                break;
            case "3":
                response = client.sendSyncApiCommand("originate", "user/1000 &bridge(user/1001)");
            break;

            default:
                // Code to execute if none of the cases match
        }



//        EslMessage response = client.sendSyncApiCommand("eval", "${domain}");
//        client.setEventSubscriptions("plain", "all");
        // client.sendSyncApiCommand("",""); //here i run command on every hit like.
//        EslMessage response = client.sendSyncApiCommand( "sofia status", "" );
//        System.err.println("sofia status = [{}]" + response.getBodyLines().get(2));
//        try {
//            Thread.sleep( 125000 );
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        client.close();

        System.err.println("Hello world!");
//        client.setEventSubscriptions(EventFormat.PLAIN, "all");
    }
}