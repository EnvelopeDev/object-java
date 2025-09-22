package list;

public class List<T> implements InterfaceList<T>
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
	
	private void checkIndex(int index) 
	{
        if(index<0 || index>=sz)
        {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range (list size: " + sz + ")\n");
        }
    }
	
	private void checkIndexForInsert(int index) 
	{
        if(index<0 || index>sz)
        {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range (list size: " + sz + ")\n");
        }
    }
	
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

	@Override
	public int getSize() {return sz;}

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

	@Override
	public boolean isEmpty() 
	{
		if(head!=null) 
		{
			return false;
		}
		return true;
	}

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

	@Override
	public void clear()
	{
		sz=0;
		head=null;
		end=null;
	}

	@Override
	public void print() 
	{
		Node<T> curr = head;
		while(curr!=null) 
		{
			System.out.print(curr.val);
			if(curr.next!=null) 
			{
				System.out.print("\n");
			}
			curr=curr.next;
		}
		System.out.print('\n');
	}

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
