package client;

import utils.ChannelReceiver;
import utils.DefaultChannelReceiver;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class AsyncHttpServer implements HttpServer {

    private final AsynchronousServerSocketChannel channel;
    private final ChannelReceiver channelReceiver;

    public AsyncHttpServer() throws IOException {
        this.channel = AsynchronousServerSocketChannel.open();
        this.channelReceiver = new DefaultChannelReceiver();
    }

    public AsyncHttpServer(AsynchronousServerSocketChannel channel, ChannelReceiver channelReceiver) {
        this.channel = channel;
        this.channelReceiver = channelReceiver;
    }

    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        channel.bind(socketAddress);
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    public AsynchronousServerSocketChannel getChannel() {
        return channel;
    }
}
