import axios from 'axios';
import authHeader from './AuthHeader';

const STATION_API_BASE_URL = "http://localhost:8080/stations";

class StationsService {
    getStations(){
        return axios.get(STATION_API_BASE_URL, {headers:authHeader()});
    }
    getStationsCount(){
        return axios.get(STATION_API_BASE_URL + "/count", {headers:authHeader()});
    }
    createStation(name, code, city) {
        const station = {
            name: name,
            code: code,
            city : city
          }
        return axios.post(STATION_API_BASE_URL ,station, {headers:authHeader()});
    } 
    getStationById(StationId){
        return axios.get(STATION_API_BASE_URL + '/' + StationId, {headers:authHeader()});
    }
    getStationsFromPage(Page){
        return axios.get(STATION_API_BASE_URL + '/page/'+ Page, {headers:authHeader()});
    }
    updateStation(name, code, city, id){
        const station = {
            id: id,
            name: name,
            code: code,
            city : city
          }
        return axios.put(STATION_API_BASE_URL ,station, {headers:authHeader()});
    }
    deleteStation(StationId){
        return axios.delete(STATION_API_BASE_URL + '/' + StationId, {headers:authHeader()});
    }
}

export default new StationsService()