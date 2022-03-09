import {  useHistory } from 'react-router';
import AuthService from './AuthService';

const LogOut = () => {
    const History = useHistory();
    AuthService.logout();
    History.push("/");
    window.location.reload();
    return(
       <div></div>
    );
}
export default LogOut;