import axios from 'axios';    
import authHeader from './AuthHeader';

    const API_URL = "http://localhost:8080/users";

    class UserService {
    changePassword(passw_old, passw_new, passw_rpt) {
        return axios.get(API_URL  + "/changePassword/" + passw_old + "/" + passw_new, {headers:authHeader()});
    }

    checkToken() {
        async function check(){ 
            await axios.get(API_URL + "/check",  {headers:authHeader()}).catch(() =>
            {
                window.history.pushState({}, '', "/LogOut");
                window.location.reload();});
        }
        check();
    }
    
    }
    export default new UserService();