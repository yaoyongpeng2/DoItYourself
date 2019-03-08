package my.redis.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDispatcher {
	static final String UnknownCmdErrorFormat="(error) "+ICommand.Result.Error.name()+"unknown command\'%s\'";
	static final String NotImplementedErrorFormat="(error) "+ICommand.Result.Error.name()+"Not implemented command\'%s\'";
	

//	private Pattern p=Pattern.compile("^\\s*(\\w+)\\s*(\\w+)\\s*(.*)");
	//multi line,because pipeline can send multi-commamds seperated by '\n' 
	private Pattern p=Pattern.compile("(\\w+)(.*)",Pattern.MULTILINE);
	public String handle(String commandLine) {
		if(commandLine==null||commandLine.isBlank())
			return "";//do nothing
		String cmdName=null;
		String args=null;
		ICommand cmd=null;
		Matcher m=p.matcher(commandLine);
		if(m.find()) {//cmd is mandatory
			cmdName=m.group(1).toLowerCase();//redis commands seare case insensitive?
			args=m.group(2).trim();
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
			result=cmd.exec(args);
		} catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
		}
		return result;
	}

}
