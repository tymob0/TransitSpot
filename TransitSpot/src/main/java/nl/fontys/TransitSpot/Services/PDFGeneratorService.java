package nl.fontys.TransitSpot.Services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import nl.fontys.TransitSpot.Collections.TicketCollection;
import nl.fontys.TransitSpot.DTO.Ticket.TicketResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PDFGeneratorService implements PDFGenerator{
    TicketCollection tickets;

    @Autowired
    public PDFGeneratorService(TicketCollection tickets) {
        this.tickets = tickets;
    }

    public void export(HttpServletResponse response, UUID ticketID) throws IOException {
        TicketResponseDTO ticket = tickets.GetByID(ticketID);
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Train Ticket.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font secondFontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(16);

        Paragraph paragraph1 = new Paragraph("TransitSpot", secondFontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontParagraph.setSize(12);


        Paragraph paragraph2 = new Paragraph("Details: \r\n", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        paragraph2.add("Ticket Number: " + ticket.getID() + "\r\n");
        paragraph2.add("Traveler full name: " + ticket.getFirstName() + " " + ticket.getLastName()+ "\r\n");
        paragraph2.add("Traveler document number: " + ticket.getTravelDocumentNr()+ "\r\n");
        paragraph2.add("Trip date: " + ticket.getTripDateTime() + "\r\n");
        paragraph2.add("Origin: " + ticket.getOrigin() + "\r\n");
        paragraph2.add("Destination: " + ticket.getDestination()+ "\r\n");

        Barcode barcode = new Barcode128();

        PdfContentByte context = writer.getDirectContent();
        barcode.setCode(ticket.getID().toString());
        Image image = barcode.createImageWithBarcode(context, null, null);

        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);

        document.add(image);
        document.close();
    }
}