package wedding.jonfreer.com.util;

import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.application.services.GuestService;
import com.jonfreer.wedding.infrastructure.factories.DatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.factories.GuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.factories.ReservationRepositoryFactory;
import java.util.ArrayList;
import java.util.Scanner;

import com.jonfreer.wedding.domain.Guest;

public class Console {

	static class DependencyInjectionContainer{
		
		public GuestService resolveGuestService(){
			return new GuestService(
					new GuestRepositoryFactory(),
					new ReservationRepositoryFactory(),
					new DatabaseUnitOfWorkFactory());
		}
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			System.out.print("> ");
			String command = scanner.nextLine();
			handleCommand(scanner, command);
			System.out.println();
		}

	}
	
	public static void handleCommand(Scanner scanner, String command){
		
		DependencyInjectionContainer diContainer = 
				new DependencyInjectionContainer();
		
		GuestService guestService = diContainer.resolveGuestService();
		
		
		if(command.equalsIgnoreCase("QUIT")){
			System.exit(0);
		}else if(command.equalsIgnoreCase("GETALL")){
			ArrayList<Guest> guests = guestService.getGuests();
			print(guests);
		}else if(command.equalsIgnoreCase("GET")){
			System.out.print("id --> ");
			String guestId = scanner.nextLine();
			
			try{
				int guestIdInt = Integer.valueOf(guestId);
				Guest guest = guestService.getGuest(guestIdInt);
				print(guest);
			}
			catch(NumberFormatException numFormatException){
				System.out.println("the id must be an integer!");
			} catch (ResourceNotFoundException e) {
				System.out.println("could not find a guest with the id provided.");
			}	
		}
	}
	
	public static void print(ArrayList<Guest> guests){
		for(Guest guest : guests){
			print(guest);
		}
	}
	
	public static void print(Guest guest){
		System.out.println("****************************************");
		System.out.println("ID:\t\t\t" + guest.getId());
		System.out.println("First Name:\t\t" + guest.getGivenName());
		System.out.println("Last Name:\t\t" + guest.getSurName());
		System.out.println("Description:\t\t" + guest.getDescription());
		System.out.println("Invite Code:\t\t" + guest.getInviteCode());
		System.out.println("Dietary Restrictions:\t" + guest.getDietaryRestrictions());
		System.out.println("****************************************");
	}
}
