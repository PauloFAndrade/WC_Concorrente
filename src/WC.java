import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WC {
    // provided as input via command line or prefixed as a constant value (5)
    private int maxCapacity;
    
    private int currentCapacity; 
    
    private boolean currentGender;
    
    private LinkedHashSet<Person> person;
    
    private Lock lock = new ReentrantLock();

	public WC(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		this.currentCapacity = maxCapacity;
		this.currentGender = false;
		this.person = new LinkedHashSet<>();
	}
    
	public boolean isEmpty() {
		return person.isEmpty();
	}
	
	public boolean isFull() {
		return person.size() == maxCapacity;
	}
	
	public boolean currentGender() {
		return currentGender;
	}
	
	public boolean isInWC(Person person) {
		return this.person.contains(person);
	}
	
	public void printWC(boolean gender, int id, boolean finished){
		if(gender) {
			System.out.print("Mulher ");
		}else {
			System.out.print("Homem ");
		}
		if(finished) {
			System.out.print(id + " saiu do");
		}else {
			System.out.print(id + " entrou no");
		}
		System.out.println(" banheiro!");
		System.out.print(this.maxCapacity - this.currentCapacity);
		if((this.maxCapacity - this.currentCapacity) > 1) System.out.println(" pessoas no banheiro!");
		else System.out.println(" pessoa no banheiro!");
		System.out.println();
	}
	
    public void enterPerson(Person person) {
    	this.lock.lock();
		
		if(isEmpty()) {
			currentGender = person.getGender();
		}
		
		if(!isFull() && !this.person.contains(person) && currentGender == person.getGender()){
			this.person.add(person);
			currentCapacity--;
			printWC(person.getGender(),person.getID(),person.getFinished());
		}
		this.lock.unlock();
    }
    
    public void leavePerson(Person person) {
    	this.lock.lock();
    	this.person.remove(person);
		currentCapacity++;
		printWC(person.getGender(),person.getID(),person.getFinished());
		this.lock.unlock();
    }
       
}