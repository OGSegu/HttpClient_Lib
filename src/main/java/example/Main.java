package example;

import server.AsyncHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsyncHttpServer asyncHttpServer = new AsyncHttpServer(8080, null);

    }
}
