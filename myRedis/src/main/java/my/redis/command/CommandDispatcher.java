package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDispatcher {
	static final String UnknownCmdErrorFormat="(error) "+ICommand.Result.Error.name()+"unknown command\'%s\'";
	static final String NotImplementedErrorFormat="(error) "+ICommand.Result.Error.name()+"Not implemented command\'%s\'";
	

	private Pattern p=Pattern.compile("^\\s*(\\w+)\\s*(\\w+)\\s*(.*)"); 
//	private Pattern p=Pattern.compile("(\\w+)(.*)"); 
	public String handle(String commandLine) {
		if(commandLine==null||commandLine.isBlank())
			return "";//do nothing
		String cmdName=null;
		String key=null;
		String value=null;
		ICommand cmd=null;
		Matcher m=p.matcher(commandLine);
		if(m.matches()) {//cmd an key is mandatory
			cmdName=m.group(1);
			key=m.group(2);
			value=m.group(3).trim();
		}else {//blank cmdName is checked and excluded
		}

		switch(cmdName) {
		case SetCmd.NAME:
			cmd = new SetCmd();
			break;
		case GetCmd.NAME:
			cmd=new GetCmd();
			break;
		case IncrCmd.NAME:
			cmd=new IncrCmd();
			break;
		default:
			return String.format(NotImplementedErrorFormat, cmdName);
		}
		
		String result;
		try {
			result=cmd.exec(key, value);
		} catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
		}
		return result;
	}

}
