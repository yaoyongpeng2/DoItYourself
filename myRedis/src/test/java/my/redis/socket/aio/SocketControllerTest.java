package my.redis.socket.aio;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import my.redis.socket.TcpClient;

class SocketControllerTest {
	static ExecutorService service=null;
	Future<?> f=null;
	final String host="localhost";
	final int port=10009;


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		service=Executors.newCachedThreadPool();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		service.shutdown();
		service.awaitTermination(10, TimeUnit.SECONDS);
	}

	@BeforeEach
	void setUp() throws Exception {
		Thread t=new Thread(()-> {
				try {
					new SocketController().init(host, port);
				} catch (IOException e) {
					e.printStackTrace();
				}
		});
		f=service.submit(t);

	}

	@AfterEach
	void tearDown() throws Exception {
		f.cancel(true);
	}

	@Test
	public void testInit() {
		final String key="myFirstkey";
		final String value="my first value";
		final String setCmdLine="set "+key+" "+value;
		final String getCmdLine="get "+key;
		
		//because SocketController().init()is blocking method,so call it in a new thead
		/*
		SocketController controller=new SocketController();
		try {
			controller.init(host, port);
		} catch (IOException e) {
			
			e.printStackTrace();
			fail(e);
		}
		*/
		TcpClient client=new TcpClient();
		for(int i=0;i<2;i++)
			client.start(host, port, setCmdLine);
		
		String gotValue=client.start(host, port, getCmdLine);
		assert(value.equals(gotValue));

	}

}
