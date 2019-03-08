package my.redis.command;

public class SetCmd implements ICommand {
	static final String NAME="set";

	@Override
	public String exec(String key, String value){
		if(key==null||value==null) {
			String error =String.format(WrongNumberArgsErrorFormat, NAME);
			throw new IllegalArgumentException(error);
		}
		MemData.getinstance().put(key, value);
		return Result.OK.name();
	}

//	@Override
//	public String syntaxCheck(String key, String value) throws Exception {
//		if(key==null||value==null)
//			return String.format(WrongNumberArgsErrorFormat, NAME);
//		else
//			return null;
//	}

}
