package nl.fontys.TransitSpot.Controllers;

import nl.fontys.TransitSpot.Collections.TicketCollection;
import nl.fontys.TransitSpot.Collections.UserCollection;
import nl.fontys.TransitSpot.DTO.Ticket.TicketRequestDTO;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;
import nl.fontys.TransitSpot.DTO.UserDTO.UserDTO;
import nl.fontys.TransitSpot.Services.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tickets")
public class TicketController {
    private final TicketCollection tickets;
    private final UserCollection users;
    private final PDFGenerator pdfGenerator;
    @Autowired
    public TicketController(TicketCollection tickets, UserCollection users, PDFGenerator pdfGenerator)
    {
        this.tickets = tickets;
        this.users = users;
        this.pdfGenerator = pdfGenerator;
    }
    @GetMapping
    public ResponseEntity GetAll(){
        List<TicketResponseDTO> list = this.tickets.GetAll();
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/ticketByUserID/{page}")
    public ResponseEntity GetTicketByUserID(@PathVariable(value = "page") Integer page ){
        UserDTO userDTO = users.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        UUID ID = userDTO.getID();
        List<TicketResponseDTO> list = this.tickets.GetByUserID(ID,page);
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/ticketPDF/{id}")
    public void generatePDF(@PathVariable(value = "id") UUID id, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGenerator.export(response, id);
    }
    @GetMapping("/Count")
    public ResponseEntity GetCount(){
        Long result = tickets.GetCount();
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("{id}")
    public ResponseEntity<TicketResponseDTO> GetTicketByID(@PathVariable(value = "id") UUID ID){
        TicketResponseDTO ticketResponseDTO = this.tickets.GetByID(ID);
        if(ticketResponseDTO !=null){
            return ResponseEntity.ok().body(ticketResponseDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping()
    public ResponseEntity<TicketRequestDTO> CreateTicket(@Valid @RequestBody TicketRequestDTO ticket){
        UserDTO userDTO = users.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        ticket.setTraveler(userDTO.getID());
        if(ticket.getID()==null){
            ticket.setID(UUID.randomUUID());
        }
        UUID result = this.tickets.Add(ticket);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }
}
