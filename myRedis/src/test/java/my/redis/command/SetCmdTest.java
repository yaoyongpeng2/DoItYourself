package my.redis.command;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

public class SetCmdTest {

	@Test
	public void testExec() {
		final String key="myFirstkey";
		final String value="my first value";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GetCmd getCmd=new GetCmd();
		String gotValue=null;
		try {
			gotValue = getCmd.exec(key, null);
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
			setCmd.exec(null, value);	//lack key
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			System.out.println("IllegalArgumentException  caught as expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testExecSyntax2() {
		final String key="myFirstkey";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key, null);	//lack value
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			System.out.println("IllegalArgumentException  caught as expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
}
