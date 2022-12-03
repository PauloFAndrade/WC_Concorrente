import java.util.LinkedHashSet;
import java.util.concurrent.Semaphore;

public class WC {
    // provided as input via command line or prefixed as a constant value (5)
    private int maxCapacity;
    
    //
    private int currentCapacity; 
    
    //
    private boolean currentGender;
    
    //
    private LinkedHashSet<Person> person;
    
    //
    private Semaphore semaphore;
    
    //
    private Semaphore leftMutex;
    
    //
    private Semaphore enterMutex;

	public WC(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		this.currentCapacity = maxCapacity;
		this.currentGender = false;
		this.person = new LinkedHashSet<>();
		this.semaphore = new Semaphore(this.maxCapacity, true);
		this.leftMutex = new Semaphore(1, true);
		this.enterMutex = new Semaphore(1, true);
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
	
    public void enterPerson(Person person) {
    	try {
            this.semaphore.acquire();
            
            if(isEmpty()) {
            	currentGender = person.getGender();
            }
            
            if(!isFull() && !this.person.contains(person) && currentGender == person.getGender()){
            	enterMutex.acquire();
            	
            	if(this.person.add(person)){
            		boolean gender = person.getGender();
            		currentCapacity--;
            		if(gender == false) {
            			System.out.println("Homem " + person.getID() + " entrou no banheiro! ("+(this.maxCapacity - this.currentCapacity) + " pessoas no banheiro!)");
            		}else {
            			System.out.println("Mulher " + person.getID() + " entrou no banheiro! ("+(this.maxCapacity - this.currentCapacity) + " pessoas no banheiro!)");
            		}
            		this.enterMutex.release();
            		//currentCapacity--;
            		//if((this.maxCapacity - this.currentCapacity) > 1) {
            		//	System.out.println("Atualmente tem " + (this.maxCapacity - this.currentCapacity) + " pessoas no banheiro!");
            		//}else {
            		//	System.out.println("Atualmente tem " + (this.maxCapacity - this.currentCapacity) + " pessoas no banheiro!");
            		//}
            	}
            }
        } catch (InterruptedException e) {
            System.out.println("Error -> " + e);
        }
    }
    
    public void leavePerson(Person person) {
    	this.semaphore.release();
    	try {
			this.leftMutex.acquire();
			if(this.person.remove(person)) {
				boolean gender = person.getGender();
        		if(gender == false) {
        			System.out.println("Homem " + person.getID() + " saiu do banheiro!");
        		}else {
        			System.out.println("Mulher " + person.getID() + " saiu do banheiro!");
        		}
        		this.leftMutex.release();
        		
        		currentCapacity++;
        		System.out.println("Atualmente tem " + (this.maxCapacity - this.currentCapacity) + " pessoas no banheiro!");
			}
		} catch (InterruptedException e) { 
			System.out.println("Error -> " + e);
		}
    }
    
    
}