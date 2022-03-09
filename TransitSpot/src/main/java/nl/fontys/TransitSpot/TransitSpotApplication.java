package nl.fontys.TransitSpot;

import nl.fontys.TransitSpot.Collections.RouteCollection;
import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Collections.TicketCollection;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class TransitSpotApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransitSpotApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserCollection users, StationCollection stations, RouteCollection routes, TicketCollection tickets){
		return args -> {
			UUID user_id = UUID.randomUUID();
			users.saveUser(new UserDTO(UUID.randomUUID(), "admin","admin@ts.com","Admin1111"));
			users.saveUser(new UserDTO(user_id, "user","transitspotbv@gmail.com","User1111"));

			users.saveRole(new Role(UUID.randomUUID(), "ROLE_ADMIN"));
			users.saveRole(new Role(UUID.randomUUID(), "ROLE_USER"));

			users.addRoleToUser("admin@ts.com", "ROLE_ADMIN");
			users.addRoleToUser("transitspotbv@gmail.com", "ROLE_USER");


			StationFullDTO EIN = new StationFullDTO(UUID.randomUUID(), "Eindhoven", "EIN", "Eindhoven");
			StationFullDTO UTR = new StationFullDTO(UUID.randomUUID(), "Utrecht", "UTR ", "Utrecht");
			StationFullDTO BOS = new StationFullDTO(UUID.randomUUID(), "Den Bosch", "BOS", "Den Bosch");
			StationFullDTO ROT = new StationFullDTO(UUID.randomUUID(), "Rotterdam", "ROT", "Rotterdam");
			StationFullDTO GRO = new StationFullDTO(UUID.randomUUID(), "Groningen", "GRO", "Groningen");

			stations.Add(EIN);
			stations.Add(UTR);
			stations.Add(GRO);
			stations.Add(BOS);
			stations.Add(ROT);

			RouteRequestDTO EIN_BOS = new RouteRequestDTO("Eindhoven-Den Bosch", 20, "EIN-BOS", 10.0);
			EIN_BOS.setID(UUID.randomUUID());
			EIN_BOS.setOrigin(EIN.getID());
			EIN_BOS.setDestination(BOS.getID());

			RouteRequestDTO EIN_UTR = new RouteRequestDTO("Eindhoven-Utrecht", 40, "EIN-UTR", 20.0);
			EIN_UTR.setID(UUID.randomUUID());
			EIN_UTR.setOrigin(EIN.getID());
			EIN_UTR.setDestination(UTR.getID());

			RouteRequestDTO BOS_ROT = new RouteRequestDTO("Den Bosch-Rotterdam", 40, "BOS-ROT", 20.0);
			BOS_ROT.setID(UUID.randomUUID());
			BOS_ROT.setOrigin(BOS.getID());
			BOS_ROT.setDestination(ROT.getID());

			RouteRequestDTO ROT_GRO = new RouteRequestDTO("Rotterdam-Groningen", 90, "ROT-GRO", 25.0);
			ROT_GRO.setID(UUID.randomUUID());
			ROT_GRO.setOrigin(ROT.getID());
			ROT_GRO.setDestination(GRO.getID());

			RouteRequestDTO UTR_GRO = new RouteRequestDTO("Utrecht-Groningen", 40, "UTR-GRO", 20.0);
			UTR_GRO.setID(UUID.randomUUID());
			UTR_GRO.setOrigin(UTR.getID());
			UTR_GRO.setDestination(GRO.getID());

			RouteRequestDTO BOS_EIN = new RouteRequestDTO("Den Bosch-Eindhoven", 20, "BOS-EIN", 10.0);
			BOS_EIN.setID(UUID.randomUUID());
			BOS_EIN.setOrigin(BOS.getID());
			BOS_EIN.setDestination(EIN.getID());

			RouteRequestDTO UTR_EIN = new RouteRequestDTO("Utrecht-Eindhoven", 40, "UTR-EIN", 20.0);
			UTR_EIN.setID(UUID.randomUUID());
			UTR_EIN.setOrigin(UTR.getID());
			UTR_EIN.setDestination(EIN.getID());

			RouteRequestDTO ROT_BOS = new RouteRequestDTO("Rotterdam-Den Bosch", 40, "ROT-BOS", 20.0);
			ROT_BOS.setID(UUID.randomUUID());
			ROT_BOS.setOrigin(ROT.getID());
			ROT_BOS.setDestination(BOS.getID());


			RouteRequestDTO GRO_UTR = new RouteRequestDTO("Groningen-Utrecht", 40, "GRO-UTR", 20.0);
			GRO_UTR.setID(UUID.randomUUID());
			GRO_UTR.setOrigin(GRO.getID());
			GRO_UTR.setDestination(UTR.getID());

			routes.Add(EIN_BOS);
			routes.Add(EIN_UTR);
			routes.Add(BOS_ROT);
			routes.Add(ROT_GRO);
			routes.Add(UTR_GRO);

			routes.Add(BOS_EIN);
			routes.Add(UTR_EIN);
			routes.Add(ROT_BOS);
			routes.Add(GRO_UTR);

			TicketRequestDTO ticket_1 = new TicketRequestDTO(UUID.randomUUID(), LocalDate.parse("2022-06-12").atStartOfDay().toLocalDate(),
					"Bohdan", "Tymofieienko", "FH-3478324");
			ticket_1.setOrigin(EIN.getID());
			ticket_1.setDestination(GRO.getID());
			ticket_1.setTraveler(user_id);
			tickets.Add(ticket_1);


			TicketRequestDTO ticket_2 = new TicketRequestDTO(UUID.randomUUID(), LocalDate.parse("2022-09-12").atStartOfDay().toLocalDate(),
					"Bohdan", "Tymofieienko", "FH-3478324");
			ticket_2.setOrigin(GRO.getID());
			ticket_2.setDestination(ROT.getID());
			ticket_2.setTraveler(user_id);
			tickets.Add(ticket_2);

			TicketRequestDTO ticket_3 = new TicketRequestDTO(UUID.randomUUID(), LocalDate.parse("2022-09-12").atStartOfDay().toLocalDate(),
					"Bohdan", "Tymofieienko", "FH-3478324");
			ticket_3.setOrigin(GRO.getID());
			ticket_3.setDestination(BOS.getID());
			ticket_3.setTraveler(user_id);
			tickets.Add(ticket_3);
		};
	}
}
