package nl.fontys.TransitSpot.Repositories.JPA;

import nl.fontys.TransitSpot.DataJPA.TicketDataJPA;
import nl.fontys.TransitSpot.Entity.Ticket;
import nl.fontys.TransitSpot.Storages.TicketStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TicketStorageJPA implements TicketStorage {
    @Autowired
    TicketDataJPA repo;
    @Override
    public List<Ticket> ReadAll() {
        return repo.findAll();
    }
    @Override
    public Ticket ReadByID(UUID ID) {
        return repo.findByID(ID);
    }
    @Override
    public boolean Create(Ticket ticket) {
        repo.save(ticket);
        return true;
    }

    @Override
    public Page<Ticket> ReadTicketByUserID(UUID id, Integer page) {
        Pageable request = PageRequest.of(page,5);
        return repo.findByUserID(id.toString(), request);
    }

    @Override
    public Long GetCount() {
        return repo.countByIDIsNotNull();
    }
}
