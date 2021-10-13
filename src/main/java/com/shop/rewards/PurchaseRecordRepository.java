package com.shop.rewards;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "records", path = "records")
public interface PurchaseRecordRepository extends PagingAndSortingRepository<PurchaseRecord, Long> {
	/*@Query("select pr from PurchaseRecord pr where purchaseDate BETWEEN :startDate AND :endDate")
	Stream<PurchaseRecord> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);*/
	
	Stream<PurchaseRecord> findByPurchaseDateBetween(Date startDate, Date endDate);
	
	Optional<PurchaseRecord> findBySkuId(String skuId);
}
