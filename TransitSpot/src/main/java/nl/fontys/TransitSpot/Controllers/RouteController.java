package nl.fontys.TransitSpot.Controllers;

import nl.fontys.TransitSpot.DTO.Route.RouteRequestDTO;
import nl.fontys.TransitSpot.DTO.Route.RouteResponseDTO;
import nl.fontys.TransitSpot.DTO.Path.PathDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.SearchService.PathSearchEngine;
import nl.fontys.TransitSpot.Collections.RouteCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/routes")
public class RouteController {
    private final RouteCollection routes;
    private final PathSearchEngine pathFinder;
    @Autowired
    public RouteController(RouteCollection routes, PathSearchEngine pathFinder) {
        this.routes = routes;
        this.pathFinder = pathFinder;
    }
    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> GetAll(){
        List<RouteResponseDTO> list = this.routes.GetAll();
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/count")
    public ResponseEntity GetCount(){
        Long result = this.routes.GetCount();
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity GetAllFromPage(@PathVariable(value = "page") Integer page){
        List<RouteResponseDTO> list = this.routes.GetAll(page);
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<RouteResponseDTO> GetRouteByID(@PathVariable(value = "id") UUID ID){
        RouteResponseDTO routeM = this.routes.GetByID(ID);
        if(routeM !=null){
            return ResponseEntity.ok().body(routeM);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{originID}/{destinationID}")
    public ResponseEntity<PathDTO> GetPathFromOriginToDestination(@PathVariable UUID originID, @PathVariable UUID  destinationID){
        if(!originID.equals(destinationID)){
            PathDTO path = this.pathFinder.SearchForRoute(originID,destinationID);
            if(path != null){
                return ResponseEntity.ok().body(path);
            }
        }
        return ResponseEntity.notFound().build();

    }
    @PostMapping()
    public ResponseEntity<RouteRequestDTO> CreateRoute(@Valid @RequestBody RouteRequestDTO route){
        if(route.getID()==null){
            route.setID(UUID.randomUUID());
        }
        if(!this.routes.Add(route)){
            String entity = "route with ID" + route.getID() + " already exists";
            return new ResponseEntity(entity, HttpStatus.CONFLICT);
        }
        else{
            String url = "route/" + route.getID();
            URI uri = URI.create(url);
            return new ResponseEntity(uri, HttpStatus.CREATED);
        }
    }
    @PutMapping()
    public ResponseEntity<RouteRequestDTO> UpdateRoute(@Valid @RequestBody RouteRequestDTO route){
        if(this.routes.Update(route)){
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity("Please provide a valid route ID.",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{ID}")
    public ResponseEntity DeleteRoute(@PathVariable UUID ID){
        this.routes.Remove(ID);
        return ResponseEntity.ok().build();
    }
}
