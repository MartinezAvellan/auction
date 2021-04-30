package br.com.quarktech.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarktech.auction.dao.AuctionDAO;
import br.com.quarktech.auction.entities.Auction;

@Service
public class AuctionService {
	
	@Autowired
	private AuctionDAO dao;
	
	public Auction getWinner() {
		final List<Auction> minimumAlone = dao.getMinimumAlone();
		if (minimumAlone.size() >= 1) {
			return minimumAlone.get(0);
		}
		return null;
	}
	

}