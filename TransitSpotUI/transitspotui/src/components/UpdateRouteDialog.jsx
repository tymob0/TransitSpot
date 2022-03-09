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
import { InputAdornment } from "@mui/material";

export default function UpdateRouteDialog(props) {
  const [getName, setName] = React.useState(props.name);
  const [getCode, setCode] = React.useState(props.code);
  const [getDuration, setDuration] = React.useState(props.duration);
  const [getPrice, setPrice] = React.useState(props.price);

  const [NameErr, setNameErr] = React.useState("");
  const [CodeErr, setCodeErr] = React.useState("");
  const [DurationErr, setDurationErr] = React.useState("");
  const [PriceErr, setPriceErr] = React.useState("");

  const nameRegEx = /^[a-z ,.'-]+$/i;
  const codeRegEx = /^[0-9a-z ,.'-]+$/i;
  const priceRegEx = /^[0-9.]+$/i;
  const durationRegEx = /^[0-9]+$/i;

  const [open, setOpen] = React.useState(false);

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

    console.log(name);
    console.log(code);
    console.log(duration);
    console.log(price);

    

    if (name || code || price || duration) {
      if(name.match(nameRegEx) && code.match(codeRegEx) && 
      (("" + duration).match(durationRegEx) && duration < 10000 && duration > 0) && 
      (("" +price).match(priceRegEx) && price < 500 && price >= 1)){
      RoutesService.updateRoute(name, duration, code, price, props.id);
      setOpen(false);
      window.location.reload()}
      else{
        alert("Provide correct details");
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
      <Box>
        <Button variant="contained" onClick={handleClickOpen} color="success">
          Update
        </Button>
      </Box>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Update route</DialogTitle>
        <DialogContent>
          <DialogContentText>Click on Save to continue. Price must be grater than 1.00€ and less than 500.00€.
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
            error={CodeErr.length === 0 ? false : true}
            helperText={CodeErr}
            defaultValue={props.code}
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
            defaultValue={props.price}
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
            defaultValue={props.duration}
            InputProps={{
              startAdornment: <InputAdornment position="start">min</InputAdornment>,
            }}
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
