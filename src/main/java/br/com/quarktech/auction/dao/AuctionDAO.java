package br.com.quarktech.auction.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.exceptions.BidFieldsNullException;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.exceptions.GreaterThanZeroAuctionException;
import br.com.quarktech.auction.exceptions.TwoDecimalBidAuctionException;

@Component
@SuppressWarnings("unchecked")
public class AuctionDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Auction> getAllAuctions() {
		final String QUERY = "SELECT a FROM Auction a";
		return em.createQuery(QUERY).getResultList();
	}
	
	public List<Auction> getMinimumAlone() {
		final String QUERY = "SELECT * FROM auction a WHERE a.auction_value = (SELECT MIN(b.auction_value) FROM auction b GROUP BY b.auction_value HAVING COUNT(b.auction_value) = 1 LIMIT 1)";
		return em.createNativeQuery(QUERY, Auction.class).getResultList();

	}
	
	@Transactional
	public void insert(final Auction auction) throws FullAuctionExcpetion, TwoDecimalBidAuctionException, GreaterThanZeroAuctionException, BidFieldsNullException {
		
		if (auction.getName() == null || auction.getAuctionValue() == null) {
			throw new BidFieldsNullException(AuctionConstants.BID_FIELDS_NULL);
		}
		
		if (auction.getAuctionValue().compareTo(BigDecimal.ZERO) == 0) {
			throw new GreaterThanZeroAuctionException(AuctionConstants.BID_GREATER_THAN_ZERO);
		}
		
		if(Math.max(0, auction.getAuctionValue().stripTrailingZeros().scale()) > 2) {
			throw new TwoDecimalBidAuctionException(AuctionConstants.BID_TWO_DECIMAIS);
		}
		
		final List<Auction> all = this.getAllAuctions();
		if(all.size() < 999) {
			em.persist(auction);
			em.flush();
		} else {
			throw new FullAuctionExcpetion(AuctionConstants.FULL_AUCTION);
		}
	}

}