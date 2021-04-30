package br.com.quarktech.auction.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.quarktech.auction.entities.Auction;

@Component
@SuppressWarnings("unchecked")
public class AuctionDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Auction> getAll() {
		final String QUERY = "SELECT a FROM Auction a";
		return em.createQuery(QUERY).getResultList();
	}
	
	public BigDecimal getTotal() {
		final String QUERY = "SELECT SUM(a.value) FROM Auction a";
		BigDecimal singleResult = (BigDecimal) em.createQuery(QUERY).getSingleResult();
		return singleResult.setScale(2, RoundingMode.HALF_UP);
	}
	
	public List<Auction> getMinimumAlone() {
		final String QUERY = "SELECT * FROM auction a WHERE a.auction_value = (SELECT MIN(b.auction_value) FROM auction b GROUP BY b.auction_value HAVING COUNT(b.auction_value) = 1 LIMIT 1)";
		return em.createNativeQuery(QUERY, Auction.class).getResultList();

	}
	
	@Transactional
	public void insert(final Auction auction) {
		final List<Auction> all = this.getAll();
		if(all.size() < 999) {
			em.persist(auction);
			em.flush();
		}
	}

}