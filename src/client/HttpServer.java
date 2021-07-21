package client;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;

public interface HttpServer extends Closeable {

    void bind(SocketAddress socketAddress) throws IOException;

}
