import axios from 'axios';
import authHeader from './AuthHeader';

const STATISTICS_API_BASE_URL = "http://localhost:8080/statistics";

class StatisticsService {
    getSalesPerWeekDay(){
        return axios.get(STATISTICS_API_BASE_URL, {headers:authHeader()});
    }
    
}

export default new StatisticsService()