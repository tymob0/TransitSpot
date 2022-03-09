import { Navbar, Nav, Container } from 'react-bootstrap';
import AuthService from '../services/AuthService';
import AccountMenu from './AccountMenu';
import { store } from 'react-notifications-component';

import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import { useEffect, useState } from "react";

const ENDPOINT = "http://localhost:8080/ws";


function MainNavigation() {
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    // use SockJS as the websocket client
    const socket = SockJS(ENDPOINT);
    // Set stomp to use websockets
    const stompClient = Stomp.over(socket);
    // connect to the backend
    stompClient.connect({}, () => {
      // subscribe to the backend
      stompClient.subscribe('/topic/greetings', (data) => {
        onMessageReceived(data);
      });
    });
    // maintain the client for sending and receiving
    setStompClient(stompClient);
  }, []);

  // display the received data
  function onMessageReceived(data)
  {
    const result = JSON.parse(data.body);
    // alert(result.name);
    store.addNotification({
      title: "General announcement",
      message: result.name ,
      type: "default",
      insert: "top",
      container: "top-right",
      animationIn: ["animate__animated", "animate__fadeIn"],
      animationOut: ["animate__animated", "animate__fadeOut"],
      dismiss: {
        duration: 5000,
        onScreen: true
      }
    });
  };
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
  <Container>
  <Navbar.Brand href="/">Transit Spot</Navbar.Brand>
  <Navbar.Toggle aria-controls="responsive-navbar-nav" />
  <Navbar.Collapse id="responsive-navbar-nav">
  <Nav className="me-auto">
  {AuthService.getCurrentUser()!==null &&(
    AuthService.getCurrentUser().roles.includes("[ROLE_ADMIN]") && (
     <Nav>
     <Nav.Link href="/Stations">Stations</Nav.Link>
   </Nav>
   ))}
   {AuthService.getCurrentUser()!==null &&(
    AuthService.getCurrentUser().roles.includes("[ROLE_ADMIN]") && (
     <Nav>
     <Nav.Link href="/Routes">Routes</Nav.Link>
   </Nav>
   ))}
   {AuthService.getCurrentUser()!==null &&(
    AuthService.getCurrentUser().roles.includes("[ROLE_ADMIN]") && (
     <Nav>
     <Nav.Link href="/Statistics">Statistics</Nav.Link>
   </Nav>
   ))}
   {AuthService.getCurrentUser()!==null &&(
    AuthService.getCurrentUser().roles.includes("[ROLE_ADMIN]") && (
     <Nav>
     <Nav.Link href="/Messages">Messages</Nav.Link>
   </Nav>
   ))}
  </Nav>
  {AuthService.getCurrentUser()!==null &&(
     <Nav>
     <AccountMenu client = {stompClient} />
   </Nav>
   )}
   {AuthService.getCurrentUser()===null && (
     <Nav>
     <Nav.Link href="/LogIn">Log In</Nav.Link>
   </Nav>
   )}
  </Navbar.Collapse>
  </Container>
</Navbar>
  );
}

export default MainNavigation;
