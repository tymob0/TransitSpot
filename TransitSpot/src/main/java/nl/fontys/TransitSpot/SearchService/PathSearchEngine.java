package nl.fontys.TransitSpot.SearchService;

import nl.fontys.TransitSpot.DTO.Path.PathDTO;

import java.util.UUID;

public interface PathSearchEngine {
    PathDTO SearchForRoute(UUID originID, UUID  destinationID);
}
