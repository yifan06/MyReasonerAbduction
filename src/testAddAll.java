import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;


public class testAddAll extends Object {

	/**
	 * @param args
	 */
	private int a;
	private int b;
	
	public testAddAll(int i,int j){
		a=i;
		b=j;
	}
	public int getA(){
		return a;
	}
	public int getB(){
		return b;
	}
	public int hashCode(){
		System.out.println("hello");
		return 0;
	}
	public boolean equals(Object t){
		//System.out.println("hello");
		if(a== ((testAddAll) t).getB()||b== ((testAddAll) t).getA())
			return true;
		else
			return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testAddAll x=new testAddAll(1,2);
		testAddAll y=new testAddAll(4,1);
		Set set=new CopyOnWriteArraySet<testAddAll>();
		Set  set1=new CopyOnWriteArraySet<testAddAll>();
		set.add(x);
		set1.add(y);
		
//		System.out.println(x.equals(y));
		System.out.println(set.addAll(set1));
		
		Iterator<testAddAll> iter=set.iterator();
		while(iter.hasNext())
			System.out.println(iter.next().getA());
		

	}

}
