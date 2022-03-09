import * as React from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Box from "@mui/material/Box";
import RoutesService from "../services/RoutesService";
import OriginDropdown from "./OriginDropdown";
import DestinationDropdown from "./DestinationDropdown";
import { InputAdornment } from "@mui/material";

export default function AddRouteDialog() {
  const [getName, setName] = React.useState(false);
  const [getCode, setCode] = React.useState(false);
  const [getDuration, setDuration] = React.useState(false);
  const [getPrice, setPrice] = React.useState(false);
  const [getOrigin, setOrigin] = React.useState(false);
  const [getDestination, setDestination] = React.useState(false);

  const [NameErr, setNameErr] = React.useState("");
  const [CodeErr, setCodeErr] = React.useState("");
  const [DurationErr, setDurationErr] = React.useState("");
  const [PriceErr, setPriceErr] = React.useState("");

  const [open, setOpen] = React.useState(false);

  const nameRegEx = /^[a-z ,.'-]+$/i;
  const codeRegEx = /^[0-9a-z ,.'-]+$/i;
  const priceRegEx = /^[0-9.]+$/i;
  const durationRegEx = /^[0-9]+$/i;

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSaveClose = () => {
    const name = getName;
    const code = getCode;
    const duration = getDuration;
    const price = getPrice;
    const origin = getOrigin;
    const destination = getDestination;

    console.log(name);
    console.log(code);
    console.log(duration);
    console.log(price);
    console.log(origin);
    console.log(destination);

    if (name || code || price || duration || origin || destination) {
      if(name.match(nameRegEx) && code.match(codeRegEx) && 
      (("" + duration).match(durationRegEx) && duration < 10000 && duration > 0) && 
      (("" +price).match(priceRegEx) && price < 500 && price >= 1 && origin.id!== destination.id)){
      RoutesService.createRoute(
        name,
        duration,
        code,
        price,
        origin.id,
        destination.id
      )
      setOpen(false);
      window.location.reload();}
      else{
        alert("Input correct data");
      }
    } else {
      alert("Details missing");
    }
   
  };

  const onInputChangeName = (event) => {
    event.preventDefault();
    setName(event.target.value);
    if (event.target.value.match(nameRegEx)) {
      setNameErr("");
    } else if (event.target.value.length > 0) {
      setNameErr("Invalid name format");
    } else {
      setNameErr("");
    }
  };

  const onInputChangeCode = (event) => {
    event.preventDefault();
    setCode(event.target.value);
    if (event.target.value.match(codeRegEx)) {
      setCodeErr("");
    } else if (event.target.value.length > 0) {
      setCodeErr("Invalid code format");
    } else {
      setCodeErr("");
    }
  };

  const onInputChangeDuration = (event) => {
    event.preventDefault();
    setDuration(event.target.value);
    if (event.target.value.match(durationRegEx) && event.target.value < 10000 && event.target.value > 0) {
      setDurationErr("");
    } else if (event.target.value.length > 0) {
      setDurationErr("Invalid duration format");
    } else {
      setDurationErr("");
    }
  };

  const onInputChangePrice = (event) => {
    event.preventDefault();
    setPrice(event.target.value);
    if (event.target.value.match(priceRegEx) && event.target.value < 500 && event.target.value >= 1) {
      setPriceErr("");
    } else if (event.target.value.length > 0) {
      setPriceErr("Invalid price format");
    } else {
      setPriceErr("");
    }
  };

  return (
    <div>
      <Box m={2} pt={3}>
        <Button variant="contained" onClick={handleClickOpen}>
          Add route
        </Button>
      </Box>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add new route</DialogTitle>
        <DialogContent>
          <DialogContentText>Click on "Add" to continue. Price must be grater than 1.00€ and less than 500.00€.
             Duration must be between 1 and 10000 minutes. Name cannot contain numbers or any special characters.</DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Name"
            fullWidth
            variant="standard"
            onChange={onInputChangeName}
            error={NameErr.length === 0 ? false : true}
            helperText={NameErr}
          />
          <TextField
            autoFocus
            margin="dense"
            id="code"
            label="Code"
            fullWidth
            variant="standard"
            onChange={onInputChangeCode}
            error={CodeErr.length === 0 ? false : true}
            helperText={CodeErr}
          />
          <TextField
            autoFocus
            margin="dense"
            id="price"
            label="Price"
            fullWidth
            variant="standard"
            onChange={onInputChangePrice}
            error={PriceErr.length === 0 ? false : true}
            helperText={PriceErr}
            InputProps={{
              startAdornment: <InputAdornment position="start">€</InputAdornment>,
            }}
          />
          <TextField
            autoFocus
            margin="dense"
            id="duration"
            label="Duration"
            fullWidth
            variant="standard"
            onChange={onInputChangeDuration}
            error={DurationErr.length === 0 ? false : true}
            helperText={DurationErr}
            InputProps={{
              startAdornment: <InputAdornment position="start">min</InputAdornment>,
            }}
          />
          <OriginDropdown
            label="Origin"
            text="Choose your starting point."
            originHandler={setOrigin}
          />
          <DestinationDropdown
            label="Destination"
            text="Choose your destination point."
            destinationHandler={setDestination}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSaveClose}>Add</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
