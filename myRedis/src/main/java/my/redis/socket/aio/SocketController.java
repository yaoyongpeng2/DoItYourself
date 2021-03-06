package my.redis.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.redis.command.CommandDispatcher;

public class SocketController {
	private final Logger logger =LoggerFactory.getLogger(SocketController.class);

    public static void main(String[] args) throws IOException {  
    	SocketController controller = new SocketController();  
    	controller.init("localhost", 10008);  
    }  
  
    public void init(String host, int port) throws IOException {  
        //ChannelGroup used to manage shared resources
    	//use two groups?one for connection, one for read/write?
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10);  
        final AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open(group);  
        //set socket option  
        channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);  
        channel.setOption(StandardSocketOptions.SO_RCVBUF, 16 * 1024);  
        //bind to host port
        channel.bind(new InetSocketAddress(host, port));  
        logger.info("Listening on {}:{}",host, port);  
        // output provider  
        logger.info("Channel Provider : {}", channel.provider()); 
        
         //wait for connection,and register CompletionHandlerto handle data after kernel finishes operation. 
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() { 
        	//use channel.read() to read one after another.
            final ByteBuffer buffer = ByteBuffer.allocate(1024);  
  
            public void completed(AsynchronousSocketChannel result, Object attachment) {  
           	String channelInfo=null;
        		try {
        			channelInfo = result.getRemoteAddress().toString();
        		} catch (IOException e1) {
        			e1.printStackTrace();
        		}
                logger.info("connection from channel:{}",channelInfo);  
               CommandDispatcher dispatcher=new CommandDispatcher();
                buffer.clear();  
                try {  
                    //read data from socket to buffer  
                    int read=result.read(buffer).get();  
                    buffer.flip(); 
//                    String commandLine=new String(buffer.array()).trim();
                    String commandLine=new String(buffer.array(),0,read).trim();
                    logger.info("server< {}",commandLine);
                    String handleResult=dispatcher.handle(commandLine);
//                    buffer.flip();
//                    buffer.clear();
                    ByteBuffer resultbuffer=ByteBuffer.wrap(handleResult.getBytes());
//                    resultbuffer.flip();
                     
                    //return info to client 
                    Future<Integer> f=result.write(resultbuffer);
                    int written=f.get();
                    logger.info("server>{},{} bytes written",handleResult,written);
                    resultbuffer.flip();  
                    buffer.flip();
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                } catch (ExecutionException e) {  
                    e.printStackTrace();  
                } finally {  
                    try {  
                        //关闭处理完的socket，并重新调用accept等待新的连接  
                        result.close();  
                        channel.accept(null, this);  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
  
            public void failed(Throwable exc, Object attachment) {  
                System.out.print("Server failed...." + exc.getCause());  
            }  
        }); 
  
        //因为AIO不会阻塞调用进程，因此必须在主进程阻塞，才能保持进程存活。  
        try {  
            Thread.sleep(Integer.MAX_VALUE);  //can be Future.cancel(true) to intrrupt, throws an InterruptedException
        } catch (InterruptedException e) { 
//        	System.out.println(e.getMessage());
        	logger.info("",e.getMessage());
//            e.printStackTrace();  
        }  
    }  
}  