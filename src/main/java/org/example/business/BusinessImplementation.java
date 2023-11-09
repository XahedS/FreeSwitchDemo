package org.example.business;

import org.example.config.Constants;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;

public class BusinessImplementation implements Constants {

    public void actionsBasedOnUserInput(String dtmfReceived, String uuid, Client client) {


        switch (dtmfReceived) {
            case "1":
                messageBuilder(uuid, client, CALL_COMMAND, APP_NAME_PLAYBACK, FILE_PATH_PLAYBACK);
//                            response = client.sendSyncApiCommand("originate", "user/1000 &playback(/usr/share/freeswitch/sounds/en/us/callie/ivr/8000/ivr-appointment_schedule_for.wav)");
                break;
            case "2":
                client.sendSyncApiCommand("bg_system", "/tmp/myApp.sh");
                break;
            case "3":
                messageBuilder(uuid, client, CALL_COMMAND, APP_NAME_BRIDGE, PEER_TOBE_CALLED);
//                            response = client.sendSyncApiCommand("originate", "user/1000 &bridge(user/1001)");
                break;

            default:
        }
    }

    public CommandResponse messageBuilder(String uuid, Client client, String callCommand, String appName, String appArg) {
        SendMsg sendMsg = new SendMsg(uuid);
        sendMsg.addCallCommand(callCommand);
        sendMsg.addExecuteAppName(appName);
        sendMsg.addExecuteAppArg(appArg);
        sendMsg.addEventLock();
        CommandResponse response = client.sendMessage(sendMsg);

        return response;
    }
}
