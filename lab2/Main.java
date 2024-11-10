import java.util.*;

import static org.junit.Assert.*;

public class Main {
    public static <Iterator> void main(String[] args) {
        //Test0: Empty List
        MyLinkedList<Integer> lstInt = new MyLinkedList<>();
        assertEquals(0, lstInt.size());
        //lstInt.remove() ;//-- NPE exception

        //Test1: Singleton
        lstInt.add(2);
        assertEquals(new Integer(2), lstInt.get(0));
        assertEquals(1, lstInt.size());
        lstInt.remove();
        assertTrue(lstInt.isEmpty());


        MyLinkedList<String> lstStr = new MyLinkedList<>();
        lstStr.add("Arseniy");
        assertEquals("Arseniy", lstStr.get(0));
        lstStr.remove(0);
        assertTrue(lstStr.isEmpty());

        lstStr.add("Arseniy");
        assertEquals("Arseniy", lstStr.get(0));
        lstStr.set(0, "Gogo");
        assertEquals("Gogo", lstStr.get(0));

        //Test3: A lot of elems
        MyLinkedList<Double> lstDbl = new MyLinkedList<>();
        for (int i = 0; i < 10; i++) {
            lstDbl.add(((double) i) / 2);
        }

        assertEquals(10, lstDbl.size());
        Double[] curArr = lstDbl.toArray(new Double[0]);
        Double[] testArr = {0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5};
        assertArrayEquals(testArr, curArr);

        for (int i = 0; i < 10; i++) {
            assertEquals(new Double(((double) i) / 2), lstDbl.get(i));
        }
        lstDbl.set(2, 9.0);
        assertEquals(new Double(9.0), lstDbl.get(2));
        lstDbl.remove(2);
        assertEquals(new Double(1.5), lstDbl.get(2));
        assertEquals(new Double(0.5), lstDbl.get(1));
        assertEquals(9, lstDbl.size());
        int n = lstDbl.size();
        for (int i = 0; i < n; i++) {
            lstDbl.remove();
        }

        assertTrue(lstDbl.isEmpty());

        //Test5: more elems
        for (int i = 0; i < 100; i++) {
            lstDbl.add(((double) i) / 2);
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(new Double(((double) i) / 2), lstDbl.get(i));
        }
        assertEquals(100, lstDbl.size());

        MyLinkedList<Integer> linkedList = new MyLinkedList<>();

        // Inserting some Integer values to our LinkedList
        linkedList.add(5);
        linkedList.add(100);
        linkedList.add(41);
        linkedList.add(40);
        linkedList.add(7);
        var it = linkedList.iterator();

        //test 6: iterators
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // Create a ListIterator starting at index 1
        MyLinkedList.MyListIterator iterator = (MyLinkedList.MyListIterator) list.listIterator(1);
        iterator.set("X");
        assertEquals("X", list.get(1));
        iterator.previous();
        LinkedList<String> oList = new LinkedList<>();
        oList.add("A");
        oList.add("X");
        oList.add("C");
        ListIterator oIter = oList.listIterator(0);

        assertEquals(iterator.next(), oIter.next());
        assertEquals(iterator.next(), oIter.next());
        assertEquals(iterator.previous(), oIter.previous());
        assertEquals(iterator.previous(), oIter.previous());

        MyLinkedList<Double> singleD = new MyLinkedList<>();
        singleD.add(0.4);
        MyLinkedList.MyListIterator iterD = (MyLinkedList.MyListIterator) singleD.listIterator();
        assertEquals(false, iterD.hasNext());
        assertEquals(false, iterD.hasPrevious());

        //test7: Index
        //linkedList<int> 5 100 41 40
        MyLinkedList.MyListIterator intIter = (MyLinkedList.MyListIterator) linkedList.listIterator(1);
        assertEquals(5, intIter.previous());
        assertEquals(1, intIter.nextIndex());
        intIter.next();
        assertEquals(0, intIter.previousIndex());
        intIter.next();
        assertEquals(2, intIter.nextIndex());


        //test 8a: addAll
        MyLinkedList<Integer> newIntList = new MyLinkedList<>();
        Collection<Integer> toAdd = Arrays.asList(1, 2, 3, 4);

        assertTrue(newIntList.addAll(toAdd));
        for (int i = 1; i < newIntList.size() + 1; i++) {
            assertEquals(new Integer(i), newIntList.get(i - 1));
        }

        //test 8b: removeall
        Collection<Integer> toRemove = Arrays.asList(2, 4);

        assertTrue(newIntList.removeAll(toRemove));
        assertEquals(new Integer(1), newIntList.get(0));
        assertEquals(new Integer(3), newIntList.get(1));
        assertEquals(2, newIntList.size());

        Collection<Integer> toRemove2 = new ArrayList<>();
        assertTrue(newIntList.removeAll(toRemove));
        assertEquals(new Integer(1), newIntList.get(0));
        assertEquals(new Integer(3), newIntList.get(1));
        assertEquals(2, newIntList.size());

        //test 8c:

        MyLinkedList<String> newStrList = new MyLinkedList<>();
        newStrList.add("2");
        newStrList.add("3");

        Collection<String> toRet = Arrays.asList("1", "2", "4");


        assertTrue(newStrList.retainAll(toRet));

        assertEquals("2", newStrList.get(0));
        assertEquals(1, newStrList.size());
        Collection<String> toRet0 = Arrays.asList();
        assertFalse(newStrList.retainAll(toRet));
        assertEquals("2", newStrList.get(0));
        assertEquals(1, newStrList.size());

        //test 9:Linked List Behavior
        MyLinkedList<Integer> myList = new MyLinkedList<>();
        LinkedList<Integer> javaList = new LinkedList<>();

        // Add elements to both lists
        List<Integer> elements = Arrays.asList(1, 2, 3, 4, 5);
        myList.addAll(elements);
        javaList.addAll(elements);

        // Test basic operations (add, get, size)
        assertEquals(myList.size(), javaList.size());
        for (int i = 0; i < myList.size(); i++) {
            assertEquals(myList.get(i), javaList.get(i));
        }
    }


}
