TicketBookingSystem
For this system at the start of system we have to tell how many rows and how many seats in each row.
For simplicity reason i have decided to run one event in one run(can be made to add multiple events per session),
in each run user will have option to select number of row and seats in each row for that event.
Each seat has a priority(priority of seats can be changed later versions) and seats will be reserved by the given priority.
There is a 30 mins hold time for each reservation, if seats are not confirmed before that reservation will be cancelled
Project is setup using Maven and have console input. and Unit-Test are build to test the ticketService

When application is started on console user will see option to select from as following
		    		1 to find number of available seats
		    		2 find and hold the best seats
		    		3 reserve the seats
		    		4 to Exit
