import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import React from "react";
import MainNavigation from "./components/MainNavigation";
import Stations from "./pages/Stations";
import RoutePlanner from "./pages/RoutePlanner";
import LogIn from "./pages/LogIn";
import LogOut from "./services/LogOut";
import SignUp from "./components/SignUp";
import Routes from "./pages/Routes";
import Announcement from "./pages/Announcement";
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";
import AuthService from "./services/AuthService";
import { Redirect } from "react-router";
import jwtDecode from 'jwt-decode';
import MyTickets from "./pages/MyTickets";
import MyAccount from "./pages/MyAccount";
import Statistics from "./pages/Statistics";

function App() {
  function requireAuth() {
    if (AuthService.isLoggedIn()===true) {
      const token = JSON.parse(localStorage.getItem("user"))&& JSON.parse(localStorage.getItem("user"))["access_token"];
      if (jwtDecode(token).exp > Date.now() / 1000) {
        return true;
      }
    }
    else return false;
  }

  function requireAdminAuth(){
    if (AuthService.isLoggedIn()===true) {
      if(AuthService.getCurrentUser().roles.includes("[ROLE_ADMIN]")===true){
        const token = JSON.parse(localStorage.getItem("user"))&& JSON.parse(localStorage.getItem("user"))["access_token"];
        if (jwtDecode(token).exp > Date.now() / 1000) {
          return true;
        }
      }
      else return false;
    }
    else return false;
  }

  function LogOutOrMain(){
    if (AuthService.isLoggedIn()===true) {
        return <LogOut/>;
    }
    return <RoutePlanner/>
  }

  return (
    <Router>
      <ReactNotification />
      <MainNavigation />
      <Switch>
        <Route
          exact
          path="/"
          render={() => (requireAdminAuth() ? (<Redirect to="/Statistics"/>) : (<RoutePlanner/>))}
        />
        <Route path="/LogIn" exact component={LogIn} />
        <Route path="/LogOut" component={LogOut} />
        <Route
          exact
          path="/Routes"
          render={() => (requireAdminAuth() ? (<Routes/>) : LogOutOrMain())}
        />
        <Route
          exact
          path="/Messages"
          render={() => (requireAdminAuth() ? (<Announcement/>) : LogOutOrMain())}
        />
        <Route
          exact
          path="/Stations"
          render={() => (requireAdminAuth() ? (<Stations/>) : LogOutOrMain())}
        />
        <Route
          exact
          path="/MyTickets"
          render={() => (requireAuth() ? (<MyTickets/>) : LogOutOrMain())}
        />
        <Route
          exact
          path="/MyAccount"
          render={() => (requireAuth() ? (<MyAccount/>) : LogOutOrMain())}
        />
        <Route
          exact
          path="/Statistics"
          render={() => (requireAdminAuth() ? (<Statistics/>) : LogOutOrMain())}
        />

        <Route path="/SignUp" component={SignUp} />
        
      </Switch>
    </Router>
  );
}
export default App;
