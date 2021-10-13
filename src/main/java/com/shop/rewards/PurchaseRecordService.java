package com.shop.rewards;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseRecordService {
	Logger logger = LogManager.getLogger(PurchaseRecordService.class);
	private static final String TOTAL_KEY = "total";

	@Autowired
	private PurchaseRecordRepository purchaseRecordRepository;
	
	@Transactional
	public PurchaseRecord save(PurchaseRecord purchaseRecord) {
		if(purchaseRecord != null) {
			purchaseRecord.setRewardPoints(getRewards(purchaseRecord.getAmount()));
			return purchaseRecordRepository.save(purchaseRecord);
		}
		return purchaseRecord;
	}
	
	@Transactional
	public Map<String, Integer> getLast3MonthsRewards(){
		LocalDate today = LocalDate.now();
		LocalDate oldDate = today.minusMonths(2).withDayOfMonth(1);
		if(logger.isDebugEnabled())
			logger.debug("today:" + today + ",oldDate:" + oldDate);
		Stream<PurchaseRecord> records = purchaseRecordRepository.findByPurchaseDateBetween(java.sql.Date.valueOf(oldDate), java.sql.Date.valueOf(today));
		Map<String, Integer>  last3MonthsRewards = null;
		if(records != null) {
			last3MonthsRewards = records.collect(Collectors.groupingBy(PurchaseRecord::getPurchasedMonth, 
					Collectors.summingInt(PurchaseRecord::getRewardPoints)));
			if(last3MonthsRewards != null)
				last3MonthsRewards.put(TOTAL_KEY, last3MonthsRewards.values().stream().mapToInt(Integer::intValue).sum());
		}
		return last3MonthsRewards;
	}
	
	private int getRewards(double amount) {
		int rewards = 0;
		int above100 = (int)amount - 100;
		if(above100 > 0) {
			rewards = above100 * 2 + 50;
		}else {
			int above50 = (int)amount - 50;
			if(above50 > 0) {
				rewards = above50;
			}
		}
		return rewards;
	}

	public PurchaseRecordRepository getPurchaseRecordRepository() {
		return purchaseRecordRepository;
	}

	public void setPurchaseRecordRepository(PurchaseRecordRepository purchaseRecordRepository) {
		this.purchaseRecordRepository = purchaseRecordRepository;
	}
}
