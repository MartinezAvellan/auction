package br.com.quarktech.auction.exceptions;

public class BidNotFoundException extends Exception {

	private static final long serialVersionUID = 5425648583811890928L;
	public BidNotFoundException(String message) {
        super(message);
    }

}