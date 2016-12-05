package com.my.company;

import java.util.List;


/**
 * Customer class
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class Customer {
	private int id;
	private String email;
	private List<Seat> lstOfSeats;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Seat> getLstOfSeats() {
		return lstOfSeats;
	}
	public void setLstOfSeats(List<Seat> lstOfSeats) {
		this.lstOfSeats = lstOfSeats;
	}
	
	
}
