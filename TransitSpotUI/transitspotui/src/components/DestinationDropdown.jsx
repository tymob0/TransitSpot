import * as React from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormHelperText from "@mui/material/FormHelperText";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { useEffect, useState } from "react";
import StationsService from "../services/StationsService";


const StationsDropdown = (props) => {
  const [stationItems, setStations] = useState([]);
  const [age, setAge] = React.useState('');
  const [station, setStation] = useState(null);

  const handleChange = (event) => {
    setAge(event.target.value);
    let inputStation = JSON.parse(event.target.value);
    setStation(inputStation);
    props.destinationHandler(inputStation);
    // console.log(inputStation);
  };

  useEffect(() => {
    getStations();
  }, []);


  const getStations = () => {
    StationsService.getStations().then((response) => {
      setStations(response.data);
      //console.log(response.data);
    });
  };

  return (
    <FormControl sx={{ m: 1, minWidth: 380 }}>
      <InputLabel id="demo-simple-select-helper-label">{props.label}</InputLabel>
      <Select
        labelId="demo-simple-select-helper-label"
        id="demo-simple-select-helper"
        value={age}
        label="Age"
        onChange={handleChange}
      >
        <MenuItem value="">
          <em>None</em>
        </MenuItem>
        {stationItems.map((station) => (
            <MenuItem value={JSON.stringify(station)}>
                {station.name}
             </MenuItem>
          ))}
      </Select>
      <FormHelperText>{props.text}</FormHelperText>
    </FormControl>
  );
};

export default StationsDropdown;
