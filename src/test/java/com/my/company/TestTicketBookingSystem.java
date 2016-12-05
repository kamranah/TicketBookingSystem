package com.my.company;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Test
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class TestTicketBookingSystem {
	private TicketServiceImpl ticketService;
	private int row = 30;
	private int seatsInEachRow = 10;
	
    @Before
	public void setUp(){
		ticketService = new TicketServiceImpl(row, seatsInEachRow);
		ticketService.setHoldTimeMins(1);
	}
	
	@Test
	public void testNumberOfAvailableSeats() {
		
		assertEquals(row * seatsInEachRow, ticketService.numOfSeatsAvailable());
	}

	@Test
	public void testSeatHoldServiceWithMoreThenAvailableSeats() {
		String customerEmail = "kamran@programmer.com";
		int numberOfRequestedSeats = 1000;
		assertNull(ticketService.findAndHoldSeats(numberOfRequestedSeats, customerEmail));
	}

	@Test
	public void testSeatHoldServiceWithSomeSeatsAndLettingThemClearBack() {
		String customerEmail = "kamran@programmer.com";
		int numberOfRequestedSeats = 10;
		int totalSeats = row * seatsInEachRow;
		
		SeatHold seatHold = ticketService.findAndHoldSeats(numberOfRequestedSeats, customerEmail);
		
		assertEquals(customerEmail, seatHold.getCustomer().getEmail());
		assertEquals(numberOfRequestedSeats, seatHold.getListOfSeats().size());
		assertEquals(totalSeats - numberOfRequestedSeats, ticketService.numOfSeatsAvailable());
		
		assertEquals(1, ticketService.getSeatsHoldMap().size());
		assertEquals(1, ticketService.getSeatsHoldScheduledJobsMap().size());
		assertEquals(numberOfRequestedSeats, ticketService.getSeatsHoldMap().get(seatHold.getSeatHoldId()).getListOfSeats().size());

		assertEquals(0, ticketService.getSeatsBookedMap().size());
		
        try {
			Thread.sleep(65000);
		} catch (InterruptedException e) {
		}
        
		assertEquals(totalSeats, ticketService.numOfSeatsAvailable());
		assertEquals(0, ticketService.getSeatsHoldMap().size());
		assertEquals(0, ticketService.getSeatsHoldScheduledJobsMap().size());
		assertEquals(0, ticketService.getSeatsBookedMap().size());
	}
	
	@Test
	public void testSeatHoldServiceWithBooking() {
		String customerEmail = "kamran@programmer.com";
		int numberOfRequestedSeats = 15;
		int totalSeats = row * seatsInEachRow;
		
		SeatHold seatHold = ticketService.findAndHoldSeats(numberOfRequestedSeats, customerEmail);
		
		assertEquals(customerEmail, seatHold.getCustomer().getEmail());
		assertEquals(numberOfRequestedSeats, seatHold.getListOfSeats().size());
		assertEquals(totalSeats - numberOfRequestedSeats, ticketService.numOfSeatsAvailable());
		
		assertEquals(1, ticketService.getSeatsHoldMap().size());
		assertEquals(1, ticketService.getSeatsHoldScheduledJobsMap().size());
		assertEquals(numberOfRequestedSeats, ticketService.getSeatsHoldMap().get(seatHold.getSeatHoldId()).getListOfSeats().size());

		assertEquals(0, ticketService.getSeatsBookedMap().size());

		String confirmationId = ticketService.reserveSeats(seatHold.getSeatHoldId(), customerEmail);

		assertEquals(totalSeats - numberOfRequestedSeats, ticketService.numOfSeatsAvailable());
		assertEquals(0, ticketService.getSeatsHoldMap().size());
		assertEquals(0, ticketService.getSeatsHoldScheduledJobsMap().size());
		assertEquals(1, ticketService.getSeatsBookedMap().size());
		assertEquals(numberOfRequestedSeats, ticketService.getSeatsBookedMap().get(confirmationId).getListOfSeats().size());
		
        try {
			Thread.sleep(65000);
		} catch (InterruptedException e) {
		}
        
		assertEquals(totalSeats - numberOfRequestedSeats, ticketService.numOfSeatsAvailable());
		assertEquals(0, ticketService.getSeatsHoldMap().size());
		assertEquals(0, ticketService.getSeatsHoldScheduledJobsMap().size());
		assertEquals(1, ticketService.getSeatsBookedMap().size());
		assertEquals(numberOfRequestedSeats, ticketService.getSeatsBookedMap().get(confirmationId).getListOfSeats().size());
	}
}
