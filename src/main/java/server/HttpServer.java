package server;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;

public interface HttpServer extends Closeable {

    void bind(SocketAddress socketAddress) throws IOException;

    HttpHandler handler();

    void accept();

}
