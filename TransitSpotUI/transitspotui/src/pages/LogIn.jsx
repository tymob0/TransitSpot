import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { useHistory } from "react-router";
import AuthService from "../services/AuthService";
import InputTextField from "../components/InputTextField";

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
      <Link color="inherit">
        TransitSpot
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default function LogIn() {
  const History = useHistory();
  const mailRegEx =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const passRegEx = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const username = data.get("email");
    const password = data.get("password");
    console.log({
      email: username,
      password: password,
    });
    if ((username || password) && username.match(mailRegEx)) {
      AuthService.login(username, password)
        .then((response) => response.json())
        .then((responseData) => {
          localStorage.setItem("user", JSON.stringify(responseData));
          History.push("/");
          window.location.reload();
        })
        .catch(() => {
          alert("error");
        });
    } else {
      alert("Provide username and password");
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
            Sign in
          </Typography>
        </div>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <InputTextField
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            rexEg={mailRegEx}
          />
          <InputTextField
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            rexEg={passRegEx}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign In
          </Button>
          <Grid container>
            <Grid item>
              <Link href="/SignUp" variant="body2">
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
        </Box>
        <Copyright sx={{ mt: 8, mb: 4 }} />
      </div>
    </div>
  );
}
