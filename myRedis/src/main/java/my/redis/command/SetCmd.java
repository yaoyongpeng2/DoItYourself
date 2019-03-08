package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetCmd implements ICommand {
	static final String NAME="set";

//	private final Pattern p=Pattern.compile("(\\w+)\\s*(\\w+)\\s*");
	private final Pattern p=Pattern.compile("(\\w+)\\s*(.*)");//blanks are part of value,if in the middle.
	@Override
	public String exec(String argstr){
		String[] args=syntaxParse(argstr);
		MemData.getinstance().put(args[0],args[1]);
		return Result.OK.name();
	}

	@Override
	public String[] syntaxParse(String argstr) throws IllegalArgumentException {
		if(argstr==null) {
			String error =String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		String[] args =null;
		Matcher m=p.matcher(argstr);
		if(m.find()){
			args =new String[2];
			args[0]=m.group(1);
			args[1]=m.group(2);
		}
		if(	args==null||args[0]==null||(args[1]==null|| args[1].isBlank())) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		return args;
	}


}
