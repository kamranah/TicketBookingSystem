package com.my.company;

import java.util.List;
import java.util.UUID;

/**
 * SeatHold class
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class SeatHold {
	private Integer seatHoldId;
	private Customer customer;
	private List<Seat> listOfSeats;
	private String seatBookingConfirmation;
	
	public String getSeatBookingConfirmation() {
		return seatBookingConfirmation;
	}

	public void setSeatBookingConfirmation(String seatBookingConfirmation) {
		this.seatBookingConfirmation = seatBookingConfirmation;
	}

	public SeatHold(){
		seatHoldId =  (int) (System.currentTimeMillis() & 0xfffffff);
	}
	
	public List<Seat> getListOfSeats() {
		return listOfSeats;
	}
	public void setListOfSeats(List<Seat> listOfSeats) {
		this.listOfSeats = listOfSeats;
	}


	public Integer getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(Integer seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
