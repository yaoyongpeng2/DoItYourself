package my.redis.command;

import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

class IncrCmdTest {

	@Test
	public void testKeyHasNumValue() {
		final String key="num";
		final String value="1";
		SetCmd setCmd=new SetCmd();
		try {
			setCmd.exec(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		IncrCmd cmd=new IncrCmd();
		String gotValue=null;
		try {
			gotValue = cmd.exec(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assert(gotValue.equals("(integer) 2"));
		GetCmd getCmd=new GetCmd();
		gotValue=getCmd.exec(key, null);
		assert(gotValue.equals("2"));

	}
	
	@Test
	public void testKeyHasNoValue() {
		final String key="newnum";
		IncrCmd cmd=new IncrCmd();
		String gotValue=null;
		try {
			gotValue = cmd.exec(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assert(gotValue.equals("1"));
		GetCmd getCmd=new GetCmd();
		gotValue=getCmd.exec(key, null);
		assert(gotValue.equals("1"));

	}

	@Test
	public void testExecSyntax1() {
		final String value="my first value";
		IncrCmd cmd=new IncrCmd();
		try {
			cmd.exec(null, value);	//lack key
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			System.out.println("IllegalArgumentException  caught as expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
}
