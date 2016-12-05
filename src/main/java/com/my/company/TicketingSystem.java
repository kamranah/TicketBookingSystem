package com.my.company;

import java.util.Scanner;

/**
 * Main TicketingSystem class
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class TicketingSystem {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		TicketServiceImpl ticketService = new TicketServiceImpl(30, 10);
		
		while(true){
		    System.out.print("------------------------------------------\n");
		    System.out.print("Please Enter from the following options \n"
		    		+ "1 to find number of available seats \n"
		    		+ "2 find and hold the best seats \n"
		    		+ "3 reserve the seats \n"
		    		+ "4 to Exit");
		    
		    int number = 0;
		    String numberStr = scanner.next();

		    try {
		    	number = Integer.parseInt(numberStr);
		    } catch (NumberFormatException e) {
		    }

		    System.out.println("The number is: " + number);
	
		    if(number == 1){
			    System.out.println("Currently Available seats : " + ticketService.numOfSeatsAvailable());
		    }
		    else if(number == 2){
			    System.out.println("Enter Customer Email : ");
			    String customerEmail = scanner.next();
			    System.out.println("How many seats for " + customerEmail + " : ");
			    int numberOfRequestedSeats = scanner.nextInt();
			    System.out.println("You have requested " + numberOfRequestedSeats + " for Customer Email " + customerEmail);
			    System.out.println("Please Enter Y to confirm : ");
			    String confirmationStr = scanner.next();
			    if(confirmationStr.trim().toLowerCase().equals("y")){
			    	SeatHold seatHold = ticketService.findAndHoldSeats(numberOfRequestedSeats, customerEmail);
			    	if(seatHold==null){
			    		System.out.println("Unable to Hold number of requested seats currently available seats : " +
			    				ticketService.numOfSeatsAvailable());
			    	}
			    	else{
			    		System.out.println("Successfully Book " +
			    				seatHold.getListOfSeats().size() + " for " + seatHold.getCustomer().getEmail());
			    		System.out.println("Seat Hold Id " +
			    				seatHold.getSeatHoldId());
			    		System.out.println("Seat Hold will expire within " +
			    				ticketService.getHoldTimeMins() + " mins");
			    		
			    	}
			    }
	
		    }
		    else if(number == 3){
			    System.out.println("Please Enter Seat Hold Id : ");
			    int seatHoldId = scanner.nextInt();
			    System.out.println("Please Enter Email : ");
			    String customerEmail = scanner.next();
			    System.out.println("If you want to Book the seats Please Enter Y ");
			    String confirmationStr = scanner.next();
			    
			    if(confirmationStr.trim().toLowerCase().equals("y")){
			    	String confirmationId = ticketService.reserveSeats(seatHoldId, customerEmail);
			    	if(confirmationId==null){
					    System.out.println("Unable to book the seats(s) associated with this seat hold id " + seatHoldId);
			    	}
			    	else{
					    System.out.println("Successfully Booked seat(s) confirmation id " + confirmationId);
			    	}

			    }

		    }
		    else if(number == 4){
		    	System.exit(0);
		    }
		    else{
		    	System.out.println("Invalid Option Selected");
		    }
		}
	}

}
