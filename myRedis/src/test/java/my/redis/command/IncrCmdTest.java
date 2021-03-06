package my.redis.command;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class IncrCmdTest {
	private final Logger logger =LoggerFactory.getLogger(IncrCmdTest.class);

	@Test
	public void testKeyHasNumValue() {
		final String key="num";
		final String value="1";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key+" "+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		IncrCmd cmd=new IncrCmd();
		String gotValue=null;
		try {
			gotValue = cmd.exec(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assert(gotValue.equals("(integer) 2"));
		GetCmd getCmd=new GetCmd();
		gotValue=getCmd.exec(key);
		assert(gotValue.equals("2"));

	}
	
	@Test
	public void testKeyHasNoValue() {
		final String key="newnum";
		IncrCmd cmd=new IncrCmd();
		String gotValue=null;
		try {
			gotValue = cmd.exec(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assert(gotValue.equals("1"));
		GetCmd getCmd=new GetCmd();
		gotValue=getCmd.exec(key);
		assert(gotValue.equals("1"));

	}

	@Test
	public void testExecSyntax1() {
		final String value="my first value";
		IncrCmd cmd=new IncrCmd();
		try {
			cmd.exec(null);	//lack key
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			logger.info("IllegalArgumentException caught. it's expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
			fail(e.getMessage());
//			assert(false);

		}
	}
	
	
}
