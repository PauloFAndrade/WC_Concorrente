import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Person implements Runnable {
    // true: woman, false: man
    private boolean gender;
    
    private WC wc;
    
    // person identifier
    private int id;
    
    private boolean wantUseWC = true;
    
    private boolean finishWC = false;
    
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
				Thread.sleep(500);
				if (((this.wc.currentGender() == this.getGender())|| this.wc.isEmpty())
	                    && !this.wc.isFull()
	                    && !this.wc.isInWC(this)) {
	                this.enterWC();
	                if(this.finishWC) {
		                this.leaveWC();
	                }
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
    			TimeUnit.MILLISECONDS.sleep((generator.nextInt(5) + 1)*1000);
    			this.finishWC = true;
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
    
    public boolean getFinished() {
    	return finishWC;
    }
    
    public boolean getwantUseWC() {
    	return wantUseWC;
    }
}
