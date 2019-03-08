package my.redis.command;

import org.junit.jupiter.api.Test;

class CommandDispatcherTest {

	@Test
	void testHandle() {
		String cmdLine="set myredis my first key value";
		CommandDispatcher dispatcher=new CommandDispatcher();
		String result=dispatcher.handle(cmdLine);
		assert(result==ICommand.Result.OK.name());
	}

}
