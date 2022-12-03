import java.util.Random;

public class Person implements Runnable {
    // true: woman, false: man
    private boolean gender;
    
    //
    private WC wc;
    
    // person's identifier
    private int id;
    
    // time in seconds
    // it is random and different with each execution of the program
    private int time;
    
    private boolean wantUseWC = true;
    
    private Random generator = new Random();
    
    public Person(boolean gender, WC wc, int id) {
		this.gender = gender;
		this.wc = wc;
		this.id = id;
	}
    
    
    
    @Override
	public void run() {
    	while(this.wantUseWC == true) {
    		try {
				Thread.sleep(25);
				if (((this.wc.currentGender() == this.getGender())|| this.wc.isEmpty())
	                    && !this.wc.isFull()
	                    && !this.wc.isInWC(this)) {
	                this.enterWC();
	                this.leaveWC();
	            }
			} catch (InterruptedException e) {
				System.out.println("Error -> " + e);
			}
    	}
	}

	public void enterWC(){
    	this.wc.enterPerson(this);
    	if(this.wc.isInWC(this)) {
    		try {
				Thread.sleep((generator.nextInt(5) + 1)*1000);
			} catch (InterruptedException e) {
				System.out.println("Error -> " + e);
			}
    	}
    }
    
    public void leaveWC() {
    	this.wc.leavePerson(this);
    	this.wantUseWC = false;
    }

	public boolean getGender() {
    	return gender;
    }
    
    public int getID() {
    	return id;
    }
}
