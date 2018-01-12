package mdr.grpc.mdrservice;

import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

/**
 *  Standard implementation of "Mdr Service"
 *  @author Marty Ross
 */
public class MdrServiceImpl extends MdrServiceGrpc.MdrServiceImplBase {


    //
    //  Public instance methods
    //

    @Override
    public void updateApiStatus(ApiStatus request, StreamObserver<Rc> responseObserver) {

        final String method = request.getMethod();
        final String status = request.getStatus();

        // implement some sort of "testable behavior" for the example...
        final int rc;
        switch(method) {

            case "clear":
                rc = 0;
                break;

            case "set":
                rc = Integer.valueOf(status);
                break;

            default:
                LOGGER.warning(String.format("unrecognized method(%s)", method));
                rc = -1;
                break;

        }

        LOGGER.info(String.format("updateApiStatus(%s, %s) => Rc(%s)", method, status, rc));
        responseObserver.onNext(Rc.newBuilder().setRc(rc).build());
        responseObserver.onCompleted();
    }


    //
    //  Private class data
    //

    private static final Logger LOGGER = Logger.getLogger(MdrServer.class.getName());


}
