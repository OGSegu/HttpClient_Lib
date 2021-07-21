package server;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public abstract class HttpHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

    protected static final int BYTE_LIMIT = 256;

    protected HttpHandler() {}

}
