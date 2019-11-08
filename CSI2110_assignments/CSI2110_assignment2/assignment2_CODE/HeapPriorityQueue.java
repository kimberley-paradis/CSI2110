/**
 * Array Heap implimentation of a priority queue
 * @author Lachlan Plant
 * Student: Kimberley Paradis - 8569079
 */
public class HeapPriorityQueue <K extends Comparable, V> implements PriorityQueue <K, V> {

	private Entry [] storage; //The Heap itself in array form
	private int tail;    //Index of last element in the heap

	/**
	 * Default constructor
	 */
	public HeapPriorityQueue() {
		this(100);
	}


	/**
	 * HeapPriorityQueue constructor with max storage of size elements
	 */
	public HeapPriorityQueue( int size ) {
		storage = new Entry [ size ];
		tail    = -1;
	}


	public HeapPriorityQueue(K[] keys, V[] values)
 {
     super();
     for (int j=0; j < Math.min(keys.length, values.length); j++)
       heap.add(new PQEntry<>(keys[j], values[j]));
     heapify();
   }

	/**
	 * Returns the number of items in the priority queue.
	 * O(1)
	 * @return number of items
	 */
	public int size() {
		return tail + 1;
	} /* size */


	/**
	 * Tests whether the priority queue is empty.
	 * O(1)
	 * @return true if the priority queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return tail < 0;
	} /* isEmpty */


	/**
	 * Inserts a key-value pair and returns the entry created.
	 * O(log(n))
	 * @param key     the key of the new entry
	 * @param value   the associated value of the new entry
	 * @param ref     a reference to the associated entry in the othe heap
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException if the heap is full
	 */
	public Entry <K, V>insert( K key, V value) throws IllegalArgumentException {
		if( tail == storage.length - 1 )
			throw new IllegalArgumentException ( "Heap Overflow" );

		Entry <K, V>    e = new Entry <> ( key, value );
		storage [ ++tail ] = e;
		e.setIndex(tail);
		upHeap ( tail );
		return e;
	} /* insert */


	/**
	 * Returns (but does not remove) an entry with minimal key.
	 * O(1)
	 * @return entry having a minimal key (or null if empty)
	 */
	public Entry <K, V> min() {
		if( isEmpty() )
			return null;
		return storage [ 0 ];
	} /* min */


	/**
	 * Removes and returns an entry with minimal key.
	 * O(log(n))
	 * @return the removed entry (or null if empty)
	 */
	public Entry <K, V> removeMin() {
		if( isEmpty() )
			return null;

		Entry <K, V>    ret = storage [ 0 ];

		if( tail == 0 ) {
			tail          = -1;
			storage [ 0 ] = null;
			return ret;
		}

		storage [ 0 ]      = storage [ tail ];
		storage [ tail-- ] = null;

		downHeap ( 0 );

		return ret;
	} /* removeMin */


	public BinaryNode removeMax( Comparable x, BinaryNode t ) {
			if( t == null )
					t = new BinaryNode( x );
			else if( x.compareTo( t.element ) < 0 )
					t.left = removeMax( x, t.left );
			else if( x.compareTo( t.element ) > 0 )
					t.right = RemoveMax( x, t.right );
			else
					throw new DuplicateItemException( x.toString( ) );
			return t;
	}

	public BinaryNode removeMin( Comparable x, BinaryNode t ) {
		if( t == null )
				throw new ItemNotFoundException( x.toString( ) );
		if( x.compareTo( t.element ) < 0 )
				t.left = removeMin( x, t.left );
		else if( x.compareTo( t.element ) > 0 )
				t.right = removeMin( x, t.right );
		else if( t.left != null && t.right != null )
		{
				t.element = findMin( t.right ).element;
				t.right = removeMin( t.right );
		} else
				t = ( t.left != null ) ? t.left : t.right;
		return t;
}

	/**
	 * Algorithm to place element after insertion at the tail.
	 * O(log(n))
	 */
	private void upHeap( int location ) {
		if( location == 0 ) return;

		int    parent = parent ( location );

		if( storage [ parent ].key.compareTo ( storage [ location ].key ) > 0 ) {
			swap ( location, parent );
			upHeap ( parent );
		}
	} /* upHeap */


	/**
	 * Algorithm to place element after removal of root and tail element placed at root.
	 * O(log(n))
	 */
	private void downHeap( int location ) {
		int    left  = (location * 2) + 1;
		int    right = (location * 2) + 2;

		//Both children null or out of bound
		if( left > tail ) return;

		//left in right out;
		if( left == tail ) {
			if( storage [ location ].key.compareTo ( storage [ left ].key ) > 0 )
				swap ( location, left );
			return;
		}

		int    toSwap = (storage [ left ].key.compareTo ( storage [ right ].key ) < 0) ?
		                left : right;

		if( storage [ location ].key.compareTo ( storage [ toSwap ].key ) > 0 ) {
			swap ( location, toSwap );
			downHeap ( toSwap );
		}
	} /* downHeap */


	/**
	 * Find parent of a given location,
	 * Parent of the root is the root
	 * O(1)
	 */
	private int parent( int location ) {
		return (location - 1) / 2;
	} /* parent */


	/**
	 * Inplace swap of 2 elements, assumes locations are in array
	 * O(1)
	 */
	private void swap( int location1, int location2 ) {
		Entry <K, V>    temp = storage [ location1 ];
		storage [ location1 ] = storage [ location2 ];
		storage [ location2 ] = temp;

		storage[location1].index= location1;
		storage[location2].index= location2;
	} /* swap */

    public void print() {

		for (Entry<K,V> e : storage)
		  System.out.println ( "(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), " );
	}
}
