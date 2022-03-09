package nl.fontys.TransitSpot.Controllers;


import nl.fontys.TransitSpot.DTO.Station.StationFullDTO;
import nl.fontys.TransitSpot.Services.StatisticsService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService service;

    @Autowired
    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity GetAll(){
        List<HashMap<String, String>> list = this.service.getSalesCountPerWeekDay();
        List<JSONObject> data = new ArrayList<>();
        for(HashMap<String, String> map: list){
            JSONObject json = new JSONObject(map);
            data.add(json);
        }
        JSONArray response = new JSONArray(data);
        return ResponseEntity.ok().body(response.toString());
    }
}
