import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Box from "@mui/material/Box";
import StationsService from "../services/StationsService";

export default function UpdateStationDialog(props) {
  const [getName, setName] = React.useState(props.name);
  const [getCode, setCode] = React.useState(props.code);
  const [getCity, setCity] = React.useState(props.city);

  const [open, setOpen] = React.useState(false);

  const nameRegEx = /^[a-z ,.'-]+$/i;
  const codeRegEx = /^[0-9a-z ,.'-]+$/i;

  const [errName, setErrName] = React.useState("");
  const [errCode, setErrCode] = React.useState("");
  const [errCity, setErrCity] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSaveClose = () => {
    const name = getName;
    const code = getCode;
    const city = getCity;
    console.log(name);
    console.log(code);
    console.log(city);
    console.log(props.id);
    setOpen(false);

    if (name || code || city) {
      if(name.match(nameRegEx) && code.match(codeRegEx) && city.match(nameRegEx)){
        StationsService.updateStation(name, code, city, props.id);
      }
      else{
        alert("Input correct details");
      }
    } else {
      alert("Details missing");
    }
    window.location.reload();
  };

  const onInputChangeName = (event) => {
    event.preventDefault();
    setName(event.target.value);
    if (event.target.value.match(nameRegEx)) {
      setErrName("");
    } else if (event.target.value.length > 0) {
      setErrName("Invalid name format");
    } else {
      setErrName("");
    }
  };

  const onInputChangeCode = (event) => {
    event.preventDefault();
    setCode(event.target.value);
    if (event.target.value.match(codeRegEx)) {
      setErrCode("");
    } else if (event.target.value.length > 0) {
      setErrCode("Invalid code format");
    } else {
      setErrCode("");
    }
  };

  const onInputChangeCity = (event) => {
    event.preventDefault();
    setCity(event.target.value);
    if (event.target.value.match(nameRegEx)) {
      setErrCity("");
    } else if (event.target.value.length > 0) {
      setErrCity("Invalid city format");
    } else {
      setErrCity("");
    }
  };

  return (
    <div>
      <Box>
        <Button variant="contained" onClick={handleClickOpen} color="success">
          Update
        </Button>
      </Box>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Update station</DialogTitle>
        <DialogContent>
          <DialogContentText>Click on "Save" to continue.
          Name and city cannot have numbers or any special characters.
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Name"
            fullWidth
            variant="standard"
            onChange={onInputChangeName}
            error={errName.length === 0 ? false : true}
            helperText={errName}
            defaultValue={props.name}
          />
          <TextField
            autoFocus
            margin="dense"
            id="code"
            label="Code"
            fullWidth
            variant="standard"
            onChange={onInputChangeCode}
            error={errCode.length === 0 ? false : true}
            helperText={errCode}
            defaultValue={props.code}
          />
          <TextField
            autoFocus
            margin="dense"
            id="city"
            label="City"
            fullWidth
            variant="standard"
            onChange={onInputChangeCity}
            error={errCity.length === 0 ? false : true}
            helperText={errCity}
            defaultValue={props.city}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSaveClose}>Save</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
