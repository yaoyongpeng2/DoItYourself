package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncrCmd implements ICommand {
	static final String NAME="incr";
	
	static final String NotIntegerError=String.format("(error) %s value is not an integer",ICommand.Result.Error.name());
	static final String WrongTypeError=String.format("(error) %s Operation against a key holding the wrong kind of value",ICommand.Result.WrongType.name());
	static final String OkMsg="(integer) ";
	Pattern p=Pattern.compile("\\s*(\\d+)\\s*");
	@Override
	public String exec(String key, String value) {
		if(key==null||(value!=null)&&value.trim().length()!=0) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		Object storedVal=MemData.getinstance().get(key);
		String result=null;

		if(storedVal==null) {//
			MemData.getinstance().put(key, "1");
			return "1";

		}
		if (!(storedVal instanceof String)) {
			result=WrongTypeError;
			
		}else {
			Matcher m=p.matcher(storedVal.toString());
			if(m.matches()) {
				Integer value1=Integer.valueOf(m.group(1))+1;
				MemData.getinstance().put(key, value1.toString());
				result=OkMsg+value1.toString();
			}else {
				result=NotIntegerError;
			}
		}
		return result;
	}

}
