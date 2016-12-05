package com.my.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

/**
 * TicketServiceImpl class
 * 
 * 
 * @author Kamran Ahmad
 *
 */
public class TicketServiceImpl implements TicketService {

	private int totalNumOfSeats=0;
	private Map<Integer, SeatHold> seatsHoldMap= new HashMap<Integer, SeatHold>();
	private Map<Integer, ScheduledFuture> seatsHoldScheduledJobsMap= new HashMap<Integer, ScheduledFuture>();

	private Map<String, SeatHold> seatsBookedMap= new HashMap<String, SeatHold>();
	private int holdTimeMins = 30;
	private Queue<Seat> seatPriorityQueue;
	private final ScheduledExecutorService seatHoldScheduler =
			     Executors.newScheduledThreadPool(5);



	public Map<Integer, SeatHold> getSeatsHoldMap() {
		return seatsHoldMap;
	}

	public void setSeatsHoldMap(Map<Integer, SeatHold> seatsHoldMap) {
		this.seatsHoldMap = seatsHoldMap;
	}

	public Map<Integer, ScheduledFuture> getSeatsHoldScheduledJobsMap() {
		return seatsHoldScheduledJobsMap;
	}

	public void setSeatsHoldScheduledJobsMap(
			Map<Integer, ScheduledFuture> seatsHoldScheduledJobsMap) {
		this.seatsHoldScheduledJobsMap = seatsHoldScheduledJobsMap;
	}

	public Map<String, SeatHold> getSeatsBookedMap() {
		return seatsBookedMap;
	}

	public void setSeatsBookedMap(Map<String, SeatHold> seatsBookedMap) {
		this.seatsBookedMap = seatsBookedMap;
	}

	public Queue<Seat> getSeatPriorityQueue() {
		return seatPriorityQueue;
	}

	public void setSeatPriorityQueue(Queue<Seat> seatPriorityQueue) {
		this.seatPriorityQueue = seatPriorityQueue;
	}

	public TicketServiceImpl(int rows, int numOfSeatsInEachRow){
		this.totalNumOfSeats = rows * numOfSeatsInEachRow;
		seatPriorityQueue = new PriorityQueue<>(totalNumOfSeats, seatComparator);
				
		int seatPriority=1;
		for(int i=0; i<rows; i++){
			for(int j=0; j<numOfSeatsInEachRow; j++){
				Seat seat = new Seat(i, j);
				seat.setSeatPriority(seatPriority++);
				seatPriorityQueue.add(seat);
			}
		}
	}
	
	public int getTotalNumOfSeats() {
		return totalNumOfSeats;
	}

	public void setTotalNumOfSeats(int totalNumOfSeats) {
		this.totalNumOfSeats = totalNumOfSeats;
	}



	public int getHoldTimeMins() {
		return holdTimeMins;
	}

	public void setHoldTimeMins(int holdTimeMins) {
		this.holdTimeMins = holdTimeMins;
	}
	
	public synchronized void releaseSeatHoldAndPutBack(SeatHold seatHold){
		System.out.println("releaseSeatHoldAndPutBack the hold for " + seatHold.getSeatHoldId() +
					"\n booked by : " + seatHold.getCustomer().getEmail());
		
		for(Seat seat:seatHold.getListOfSeats()) {
			seatPriorityQueue.add(seat);
		}
		
		seatsHoldMap.remove(seatHold.getSeatHoldId());
		seatsHoldScheduledJobsMap.remove(seatHold.getSeatHoldId());
	}

	@Override
	public int numOfSeatsAvailable() {
		return seatPriorityQueue.size();
	}

	@Override
	public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		if(numSeats > numOfSeatsAvailable()){
			System.out.println("Unable to hold requested number of seats");
			return null;
		}
		else{
			SeatHold seatHold = new SeatHold();
			//hold seats
			Customer customer = new Customer();
			customer.setEmail(customerEmail);
			List<Seat> lstSeatsOnHold = new ArrayList<Seat>(numSeats);
			
			for(int i=0; i<numSeats; i++){
				Seat seatOnHold = seatPriorityQueue.poll();
				lstSeatsOnHold.add(seatOnHold);
			}
			
			seatHold.setCustomer(customer);
			seatHold.setListOfSeats(lstSeatsOnHold);
			
			seatsHoldMap.put(seatHold.getSeatHoldId(), seatHold);
			
		    Runnable releaseHold = new ReleaseHoldHandler(seatHold);

		    ScheduledFuture<?> scheduledFutureHandle = seatHoldScheduler.schedule(releaseHold,
					holdTimeMins, TimeUnit.MINUTES);
		    
		    seatsHoldScheduledJobsMap.put(seatHold.getSeatHoldId(), scheduledFutureHandle);
		    
		    return seatHold;
		}
	}


	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		if(seatsHoldMap.containsKey(seatHoldId)){
			ScheduledFuture<?> scheduledFutureHandle = seatsHoldScheduledJobsMap.remove(seatHoldId);
			scheduledFutureHandle.cancel(true);
			SeatHold seatHold = seatsHoldMap.remove(seatHoldId);

			String uniqueConfirmationID = UUID.randomUUID().toString();

			seatsBookedMap.put(uniqueConfirmationID, seatHold);
			
			return uniqueConfirmationID;
		}
		else{
			System.out.println("Unable to find seat hold id " + seatHoldId);
		}
		
		return null;
	}

	public static Comparator<Seat> seatComparator = new Comparator<Seat>(){
		
		@Override
		public int compare(Seat s1, Seat s2) {
            return (int) (s1.getSeatPriority() - s2.getSeatPriority());
        }
	};
	
	
	private final class ReleaseHoldHandler implements Runnable {
		ReleaseHoldHandler(SeatHold seatHold){
		  this.seatHold = seatHold;
		}
		@Override public void run() {
			releaseSeatHoldAndPutBack(seatHold);
		}
		private SeatHold seatHold;
	}
}
