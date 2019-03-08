package my.redis.socket;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {
	
	public String start(String host,int port,String msg) {
		Socket socket=null;
		String returnMsg=null;
		try {
			socket = new Socket(host, port);
			System.out.println("client>"+msg);
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(msg);
//			bw.newLine();//!important,because server side calls readline(),which blocks untill a new line read
			bw.flush();
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			returnMsg=br.readLine();
			System.out.println("client<"+returnMsg);
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
