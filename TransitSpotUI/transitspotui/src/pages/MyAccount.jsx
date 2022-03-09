import UserService from "../services/UserService";
import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import InputTextField from "../components/InputTextField";


export default function UpdatePassword() {
  UserService.checkToken();
  const passRegEx = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const password = data.get("password_old");
    const password_new = data.get("password_new");
    const password_rpt = data.get("password_rpt");

    if (password_new === password_rpt) {
      if(password.match(passRegEx) && password_new.match(passRegEx) && password_rpt.match(passRegEx)){
        UserService.changePassword(password, password_new, password_rpt);
        alert("Succesful");
        window.location.reload();
      }
      else{
        alert("Provide correct password format");
      }
    } else {
      alert("Passwords do not match");
    }
  };

  return (
    <div class="centered">
      <div class="narrow_container">
        <div class="icons_container">
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
        </div>
        <div class="icons_container">
          <Typography component="h1" variant="h5">
            Update password
          </Typography>
        </div>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <InputTextField
            name="password_old"
            label="Old password"
            type="password"
            id="password_old"
            autoComplete="current-password"
            rexEg={passRegEx}
          />
          <InputTextField
            name="password_new"
            label="New  Password"
            type="password"
            id="password_new"
            autoComplete="current-password"
            rexEg={passRegEx}
          />
          <InputTextField
            name="password_rpt"
            label="Repeat Password"
            type="password"
            id="password_rpt"
            autoComplete="current-password"
            rexEg={passRegEx}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Update
          </Button>
        </Box>
      </div>
    </div>
  );
}




