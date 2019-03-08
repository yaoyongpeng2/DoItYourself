package my.redis.command;

public interface ICommand {
	enum Result {
		OK,Error, WrongType;
	}
	static final String WrongNumberArgsErrorFormat="(error) "+ICommand.Result.Error.name()+" wrong number of arguments for\'%s\' command";

	public String exec(String key,String value);
//	public String syntaxCheck(String key,String value) throws IllegalArgumentException;

}
