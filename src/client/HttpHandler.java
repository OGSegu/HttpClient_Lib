package client;

import java.nio.channels.Channel;

public interface HttpHandler {

    void handle(Channel socketChannel);

}
