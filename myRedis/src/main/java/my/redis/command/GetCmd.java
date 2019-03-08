package my.redis.command;

public class GetCmd implements ICommand {
	static final String NAME="get";
	
	static final String NullMsg=String.format("(nil)",ICommand.Result.Error.name());
	static final String WrongTypeMsg=String.format("(error) %s Operation against a key holding the wrong kind of value",ICommand.Result.WrongType.name());

	@Override
	public String exec(String key, String value) {
		if(key==null||(value!=null)&&value.trim().length()!=0) {
			String error=String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}

		Object storedVal=MemData.getinstance().get(key);
		return storedVal.toString();
	}

}
