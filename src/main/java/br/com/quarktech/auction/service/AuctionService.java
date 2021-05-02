package br.com.quarktech.auction.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.dao.AuctionDAO;
import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.exceptions.BidFieldsNullException;
import br.com.quarktech.auction.exceptions.BidNotFoundException;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.exceptions.GreaterThanZeroAuctionException;
import br.com.quarktech.auction.exceptions.TwoDecimalBidAuctionException;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@Service
public class AuctionService implements AuctionServiceInterface {
	
	@Autowired
	private AuctionDAO dao;
	
	@Override
	public void setAuction(final AuctionRequestDTO requestDTO) throws FullAuctionExcpetion, TwoDecimalBidAuctionException, GreaterThanZeroAuctionException, BidFieldsNullException {
		final Auction auction = new Auction();
		auction.setName(requestDTO.getName());
		auction.setAuctionValue(requestDTO.getValue());
		dao.insert(auction);
	}
	
	@Override
	public Auction getBidWinnerDB() throws BidNotFoundException {
		final List<Auction> minimumAlone = dao.getMinimumAlone();
		if (minimumAlone.size() >= 1) {
			return minimumAlone.get(0);
		} else {
			throw new BidNotFoundException(AuctionConstants.BID_NOT_FOUND);
		}
	}
	
	@Override
	public Auction getBidWinnerMap() throws BidNotFoundException {
		final Auction auction = new Auction();
		final Map<String, BigDecimal> map = new HashMap<>();
		final List<Auction> allAuctions = dao.getAllAuctions();
		for (final Auction a : allAuctions) {
			map.put(a.getName(), a.getAuctionValue());
		}
		this.getRemoveDuplicatedBid(map);
		final Entry<String, BigDecimal> minimum = this.getMinimumBid(map);
		if(minimum == null) {
			throw new BidNotFoundException(AuctionConstants.BID_NOT_FOUND);
		}
		auction.setName(minimum.getKey());
		auction.setAuctionValue(minimum.getValue());
		return auction;
	}
	
	private void getRemoveDuplicatedBid(final Map<String, BigDecimal> map) {
        final HashMap<BigDecimal, List<String>> valueToKeyMapCounter = new HashMap<>();
        for (final Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            if (valueToKeyMapCounter.containsKey(entry.getValue())) {
                valueToKeyMapCounter.get(entry.getValue()).add(entry.getKey());
            } else {
                final List<String> keys = new ArrayList<>();
                keys.add(entry.getKey());
                valueToKeyMapCounter.put(entry.getValue(), keys);
            }
        }
        for (Map.Entry<BigDecimal, List<String>> counterEntry : valueToKeyMapCounter.entrySet()) {
            if (counterEntry.getValue().size() > 1) {
                for(final String value: counterEntry.getValue()) {
                	map.remove(value);
                }
            }
        }
	}
	
	private Entry<String, BigDecimal> getMinimumBid(final Map<String, BigDecimal> map) {
		Entry<String, BigDecimal> min = null;
		for (Entry<String, BigDecimal> entry : map.entrySet()) {
		    if (min == null || min.getValue().compareTo(entry.getValue()) == 1) {
		        min = entry;
		    }
		}
		return min;
	}

}