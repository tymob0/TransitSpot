package nl.fontys.TransitSpot.Services;

import java.util.HashMap;
import java.util.List;

public interface StatisticsService {
    List<HashMap<String, String>> getSalesCountPerWeekDay();
}
