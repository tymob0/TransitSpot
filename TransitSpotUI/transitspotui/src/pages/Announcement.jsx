import UserService from "../services/UserService";
import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import EmailIcon from '@mui/icons-material/Email';
import Typography from '@mui/material/Typography';

import { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
// Set the backend location
const ENDPOINT = "http://localhost:8080/ws";



function Announcement() {
  UserService.checkToken();
  const [stompClient, setStompClient] = useState(null);

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const message = data.get('text');
    stompClient.send("/app/hello", {}, JSON.stringify({'name': message}));
  };

  useEffect(() => {
    const socket = SockJS(ENDPOINT);
    // Set stomp to use websockets
    const stompClient = Stomp.over(socket);
    // connect to the backend
    // maintain the client for sending and receiving
    setStompClient(stompClient);
  }, []);



  return (
    <div class='centered_message'>
        <div class='narrow_container'>
          <div class='icons_container'>
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <EmailIcon />
          </Avatar>
          </div>
          <div class='icons_container'>
          <Typography component="h1" variant="h5">
            Your message
          </Typography>
          </div>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
            <TextField
              required
              id="outlined-multiline-flexible"
              label="Multiline"
              multiline
              maxRows={4}
              name="text"
              fullWidth
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Post announcement
            </Button>
          </Box>
          </div>
      </div>
  );

}

export default Announcement;
