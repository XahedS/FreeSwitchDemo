package org.example.config;

import lombok.Data;
import org.example.business.BusinessImplementation;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;

import java.util.Map;
@Data
public class ClientConnector implements  Constants{
    private String address;
    private String password;
    private int port;
    private int timeoutMs;

    private BusinessImplementation businessImplementation;
    public ClientConnector(String address,  int port, String password, int timeoutMs) {
        this.address = address;
        this.password = password;
        this.port = port;
        this.timeoutMs = timeoutMs;
        businessImplementation = new BusinessImplementation();
    }

    public Client clientConnect() {
        Client client = new Client();

        client.addEventListener(new IEslEventListener() {
            public void eventReceived(EslEvent event) {
//                log.info( "Event received [{}]", event );
                if (event.getEventName().equals(EVENT_TYPE)) {
                    String dtmfReceived = getDTMFDigit(event);
                    String uuid = event.getEventHeaders().get(EVENT_HEADER_FIELD_UniqueID);//event.getEventHeaders().get("Core-UUID");//event.getEventHeaders().get("unique-id");
                    businessImplementation.actionsBasedOnUserInput(dtmfReceived, uuid, client);
                }
            }

            public void backgroundJobResultReceived(EslEvent event) {
//                log.info( "Background job result received [{}]", event );
            }

        });

        try {
            client.connect(address, port, password, timeoutMs);
        } catch (InboundConnectionFailure e) {
            throw new RuntimeException(e);
        }
        clientEventSubscription(client);
        return client;
    }

    public void clientEventSubscription(Client client) {
        client.setEventSubscriptions(EVENT_FORMAT, EVENT_TYPE);
    }

    public static String getDTMFDigit(EslEvent event) {
        return event.getEventHeaders()
                .entrySet()
                .stream()
                .filter(entry -> "DTMF-Digit".equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .peek(value -> System.err.println("You pressed " + value))
                .findFirst()
                .orElse(null);
    }
}
