package my.redis.command;

//import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

class GetCmdTest {

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
		gotValue = getCmd.exec(key);
		assert(gotValue.equals(value));
	}
	
	@Test
	public void testExecSyntax1() {
		final String key="myFirstkey";
		final String value="my first value";
		GetCmd getCmd=new GetCmd();
		try {
			getCmd.exec(key+" "+value);	////extra argue here,throw IllegalArgumentException
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			System.out.println("IllegalArgumentException  caught as expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
			assert(false);

		}
	}
	@Test
	public void testExecSyntax2() {
		final String value="my first value";
		GetCmd getCmd=new GetCmd();
		try {
			getCmd.exec(value);	////extra argue here,throw IllegalArgumentException
		}catch(IllegalArgumentException iae) {
			//do nothing,expected
			System.out.println("IllegalArgumentException  caught as expected, just ignore it");
		}catch(Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
			assert(false);

		}

	}

}
