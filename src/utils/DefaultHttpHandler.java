package utils;

import client.HttpHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class DefaultHttpHandler extends HttpHandler  {

    @Override
    public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
        try {
            System.out.println("Received connection from: " + socketChannel.getRemoteAddress().toString());
            serverSocketChannel.accept(serverSocketChannel, this);
            StringBuilder sb = new StringBuilder();
            ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_LIMIT);
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
            System.out.println(sb);
            socketChannel.write(ByteBuffer.wrap("Your input was received. Thank you\r\n".getBytes()));
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {

    }
}
