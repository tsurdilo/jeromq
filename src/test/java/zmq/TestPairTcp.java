package zmq;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestPairTcp
{
    //  Create REQ/ROUTER wiring.

    @Test
    public void testPairTcp() throws IOException
    {
        int port = Utils.findOpenPort();
        Ctx ctx = ZMQ.init(1);
        assertThat(ctx, notNullValue());
        SocketBase sb = ZMQ.socket(ctx, ZMQ.ZMQ_PAIR);
        assertThat(sb, notNullValue());
        boolean brc = ZMQ.bind(sb, "tcp://127.0.0.1:" + port);
        assertThat(brc, is(true));

        SocketBase sc = ZMQ.socket(ctx, ZMQ.ZMQ_PAIR);
        assertThat(sc, notNullValue());
        brc = ZMQ.connect(sc, "tcp://127.0.0.1:" + port);
        assertThat(brc, is(true));

        Helper.bounce(sb, sc);

        //  Tear down the wiring.
        ZMQ.close(sb);
        ZMQ.close(sc);
        ZMQ.term(ctx);

    }
}
