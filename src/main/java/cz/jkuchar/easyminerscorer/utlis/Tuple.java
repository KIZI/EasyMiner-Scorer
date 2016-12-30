package cz.jkuchar.easyminerscorer.utlis;

public class Tuple<T> {
	private T left;
	private T right;
	public Tuple(T left, T right) {
		super();
		this.left = left;
		this.right = right;
	}
	public T getLeft() {
		return left;
	}
	public T getRight() {
		return right;
	}
	@Override
	public String toString() {
		return "Tuple [left=" + left + ", right=" + right + "]";
	}	
}
