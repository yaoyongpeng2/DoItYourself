package my.redis.command;

import java.util.concurrent.ConcurrentHashMap;

public class MemData {

	private ConcurrentHashMap<String, Object> data=new ConcurrentHashMap<String, Object>(1024);
	private MemData() {};
	private static MemData instance=new MemData();
	public static MemData getinstance() {
		if(instance==null) {
			instance=new MemData();
		}
		return instance;
	}
	public Object get(String key) {
		return data.get(key);
	}
	public Object put(String key,Object value) {
		return data.put(key, value);
	}

}
