package client;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class HttpServerImpl implements HttpServer {

    AsynchronousServerSocketChannel channel;

    public HttpServerImpl() throws IOException {
        this.channel = AsynchronousServerSocketChannel.open();
    }

    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        channel.bind(socketAddress);
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }
}
