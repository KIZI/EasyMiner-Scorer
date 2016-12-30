package cz.jkuchar.easyminerscorer.utlis.transformation;

import java.util.Map;
import java.util.function.Function;

import cz.jkuchar.easyminerscorer.rules.Item;

/**
 * MapValues transformation 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class MapValues implements Function<Item, Item> {
	String source;
	String target;
	Map<String, String> mapper;

	public MapValues(String source, String target, Map<String, String> mapper) {
		this.source = source;
		this.target = target;
		this.mapper = mapper;
	}

	@Override
	public Item apply(Item item) {
		if (item.containsKey(source) && mapper.containsKey(item.get(source))) {
			item.put(target, mapper.get(item.get(source)));
		}
		return item;
	}
}
