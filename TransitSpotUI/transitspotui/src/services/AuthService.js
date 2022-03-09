import axios from 'axios';    
import authHeader from './AuthHeader';

    const API_URL = "http://localhost:8080/login";
 

    class AuthService {
    login(username, password) {
        var reqBody = "username="+username+"&password="+password+"&grant_type=password";
        return fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            },
            body: reqBody
        })

    }

    addUser(username, password, firstName, lastName) {
        const user = {
            name: firstName + " " + lastName,
            email: username,
            password : password
          }
        return axios.post('http://localhost:8080/users/addUser',user, {headers:authHeader()});
    } 

    getCurrentUser(){
        return JSON.parse(localStorage.getItem("user"));
    }        

    isLoggedIn(){
        if(JSON.parse(localStorage.getItem("user") !== null)){
            return true;
        }
        else {
            return false;
        }
    };

    isTokkenValid(){
        async function check(){
            return await axios.get('http://localhost:8080/users/check',{headers: authHeader()}).then(() => {return true}).catch(() => {return false;});
        }
        console.log(check());
        return check();
    }

    logout() {
        localStorage.removeItem("user");
        localStorage.removeItem("origin");
        localStorage.removeItem("destination");
        localStorage.removeItem("value");
    }
}

    export default new AuthService();