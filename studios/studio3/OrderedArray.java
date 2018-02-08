package studio3;

public class OrderedArray<T extends Comparable<T>> implements PriorityQueue<T> {

	public T[] array;
	private int size;
	
	@SuppressWarnings("unchecked")
	public OrderedArray(int maxSize) {
		array = (T[]) new Comparable[maxSize];
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		if (array.length == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void insert(T thing) {
		if (size == 0) {
			array[array.length-1] = thing;
		}
		else {
			//int index = 0;
			for (int i = size; i >= 0; --i) {
				if (thing.compareTo(array[i]) > 0) { //thing is greater than array at i
					//index = i;
					for (int j = i; j >= 0; j--) {
						array [j+1] = array [j];
					}
					array [i] = thing;
					break;
				}
			}
		}
		size = size + 1;
	}
	
	@Override
	public T extractMin() {
		T min = array [size];
//		for (int i = 1; i < size-1; ++i) {
//			array [i-1] = array [i];
//		}
		return min;
		
		
		
	}

}
