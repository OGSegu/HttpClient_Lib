package utils;

import server.HttpHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultHttpHandler extends HttpHandler  {

    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
        try {
            logger.log(Level.INFO, "Received connection from: {0}", socketChannel.getRemoteAddress());
            serverSocketChannel.accept(serverSocketChannel, this);
            StringBuilder sb = readRequest(socketChannel);
            logger.log(Level.INFO, "Received data:\n{0}", sb);
            socketChannel.write(ByteBuffer.wrap("Your input was received. Thank you\n".getBytes()));
        } catch (ExecutionException e) {
            logger.log(Level.WARNING,"Failed to read request:\n{0}", e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.warning("Failed to get remote socket address");
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                logger.warning("Failed to close connection");
            }
        }
    }

    private StringBuilder readRequest(AsynchronousSocketChannel socketChannel) throws InterruptedException, ExecutionException {
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

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
        exc.printStackTrace();
    }


}
