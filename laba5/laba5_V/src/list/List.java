package list;

/**
 * Class for working with a list of items
 * @param <T> type of list items
 * @author Vadim Ustinov
 * @version 1.0
 */
public class List<T> implements interfaceList<T>
{
	private static class Node<T>
	{
	    T val;
	    Node<T> next;
	    Node(T data) 
	    {
	        this.val = data;
	        this.next = null;
	    }
	}
	
	private Node<T> head;
	private Node<T> end;
	private int sz;
	  
	public List()
	{
	    head = null;
	    end = null;
	    sz=0;
	}
	
	/**
     * Checks if the index is valid for access operations
     * @param index index to check
     * @throws IndexOutOfBoundsException if index is out of range
     */
	private void checkIndex(int index) 
	{
        if(index<0 || index>=sz)
        {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range (list size: " + sz + ")\n");
        }
    }
	
	/**
     * Checks if the index is valid for insert operations
     * @param index index to check
     * @throws IndexOutOfBoundsException if index is out of range
     */
	private void checkIndexForInsert(int index) 
	{
        if(index<0 || index>sz)
        {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range (list size: " + sz + ")\n");
        }
    }
	
	/**
     * Adds an element to the end of the list
     * @param value value to add
     */
	@Override
	public void push_back(T value)
	{
		Node<T> newNode = new Node<>(value);
		if(head==null) 
		{
			head = newNode;
			end = newNode;
			head.next = null;
			end.next = null;
		}
		else 
		{
			end.next = newNode;
			end = newNode;
		}
		sz++;
	}

	/**
     * Adds an element to the beginning of the list
     * @param value value to add
     */
	@Override
	public void push_front(T value) 
	{
		Node<T> newNode = new Node<>(value);
		if(head == null)
		{
			head = newNode;
			end = newNode;
			head.next = null;
			end.next = null;
		}
		else 
		{
			Node<T> temp = head;
			head = newNode;
			head.next = temp;
		}
		sz++;
	}

	/**
     * Inserts an element at the specified position
     * @param value value to insert
     * @param index insertion position
     */
	@Override
	public void insert(T value, int index) 
	{
		checkIndexForInsert(index);
		if(index==0) 
		{
			this.push_front(value);
			return;
		}
		if(index==sz) 
		{
			this.push_back(value);
			return;
		}
		Node<T> newNode = new Node<>(value);
		Node<T> curr = head;
		for(int i=0;i<index-1;i++) 
		{
			curr=curr.next;
		}
		newNode.next = curr.next;
		curr.next = newNode;
		sz++;
	}

	/**
     * Returns the number of elements in the list
     * @return size of the list
     */
	@Override
	public int getSize() {return sz;}

	/**
     * Returns the element at the specified position
     * @param index position of the element
     * @return element at the specified position
     */
	@Override
	public T at(int index) 
	{
		checkIndex(index);
		Node<T> curr = head;
		for(int i=0;i<index;i++) 
		{
			curr = curr.next;
		}
		return curr.val;
	}

	/**
     * Removes the element at the specified position
     * @param index position of the element to remove
     */
	@Override
	public void remove(int index)
	{
		checkIndex(index);
		if(index==0) 
		{
			head = head.next;
			if(head==null) 
			{
				end=null;
			}
			sz--;
			return;
		}
		Node<T> curr = head, temp;
		for(int i=0;i<index-1;i++) 
		{
			curr=curr.next;
		}
		temp = curr.next;
		curr.next = temp.next;
		if(curr.next == null) 
		{
            end = curr;
        }
		temp = null;
		sz--;
	}

	/**
     * Checks if the list is empty
     * @return true if the list is empty, false otherwise
     */
	@Override
	public boolean isEmpty() 
	{
		if(head!=null) 
		{
			return false;
		}
		return true;
	}

	/**
     * Checks if the list contains the specified value
     * @param value value to search for
     * @return true if the value is found, false otherwise
     */
	@Override
	public boolean contains(T value) 
	{
		Node<T> curr = head;
		while(curr!=null)
		{
			if(curr.val==value)
			{
				return true;
			}
			curr=curr.next;
		}
		return false;
	}

	/**
     * Removes all elements from the list
     */
	@Override
	public void clear()
	{
		sz=0;
		head=null;
		end=null;
	}

	/**
     * Replaces the element at the specified position with a new value
     * @param value new value
     * @param index position of the element to replace
     */
	@Override
	public void replace(T value, int index) 
	{
		checkIndex(index);
		Node<T> curr = head;
		for(int i=0;i<index;i++) 
		{
			curr = curr.next;
		}
		curr.val = value;
	}
	
	/**
     * Converts the list to an array of strings
     * @return array of string representations of elements, or null if list is empty
     */
	@Override
	public String[] convToStr()
	{
		if(sz==0) 
		{
			return null;
		}
		String[] res = new String[sz];
		Node<T> curr = head;
		for(int i=0;i<sz;i++) 
		{
			res[i] = curr.val.toString();
			curr = curr.next;
		}
		return res;
	}
}