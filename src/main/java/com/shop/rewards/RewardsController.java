package com.shop.rewards;


import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 
 * @author Sailaja
 * API URLs :
 * 1. create record : POST : http://localhost:8080/records/
 * {
	"skuId": "101",
	"amount": 300
	}
	2. get all records : GET : http://localhost:8080/records/
	3. get single record by Id GET : http://localhost:8080/records/1
 */

@BasePathAwareController
public class RewardsController {
	@Autowired
	private PurchaseRecordService purchaseRecordService;
	
	
	@RequestMapping(path="records", method=RequestMethod.POST, produces="application/hal+json")
    public ResponseEntity<Object> addTransaction(@RequestBody @Valid final PurchaseRecord purchaseRecord) {
		PurchaseRecord savedPurchaseRecord = purchaseRecordService.save(purchaseRecord);
		savedPurchaseRecord.setLast3MonthRewards(purchaseRecordService.getLast3MonthsRewards());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(savedPurchaseRecord.getId())
                 .toUri();
		 return ResponseEntity.created(location).body(savedPurchaseRecord);
    }
	
	@RequestMapping(path="records/{id}", method=RequestMethod.GET, produces="application/hal+json")
    public ResponseEntity<Object> getRecord(@PathVariable final Long id) {
		Optional<PurchaseRecord> record = purchaseRecordService.getPurchaseRecordRepository().findById(id);
		if(record.isPresent()) {
			PurchaseRecord savedPurchaseRecord = record.get();
			savedPurchaseRecord.setLast3MonthRewards(purchaseRecordService.getLast3MonthsRewards());
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(savedPurchaseRecord.getId())
                 .toUri();
		 return ResponseEntity.created(location).body(savedPurchaseRecord);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
	
	@RequestMapping(path="records/search/findBySkuId", method=RequestMethod.GET, produces="application/hal+json")
    public ResponseEntity<Object> findBySkuId(@RequestParam final String skuId) {
		if(!StringUtils.isEmpty(skuId)) {
			Optional<PurchaseRecord> record = purchaseRecordService.getPurchaseRecordRepository().findBySkuId(skuId);
			if(record.isPresent()) {
				PurchaseRecord savedPurchaseRecord = record.get();
				savedPurchaseRecord.setLast3MonthRewards(purchaseRecordService.getLast3MonthsRewards());
				URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                 .path("/{id}")
	                 .buildAndExpand(savedPurchaseRecord.getId())
	                 .toUri();
			 return ResponseEntity.created(location).body(savedPurchaseRecord);
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
