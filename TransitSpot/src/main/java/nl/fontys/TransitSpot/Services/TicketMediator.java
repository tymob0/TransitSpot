package nl.fontys.TransitSpot.Services;

import nl.fontys.TransitSpot.Collections.StationCollection;
import nl.fontys.TransitSpot.Collections.TicketCollection;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.Converters.StationsConverter;
import nl.fontys.TransitSpot.Converters.TicketConverter;
import nl.fontys.TransitSpot.Converters.UsersConverter;
import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;
import nl.fontys.TransitSpot.Entity.AppUser;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Entity.Ticket;
import nl.fontys.TransitSpot.Storages.TicketStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicketMediator implements TicketCollection {
    private TicketStorage storage;
    private TicketConverter converter;
    private StationsConverter stationsConverter;
    private UsersConverter usersConverter;
    private StationCollection stations;
    private UserCollection users;
    private EmailSender sender;

    @Autowired
    public TicketMediator(TicketStorage storage, TicketConverter converter, StationsConverter stationsConverter,
                          UsersConverter usersConverter, StationCollection stations, UserCollection users, EmailSender sender ) {
        this.storage = storage;
        this.converter = converter;
        this.stationsConverter = stationsConverter;
        this.usersConverter = usersConverter;
        this.stations = stations;
        this.users = users;
        this.sender = sender;
    }

    @Override
    public List<TicketResponseDTO> GetAll() {
        return converter.EntityToDTO(storage.ReadAll());
    }
    @Override
    public TicketResponseDTO GetByID(UUID ID) {
        return converter.EntityToDTO(storage.ReadByID(ID));
    }
    @Override
    public UUID Add(TicketRequestDTO ticket) {
        Ticket converted = null;
        Station origin = stationsConverter.FullDTOtoEntity(stations.GetByID(ticket.getOrigin()));
        Station destination = stationsConverter.FullDTOtoEntity(stations.GetByID(ticket.getDestination()));
        AppUser user = usersConverter.DTOtoEntity(users.getUserByID(ticket.getTraveler()));
        if(origin != null && destination != null && user != null){
            converted = converter.DTOtoEntity(ticket);
            converted.setOrigin(origin);
            converted.setDestination(destination);
            converted.setTraveler(user);
        }
        this.sendMessage(ticket);
        storage.Create(converted);

        return ticket.getID();
    }
    @Override
    public List<TicketResponseDTO> GetByUserID(UUID id, Integer page) {
        return converter.EntityToDTO(storage.ReadTicketByUserID(id,page).getContent());
    }

    private void sendMessage(TicketRequestDTO ticket){
        String emailMessage = "Dear " + users.getUserByID(ticket.getTraveler()).getName()+
                ", You successfully purchased a ticket from " +
                 stations.GetByID(ticket.getOrigin()).getName() +
                " To " + stations.GetByID(ticket.getDestination()).getName()+
                ". On " + ticket.getTripDateTime() + ". We wish you a safe trip!";
        sender.send(users.getUserByID(ticket.getTraveler()).getEmail(),
                 "Transit spot. New ticket! " + ticket.getID() ,emailMessage);
    }

    @Override
    public Long GetCount() {
        return storage.GetCount();
    }
}
