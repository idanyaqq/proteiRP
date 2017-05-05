package ru.originld.server;

/**
 * Created by redin on 5/3/17.
 */
public class App {

    private WebServer server;

    public static void main(String[] args) throws Exception {
        new App().start();
    }

    public App() throws Exception {
        server = new WebServer();
    }

    private void start() throws Exception{
        addShutdownHook();
        server.start();
        server.join();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    server.stop();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }));
    }

}
