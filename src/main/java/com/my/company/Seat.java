package com.my.company;

/**
 * Seat class
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class Seat {
	private int seatPriority;
	private int row;
	private int seatNumber;
	private Customer reservedByCustomer;
	
	public int getSeatPriority() {
		return seatPriority;
	}

	public void setSeatPriority(int seatPriority) {
		this.seatPriority = seatPriority;
	}

	public Customer getReservedByCustomer() {
		return reservedByCustomer;
	}

	public void setReservedByCustomer(Customer reservedByCustomer) {
		this.reservedByCustomer = reservedByCustomer;
	}

	public Seat(int row, int seatNumber){
		this.row = row;
		this.seatNumber = seatNumber;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	
}
