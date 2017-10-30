package pasito.staticEnvironment;

public class AlreadyBoundException extends Exception {
    public String log;
    public AlreadyBoundException() {
        log = " já foi declarada.";
    }
    
    @Override
    public String getMessage() {
    	return log;
    }
}
