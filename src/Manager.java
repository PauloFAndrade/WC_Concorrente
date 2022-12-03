public class Manager {
    
    public static int getWCMaxCapacity(String[] args) {
        int wcMaxCapacity = -1;
        try {
            int index = 0;
            for(String arg : args) {
                index++;
                if(arg.equals("-maxC")) {
                    wcMaxCapacity = Integer.parseInt(args[index]);
                }
            }
            if(wcMaxCapacity == -1){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException error) {
            if(error instanceof NumberFormatException){
                System.out.println("The argument must be a integer");
                System.exit(-1);
            }else{
                System.out.println("You have to pass the maximum capacity as argument using -C");
                System.exit(-1);
            }
        }
        return wcMaxCapacity;
    }
    
}
