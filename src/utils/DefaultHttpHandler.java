package utils;

import dto.HttpRequestDTO;
import exception.HttpSyntaxException;
import server.HttpHandler;
import service.HttpRequestMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
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
            logger.log(Level.INFO, "Received data:\r\n{0}", sb);
            HttpRequestDTO httpRequestDTO = HttpRequestMapper.parse(sb.toString());

            /*
            Init all endpoints and store it in Map

            Map<T, A>
            T - endpoint, probably string
            A - class by reflection

            Get endpoint class for httpRequestDTO.getEndpoint() and find method get, post and etc.

             */

            switch (httpRequestDTO.getEndpoint()) {
                case ("/"):
                    socketChannel.write(ByteBuffer.wrap("In root".getBytes()));
                    break;
                case ("/user"):
                    socketChannel.write(ByteBuffer.wrap("In user".getBytes()));
                    socketChannel.write(ByteBuffer.wrap(httpRequestDTO.getParams().toString().getBytes()));
                    break;
                default:
                    socketChannel.write(ByteBuffer.wrap("ERROR! FAILED TO GET THIS ENDPOINT".getBytes()));
            }
        } catch (ExecutionException e) {
            logger.log(Level.WARNING,"Failed to read request:\r\n{0}", e.getCause().getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to get remote socket address");
        } catch (HttpSyntaxException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                logger.warning("Failed to close connection");
            }
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
        exc.printStackTrace();
    }


}
