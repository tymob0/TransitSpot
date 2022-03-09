import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import InputTextField from "./InputTextField";

import { useHistory } from "react-router";
import AuthService from "../services/AuthService";

import "../style.css";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" >
        TransitSpot
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default function SignIn() {
  const mailRegEx =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const passRegEx = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
  const nameRegEx = /^[a-z ,.'-]+$/i;
  const History = useHistory();
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const username = data.get("email");
    const password = data.get("password");
    const password_rpt = data.get("password_rpt");
    const fname = data.get("First_Name");
    const lname = data.get("Last_Name");

    //eslint-disable-next-line no-console
    console.log({
      password_rpt: password_rpt === password,
    });
    if (username || password) {
      if ((password === password_rpt) && username.match(mailRegEx) && password.match(passRegEx) && fname.match(nameRegEx) && lname.match(nameRegEx)) {
        AuthService.addUser(username, password, fname, lname)
          .then((response) => response.json())
          .then((responseData) => {
            History.push("/");
            window.location.reload();
          })
          .catch(() => {
            alert("error");
          });
        History.push("/LogIn");
        window.location.reload();
      }
      else {
        alert("Provide correct details");
      }
    } else {
      alert("Provide correct details");
    }
  };

  return (
    <div class="centered">
      <div class="narrow_container">
        <div class="icons_container">
          <Avatar sx={{ bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
        </div>
        <div class="icons_container">
          <Typography component="h1" variant="h5">
            Sign up
          </Typography>
        </div>
        Password must contain at lease one upper case character, one lower case  character and one number. Minimum 8 symbols.  
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <InputTextField
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            rexEg={mailRegEx}
          />
          <InputTextField name="First_Name" label="First Name" id="First_Name" rexEg={nameRegEx}/>
          <InputTextField name="Last_Name" label="Last Name" id="Last_Name" rexEg={nameRegEx}/>
          <InputTextField
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            rexEg={passRegEx}
          />
          <InputTextField
            name="password_rpt"
            label="Repeat Password"
            type="password"
            id="password_rpt"
            rexEg={passRegEx}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign Up
          </Button>
          <Grid container>
            <Grid item>
              <Link href="/LogIn" variant="body2">
                {"Already have an account? Sign In"}
              </Link>
            </Grid>
          </Grid>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </div>
    </div>
  );
}
