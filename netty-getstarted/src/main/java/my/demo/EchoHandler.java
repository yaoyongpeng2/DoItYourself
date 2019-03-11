package my.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class EchoHandler extends ChannelInboundHandlerAdapter { // (1)
	private final Logger logger= LoggerFactory.getLogger(EchoHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
 //       ByteBuf buf=(ByteBuf) msg;
//       nbyte[] bytes=new byte[buf.readableBytes()]
//        String msgStr=new String(buf.array());
//       logger.info("server<{}",msgStr); 
    	ctx.write(msg);
    	ctx.flush();
 //FIXME       buf.release(); // (3)
 //       ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}