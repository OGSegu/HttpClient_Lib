package utils;

import client.HttpHandler;

import java.util.concurrent.Future;

public interface ChannelReceiver {

    Future<Integer> accept(HttpHandler httpHandler);

}
