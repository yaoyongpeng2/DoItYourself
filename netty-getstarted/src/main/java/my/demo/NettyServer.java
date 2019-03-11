package my.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @see <a href=https://netty.io/wiki/user-guide-for-4.x.html>netty get started</a>
 *
 */
public class NettyServer {
    
    private int port;
    
    public NettyServer(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() {
            	 // (4)The handler specified here will always be evaluated by a newly accepted Channel. 
            	 //The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel. 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new EchoHandler())
                     .addLast(new TimeServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             //when many client connection incoming,put others into queue whose length is set by SO_BACKLOG,
             //default to 50,if not set oset toa value<=0
              .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            //TCP heart-beat. if nodata dent within 2 hours.
            //server sends keepalive to client,and client replies ACK
            //Did you notice option() and childOption()?
            //option() is for the NioServerSocketChannel that accepts incoming connections. 
            //childOption() is for the Channels accepted by the parent ServerChannel, which is NioServerSocketChannel in this case.

    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new NettyServer(port).run();
    }
}
