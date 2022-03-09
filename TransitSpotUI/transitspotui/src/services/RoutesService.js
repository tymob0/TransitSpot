import axios from 'axios';
import authHeader from './AuthHeader';

const ROUTES_API_BASE_URL = "http://localhost:8080/routes";

class RoutesService {
    getRoutes(){
        return axios.get(ROUTES_API_BASE_URL, {headers:authHeader()});
    }
    getRoutesCount(){
        return axios.get(ROUTES_API_BASE_URL + "/count", {headers:authHeader()});
    }
    getRoutesFromPage(Page){
        return axios.get(ROUTES_API_BASE_URL + "/page/" + Page, {headers:authHeader()});
    }
    createRoute(name, duration, code, price, origin, destination) {
        const route = {
            name: name,
            duration: duration,
            code: code,
            price: price,
            origin : origin,
            destination : destination
          }
        return axios.post(ROUTES_API_BASE_URL ,route , {headers:authHeader()});
    } 
    getRouteById(RouteId){
        return axios.get(ROUTES_API_BASE_URL + '/' + RouteId);
    }
    updateRoute(name, duration, code, price, id){
        const route = {
            id: id,
            name: name,
            duration: duration,
            code: code,
            price: price,
          }
        return axios.put(ROUTES_API_BASE_URL ,route , {headers:authHeader()});
    }
    deleteRoute(RouteId){
        return axios.delete(ROUTES_API_BASE_URL + '/' + RouteId, {headers:authHeader()} );
    }
}

export default new RoutesService()