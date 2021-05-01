package br.com.quarktech.auction.exceptions;

public class GreaterThanZeroAuctionException  extends Exception {

	private static final long serialVersionUID = -5055502712159954196L;
	public GreaterThanZeroAuctionException(String message) {
        super(message);
    }
}