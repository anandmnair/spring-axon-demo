package con.anand.axon.demo.account.exception;

public class OverdraftLimitExceedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8868186288845459785L;

	public OverdraftLimitExceedException(String messaage) {
		super(messaage);
	}
	
}
