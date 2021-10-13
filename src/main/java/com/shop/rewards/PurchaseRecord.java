package com.shop.rewards;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PurchaseRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "skuId can't be blank")
	private String skuId;
	
	@NotNull(message = "amount can't be blank")
	private Double amount;
	
	@Temporal(TemporalType.DATE)
	private Date purchaseDate = new Date();

	@Transient
	private Map<String, Integer> last3MonthRewards;
	
	private int rewardPoints;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	@JsonIgnore
	public String getPurchasedMonth() {
		if(getPurchaseDate() != null) {
			LocalDate ld = new java.sql.Date( getPurchaseDate().getTime())
                    .toLocalDate();
			if(ld != null)
				return ld.getMonth().name();
		}
		return null;
	}

	public Map<String, Integer> getLast3MonthRewards() {
		return last3MonthRewards;
	}

	public void setLast3MonthRewards(Map<String, Integer> last3MonthRewards) {
		this.last3MonthRewards = last3MonthRewards;
	}
}
