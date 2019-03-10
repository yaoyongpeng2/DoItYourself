package my.redis.command;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetCmdTest {
	private final Logger logger =LoggerFactory.getLogger(SetCmdTest.class);

	@Test
	public void testExec() {
		final String key="myFirstkey";
		final String value="my first value";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key+" "+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GetCmd getCmd=new GetCmd();
		String gotValue=null;
		try {
			gotValue = getCmd.exec(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assert(gotValue.equals(value));
	}
	@Test
	public void testExecSyntax1() {
		final String value="my first value";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(null);	//lack key
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			logger.info("IllegalArgumentException caught. it's expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
			fail(e.getMessage());
//			assert(false);
		}
	}
	
	@Test
	public void testExecSyntax2() {
		final String key="myFirstkey";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key);	//lack value
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
