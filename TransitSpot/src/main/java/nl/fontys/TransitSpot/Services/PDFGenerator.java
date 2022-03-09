package nl.fontys.TransitSpot.Services;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface PDFGenerator {
    void export(HttpServletResponse response, UUID ticketID) throws IOException;
}
