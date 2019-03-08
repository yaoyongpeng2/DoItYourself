package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncrCmd implements ICommand {
	static final String NAME="incr";
	
	static final String NotIntegerError=String.format("(error) %s value is not an integer",ICommand.Result.Error.name());
	static final String WrongTypeError=String.format("(error) %s Operation against a key holding the wrong kind of value",ICommand.Result.WrongType.name());
	static final String OkMsg="(integer) ";
	private final Pattern cmdP=Pattern.compile("\\s*(\\w+)(.*)");
	private final Pattern numP=Pattern.compile("\\s*(\\d+)\\s*");
	@Override
	public String exec(String argstr) {
		String[] args=syntaxParse(argstr);
		Object storedVal=MemData.getinstance().get(args[0]);
		String result=null;

		if(storedVal==null) {//
			MemData.getinstance().put(args[0], "1");
			return "1";

		}
		if (!(storedVal instanceof String)) {
			result=WrongTypeError;
			
		}else {
			Matcher m=numP.matcher(storedVal.toString());
			if(m.matches()) {
				Integer value1=Integer.valueOf(m.group(1))+1;
				MemData.getinstance().put(args[0], value1.toString());
				result=OkMsg+value1.toString();
			}else {
				result=NotIntegerError;
			}
		}
		return result;
	}
	
	@Override
	public String[] syntaxParse(String argstr) throws IllegalArgumentException {
		if(argstr==null) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);

		}
		String[] args =null;
		Matcher m=cmdP.matcher(argstr);
		if(m.find()){
			args =new String[2];
			args[0]=m.group(1);
			args[1]=m.group(2);
		}
		if(	args==null 
			||args[0]==null
			||args[1]!=null &&!args[1].isBlank()) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		return args;
	}

}
