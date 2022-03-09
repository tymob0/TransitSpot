import axios from 'axios';
import authHeader from './AuthHeader';

const STATION_API_BASE_URL = "http://localhost:8080/routes";

class PathSearchService {
    getPath(OriginId, DestinationID){
        // console.log(STATION_API_BASE_URL + '/' + OriginId + "/" + DestinationID)
         return axios.get(STATION_API_BASE_URL + '/' + OriginId + "/" + DestinationID, {headers:authHeader()});
    }
}

export default new  PathSearchService()