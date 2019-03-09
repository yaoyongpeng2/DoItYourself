package my.redis.socket;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpClient {
	private final Logger logger =LoggerFactory.getLogger(this.getClass());
	public String start(String host,int port,String msg) {
		Socket socket=null;
		String returnMsg=null;
		try {
			socket = new Socket(host, port);
			logger.info("client>{}",msg);
			OutputStream os=socket.getOutputStream();
			os.write(msg.getBytes("utf-8"));
			os.write('\n');//!important,because server side calls readline(),which blocks untill a new line read
			os.flush();//write right now
//			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			String readMsg=br.readLine();//bug here.return message may be multi lines
			byte[] b=new byte[1024];
			int read=socket.getInputStream().read(b);
			returnMsg=new String(b,0,read);
			logger.info("client<{}",returnMsg);
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket!=null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return returnMsg;
	}
	public static void main(final String[] args) {
		final String host=args[0];
		final int port=Integer.valueOf(args[1]).intValue();
		final StringBuffer message=new StringBuffer();
		message.append(" ");
		for(int i=2;i<args.length;i++)
			message.append(" "+args[i]);
		new Thread(new Runnable() {

			public void run() {
//				new TcpClient().start("localhost", TcpServer.PORT, "Hello TCP socket!");
				new TcpClient().start(host, port, message.toString());
				
			}
			
		}).start();
	}
}
