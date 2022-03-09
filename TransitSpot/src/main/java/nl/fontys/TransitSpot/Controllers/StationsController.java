package nl.fontys.TransitSpot.Controllers;

import nl.fontys.TransitSpot.DTO.Station.StationDTO;
import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Entity.Station;
import nl.fontys.TransitSpot.Collections.StationCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/stations")
public class StationsController {
    private final StationCollection stations;
    @Autowired
    public StationsController(StationCollection stations) {
        this.stations = stations;
    }

    @GetMapping
    public ResponseEntity GetAll(){
        List<StationFullDTO> list = this.stations.GetAll();
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/count")
    public ResponseEntity GetCount(){
        Long result = this.stations.GetCount();
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity GetAllFromPage(@PathVariable(value = "page") Integer page){
        List<StationFullDTO> list = this.stations.GetAll(page);
        if(list.size()>0)
            return ResponseEntity.ok().body(list);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<StationFullDTO> GetStationByID(@PathVariable(value = "id") UUID ID){
        StationFullDTO station = this.stations.GetByID(ID);
        if(station!=null){
            return ResponseEntity.ok().body(station);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping()
    public ResponseEntity<StationDTO> CreateStation(@Valid @RequestBody StationFullDTO station){
        if(station.getID()==null){
            station.setID(UUID.randomUUID());
        }
        if(!this.stations.Add(station)){
            station.getID();
            String entity = "Station with ID" + station.getID() + " already exists";
            return new ResponseEntity(entity, HttpStatus.CONFLICT);
        }
        else{
            String url = "station/" + station.getID();
            URI uri = URI.create(url);
            return new ResponseEntity(uri, HttpStatus.CREATED);
        }
    }
    @PutMapping()
    public ResponseEntity<StationDTO> UpdateStation(@Valid @RequestBody StationFullDTO station){
        if(this.stations.Update(station)){
            return ResponseEntity.noContent().build();
        }
        else{
            return new ResponseEntity("Please provide a valid station ID.",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{ID}")
    public ResponseEntity DeleteStation(@PathVariable UUID ID){
        this.stations.Remove(ID);
        return ResponseEntity.ok().build();
    }
}
