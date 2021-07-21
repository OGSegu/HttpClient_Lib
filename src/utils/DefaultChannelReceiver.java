package utils;

import client.HttpHandler;

import java.util.concurrent.Future;

public class DefaultChannelReceiver implements ChannelReceiver {


    @Override
    public Future<Integer> accept(HttpHandler httpHandler) {
        return null;
    }

}
