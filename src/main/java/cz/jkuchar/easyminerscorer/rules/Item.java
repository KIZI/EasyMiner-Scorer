package cz.jkuchar.easyminerscorer.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;


/**
 * Item for scoring
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Component
public class Item {

	private Map<String, String> memory;
	private long id;

	public Item() {
		this(0);
	}

	public Item(int id) {
		this.memory = new HashMap<String, String>();
		this.id = id;
	}

	public boolean containsKey(String key) {
		return this.memory.containsKey(key);		
	}
	
	public Set<String> keySet(){
		return this.memory.keySet();
	}
	

	public String get(String key) {
		return this.memory.get(key);
	}

	public void put(String key, String value) {
		this.memory.put(key, value);
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item [memory=" + memory + "]";
	}
	

}
