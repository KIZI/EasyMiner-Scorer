package cz.jkuchar.easyminerscorer.utlis.transformation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import cz.jkuchar.easyminerscorer.rules.Item;

// http://www.dmg.org/v4-2-1/Transformations.html

/**
 * Transformation Builder
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
public class TransformationBuilder {

	public static Function<Item, Item> mapValues(String source, String target,
			Map<String, String> mapper) {
		return item -> {
			Item tmp = new Item();
			if (item.containsKey(source)
					&& mapper.containsKey(item.get(source))) {
				tmp.put(target, mapper.get(item.get(source)));
			}
			return tmp;
		};
	}

	public static Function<Item, Item> discretize(String source, String target,
			List<Function<String, String>> mapper) {
		return item -> {
			Item tmp = new Item();
			if (item.containsKey(source)) {
				for (Function<String, String> t : mapper) {
					String out = t.apply(item.get(source));
					if (out != null) {
						tmp.put(target, out);
					}
				}
			}
			return tmp;
		};
	}

	public static Item transform(Item item,
			List<Function<Item, Item>> transformations) {
		Item result = new Item();
		for (Function<Item, Item> t : transformations) {
			Item tmp = t.apply(item);
			for (String key : tmp.keySet()) {
				result.put(key, tmp.get(key));
			}
		}
		for (String key : result.keySet()) {
			item.put(key, result.get(key));
		}
		return item;
	}

}
