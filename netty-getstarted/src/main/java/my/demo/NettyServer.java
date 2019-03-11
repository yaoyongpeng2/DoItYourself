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
                     ch.pipeline().addLast(new EchoServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             //用来初始化服务端可连接队列，多个客户端来的时候，
             //服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
             //如果未设置或所设置的值小于1，Java将使用默认值50
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）
            //并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活
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
