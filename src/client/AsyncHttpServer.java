package client;

import utils.DefaultHttpHandler;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class AsyncHttpServer implements HttpServer {

    public final AsynchronousServerSocketChannel channel;
    public final HttpHandler httpHandler;

    public AsyncHttpServer() throws IOException {
        this(AsynchronousServerSocketChannel.open(), new DefaultHttpHandler());
    }

    public AsyncHttpServer(HttpHandler httpHandler) throws IOException {
        this(AsynchronousServerSocketChannel.open(), httpHandler);
    }

    public AsyncHttpServer(AsynchronousServerSocketChannel channel) throws IOException {
        this(channel, new DefaultHttpHandler());
    }

    public AsyncHttpServer(AsynchronousServerSocketChannel channel, HttpHandler httpHandler) {
        this.channel = channel;
        this.httpHandler = httpHandler;
    }


    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        channel.bind(socketAddress);
    }

    @Override
    public HttpHandler handler() {
        return this.httpHandler;
    }

    @Override
    public void accept() {
        channel.accept(this.channel, httpHandler);
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}
