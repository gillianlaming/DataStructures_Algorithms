package studio3;

import java.util.LinkedList;
import java.util.List;

public class UnorderedList<T extends Comparable<T>> implements PriorityQueue<T> {

	public List<T> list;

	public UnorderedList() {
		list = new LinkedList<T>();
	}

	@Override
	public boolean isEmpty() {
		if (list.size() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void insert(T thing) {
		list.add(thing);
	}

	@Override
	public T extractMin() {
		T min = list.get(0);
		for (T t: list) {
			if (t.compareTo(min) < 0 ) {
				min = t;
			}
		}
		list.remove(min);
		return min;


	}
}

