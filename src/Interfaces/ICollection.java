package Interfaces;

public interface ICollection {

	public int size();
	public void clear();
	public boolean add(Object item);
	
	public boolean sortIncrease();
	public boolean sortDecrease();
}
