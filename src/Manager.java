import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Manager {
	
	private static int idMan = 1;
	private static int idWoman = 1;
	private static int qtyPerson = 10;
    
    public static int getWCMaxCapacity(String[] args) {
        int wcMaxCapacity = 5;
        try {
            int index = 0;
            for(String arg : args) {
                index++;
                if(arg.equals("-maxC")) {
                    wcMaxCapacity = Integer.parseInt(args[index]);
                }
            }
        } catch (IllegalArgumentException error) {
            if(error instanceof NumberFormatException) {
                System.out.println("The argument must be a integer");
                System.exit(-1);
            }
        }
        return wcMaxCapacity;
    }
    
    public static void main(String[] args) {
    	WC wc = new WC(getWCMaxCapacity(args));
    	List<Person> person = new ArrayList<>();
    	Random generator = new Random();
    	    	
    	for (int i = 0; i < qtyPerson; i++) {
            if (generator.nextInt(10)%2 == 0) {
                person.add(new Person(false, wc, idMan));
                idMan++;
            } else {
            	person.add(new Person(true, wc, idWoman));
                idWoman++;
            }
        }
    	
    	person.stream().map((Person) -> new Thread(Person)).forEach((thread) -> {
    		thread.start();
        });
    	
    	person.stream().map((Person) -> new Thread(Person)).forEach((thread) -> {
            try {
            	thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error -> " + e);
            }
        });
	}
    
}
