package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetCmd implements ICommand {
	static final String NAME="get";
	
	static final String NullMsg=String.format("(nil)",ICommand.Result.Error.name());
	static final String WrongTypeMsg=String.format("(error) %s Operation against a key holding the wrong kind of value",ICommand.Result.WrongType.name());

	private final Pattern p=Pattern.compile("(\\w+)(.*)");
	@Override
	public String exec(String argstr) {
		String[] args=syntaxParse(argstr);
	
		Object storedVal=MemData.getinstance().get(args[0]);
		return storedVal.toString();
	}


	@Override
	public String[] syntaxParse(String argstr) throws IllegalArgumentException {
		if(argstr==null) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);

		}
		String[] args =null;
		Matcher m=p.matcher(argstr);
		if(m.find()){
			args =new String[2];
			args[0]=m.group(1);
			args[1]=m.group(2);
		}
		if(		args==null 
				||args[0]==null
				||(args[1]!=null &&!args[1].isBlank())) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		return args;
	}
}
