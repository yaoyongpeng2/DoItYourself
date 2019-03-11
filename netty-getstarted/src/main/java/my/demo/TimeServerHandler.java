package my.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        //A ChannelFuture represents an I/O operation which has not yet occurred. 
        //It means,any requested operation might not have been performed yet
        //because all operations are asynchronous in Netty
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
//                ctx.close();
            }
        }); // (4)
      //Alternatively, you could simplify the code using a pre-defined listener
       // f.addListener(ChannelFutureListener.CLOSE);
        System.out.println("exit before closing channel...");
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}