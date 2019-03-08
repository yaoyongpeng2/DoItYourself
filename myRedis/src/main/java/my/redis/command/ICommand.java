package my.redis.command;

public interface ICommand {
	enum Result {
		OK,Error, WrongType;
	}
	static final String WrongNumberArgsErrorFormat="(error) "+ICommand.Result.Error.name()+" wrong number of arguments for\'%s\' command";

	public String exec(String args);
	public String[] syntaxParse(String argstr) throws IllegalArgumentException;

}
