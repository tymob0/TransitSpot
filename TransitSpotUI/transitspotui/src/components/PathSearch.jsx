import * as React from "react";
import Avatar from "@mui/material/Avatar";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import DirectionsTransitIcon from "@mui/icons-material/DirectionsTransit";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import OriginDropdown from "./OriginDropdown";
import DestinationDropdown from "./DestinationDropdown";
import DesktopDatePicker from "@mui/lab/DesktopDatePicker";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';

const theme = createTheme();

export default function PathSearch(props) {
  const [value, setValue] = React.useState(new Date());

  const handleDateChange = (newValue) => {
    setValue(newValue);
    props.dateHandler(newValue);
  };
 
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <DirectionsTransitIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Plan your trip
          </Typography>
          <Box
            component="form"
            // onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
            // ref={selectedStations}
          >
            <OriginDropdown
              label="Origin"
              text="Choose your starting point."
              originHandler={props.originHandler}
              data-cy="origin_dropdown"
            />
            <DestinationDropdown
              label="Destination"
              text="Choose your destination point."
              destinationHandler={props.destinationHandler}
              data-cy="destination_dropdown"
            />
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <Stack spacing={3}>
                <DesktopDatePicker
                  label="Trip date"
                  inputFormat="dd/MM/yyyy"
                  onChange={handleDateChange}
                  value = {value}
                  renderInput={(params) => <TextField {...params} />}
                  disablePast
                />
              </Stack>
            </LocalizationProvider>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
