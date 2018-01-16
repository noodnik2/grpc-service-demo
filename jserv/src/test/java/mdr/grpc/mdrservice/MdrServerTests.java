package mdr.grpc.mdrservice;

import io.grpc.testing.GrpcServerRule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.logging.Logger;

/**
 *  To test the server, make calls with a real stub using the in-process channel,
 *  and verify behaviors or state changes from the client side.
 */
@RunWith(JUnit4.class)
public class MdrServerTests {


    //
    //  Public instance methods
    //

    /**
     *  Tests the "clear" method
     */
    @Test
    public void testClearMethod() {
        assertUpdateApiStatusResult(0, "clear");
    }

    /**
     *  Tests the "set" method
     */
    @Test
    public void testSetMethod() {
        assertUpdateApiStatusResult(4352, "set:4352");
    }


    //
    //  Private instance methods
    //

    /**
     *  Perform the indicated test case
     *  @param inExpectedRc expected return code
     *  @param inMethodAndStatus method and optional status (separated by a colon)
     */
    private void assertUpdateApiStatusResult(
        final int inExpectedRc,
        @NotNull final String inMethodAndStatus
    ) {

        final String[] methodAndStatusArray = inMethodAndStatus.split(":");
        Assert.assertTrue(methodAndStatusArray.length > 0 && methodAndStatusArray.length < 3);

        // Register a new instance of the service implementation with the in-process server
        TEST_GRPC_SERVICE.getServiceRegistry().addService(new MdrServiceImpl());

        MdrServiceGrpc.MdrServiceBlockingStub blockingStub = MdrServiceGrpc.newBlockingStub(
            TEST_GRPC_SERVICE.getChannel()
        );

        LOGGER.info("call updateApiStatus()");
        final ApiStatus.Builder builder = ApiStatus.newBuilder();
        builder.setMethod(methodAndStatusArray[0]);
        if (methodAndStatusArray.length > 1) {
            builder.setStatus(methodAndStatusArray[1]);
        }
        final ApiStatus request = builder.build();
        final Rc rc = blockingStub.updateApiStatus(request);
        LOGGER.info(String.format("rc(%s)", rc.getRc()));

        Assert.assertEquals(inExpectedRc, rc.getRc());
    }


    //
    //  Public class data
    //

    @Rule   // "public" so JUnit4 can do its magic prior to running a test
    public final GrpcServerRule TEST_GRPC_SERVICE = new GrpcServerRule().directExecutor();


    //
    //  Private class data
    //

    private static final Logger LOGGER = Logger.getLogger(MdrServer.class.getName());

}
