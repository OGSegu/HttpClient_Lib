package server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public abstract class HttpHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

    protected static final int BYTE_LIMIT = 256;

    protected HttpHandler() {}

    protected StringBuilder readRequest(AsynchronousSocketChannel socketChannel) throws InterruptedException, ExecutionException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_LIMIT);
        StringBuilder sb = new StringBuilder();
        boolean keepReading = true;
        while (keepReading) {
            socketChannel.read(byteBuffer).get();

            keepReading = byteBuffer.position() == BYTE_LIMIT;

            byte[] arrayHttpResponse = keepReading
                    ? byteBuffer.array()
                    : Arrays.copyOfRange(byteBuffer.array(), 0, byteBuffer.position());

            sb.append(new String(arrayHttpResponse));
            byteBuffer.clear();
        }
        return sb;
    }

}
