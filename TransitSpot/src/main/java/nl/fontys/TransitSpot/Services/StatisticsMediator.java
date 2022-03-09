package nl.fontys.TransitSpot.Services;

import nl.fontys.TransitSpot.DTO.Statistics.StationSalesDTO;
import nl.fontys.TransitSpot.DataJPA.TicketDataJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsMediator implements StatisticsService{
    private TicketDataJPA repo;
    @Autowired
    public StatisticsMediator(TicketDataJPA repo) {
        this.repo = repo;
    }

    @Override
    public List<HashMap<String, String>> getSalesCountPerWeekDay() {
        List<HashMap<String, String>> data = new ArrayList<>();
        HashMap<String, Integer> weekdays = new HashMap<>();
        weekdays.put("Monday", 0);
        weekdays.put("Tuesday", 1);
        weekdays.put("Wednesday", 2);
        weekdays.put("Thursday", 3);
        weekdays.put("Friday", 4);
        weekdays.put("Saturday", 5);
        weekdays.put("Sunday", 6);

        for(String day : weekdays.keySet()){
            data.add(this.getCountPerDay(day,weekdays.get(day)));
        }
        return data;
    }

    private HashMap<String, String> getCountPerDay(String dayName, Integer dayNumber){
        HashMap<String, String> result = new HashMap<>();
        result.put("day", dayName);
        List<StationSalesDTO> sales = this.getSalesPerStation(dayNumber);
        for (StationSalesDTO x : sales) {
            result.put(x.getStation(), x.getTotal());
        }
        return result;
    }

    private List<StationSalesDTO> getSalesPerStation(Integer day){
        List<Tuple> list = repo.groupedSalesByMonth(day);

        List<StationSalesDTO> sales = list.stream()
                .map(t -> new StationSalesDTO(
                        t.get(0, String.class),
                        t.get(1, BigInteger.class).toString()
                ))
                .collect(Collectors.toList());

        return sales;
    }
}
