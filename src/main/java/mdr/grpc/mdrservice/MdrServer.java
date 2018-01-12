package mdr.grpc.mdrservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  @author Marty Ross
 */
public class MdrServer {


    //
    //  Public class methods
    //

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        LOGGER.setLevel(Level.INFO);
        final MdrServer server = new MdrServer();
        server.startService();
        server.blockUntilShutdown();
    }


    //
    //  Private inner classes
    //

    private static class MdrServiceImpl extends MdrServiceGrpc.MdrServiceImplBase {
        @Override
        public void updateApiStatus(ApiStatus request, StreamObserver<Rc> responseObserver) {
            LOGGER.info(String.format("updateApiStatus(%s, %s)", request.getApiname(), request.getStatus()));
            responseObserver.onNext(Rc.newBuilder().setRc(99).build());
            responseObserver.onCompleted();
        }

    }


    //
    //  Private instance methods
    //

    private void startService() throws IOException {

        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new MdrServiceImpl()).build();
        server.start();
        LOGGER.info("server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(
            new Thread(
                () -> {
                    // Use stderr here since the LOGGER may have been reset by its JVM shutdown hook.
                    System.err.println("*** shutting down gRPC server since JVM is shutting down");
                    stopService();
                    System.err.println("*** server shut down");
                }
            )
        );
    }

    private void stopService() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    //
    //  Private class data
    //

    private static final Logger LOGGER = Logger.getLogger(MdrServer.class.getName());


    //
    //  Private instance data
    //

    private Server server;

}
