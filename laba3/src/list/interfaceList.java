package list;

public interface interfaceList<T> 
{
	void push_back(T value);
	void push_front(T value);
	void insert(T value, int index);
	int getSize();
	void replace(T value, int index);
	T at(int index);
	void remove(int index);
	boolean isEmpty();
	boolean contains(T value);
	void clear();
	void print();
	String[] convToStr();
}
