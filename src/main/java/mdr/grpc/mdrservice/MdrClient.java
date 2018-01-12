package mdr.grpc.mdrservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 *  @author Marty Ross
 */
public class MdrClient {


  //
  //  Public class methods
  //

  public static void main(String[] args) throws InterruptedException{
    final MdrClient client = new MdrClient("localhost", 50051);
    try {
      client.udpateApiStatus("api2", "unstable");
    } finally {
      client.shutdown();
    }
  }


  //
  //  Public instance construction
  //

  /**
   *  @param inHost host
   *  @param inPort port
   */
  public MdrClient(final String inHost, final int inPort) {
    // Channels are secure by default (via SSL/TLS).
    // For the example we disable TLS to avoid needing certificates.
    _channel = ManagedChannelBuilder
        .forAddress(inHost, inPort)
        .usePlaintext(true)
        .build();
    _blockingStub = MdrServiceGrpc.newBlockingStub(_channel);
  }


  //
  //  Public instance methods
  //

  /**
   *  @throws InterruptedException unhandled exception
   */
  public void shutdown() throws InterruptedException {
    _channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**apiservice
   *
   */
  public void udpateApiStatus(final String inApiName, final String inApiStatus) {
    logger.info("call updateApiStatus()");
    final ApiStatus request = ApiStatus.newBuilder().setApiname(inApiName).setStatus(inApiStatus).build();
    Rc rc = _blockingStub.updateApiStatus(request);
    logger.info(String.format("rc(%s)", rc.getRc()));
  }


  //
  //  Private class data
  //

  private static final Logger logger = Logger.getLogger(MdrClient.class.getName());


  //
  //  Private instance data
  //

  private final ManagedChannel _channel;
  private final MdrServiceGrpc.MdrServiceBlockingStub _blockingStub;

}
