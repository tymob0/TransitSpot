import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';


export default function AddressForm(props) {
  const nameRegEx = /^[a-z ,.'-]+$/i;
  const codeRegEx = /^[0-9a-z ,.'-]+$/i;

  const [errFName, setErrFName] = React.useState('');
  const [errLName, setErrLName] = React.useState('');
  const [errDoc, setErrDoc] = React.useState('');

  const handleFirstName = (event) => {
    if (event.target.value.match(nameRegEx)) {
      setErrFName("");
      props.firstNamehandler(event.target.value);
    } else if (event.target.value.length > 0) {
      setErrFName("Invalid name format");
      props.firstNamehandler(false);
    }
  };

  const handleLastName = (event) => {
    if (event.target.value.match(nameRegEx)) {
      setErrLName("");
      props.lastNamehandler(event.target.value);
    } else if (event.target.value.length > 0) {
      setErrLName("Invalid name format");
      props.firstNamehandler(false);
    } 
  };

  const handleDocumentNr = (event) => {
    if (event.target.value.match(codeRegEx)) {
      setErrDoc("");
      props.documentNrHandler(event.target.value);
    } else if (event.target.value.length > 0) {
      setErrDoc("Invalid name format");
      props.firstNamehandler(false);
    }
  };

  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        Purchase details
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="firstName"
            name="firstName"
            label="First name"
            fullWidth
            variant="standard"
            onChange={handleFirstName}
            error={errFName.length === 0 ? false : true}
            helperText={errFName}
          />
          
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="lastName"
            name="lastName"
            label="Last name"
            fullWidth
            variant="standard"
            onChange={handleLastName}
            error={errLName.length === 0 ? false : true}
            helperText={errLName}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            required
            id="document_nr"
            name="document_nr"
            label="Document number"
            fullWidth
            autoComplete="document"
            variant="standard"
            onChange={handleDocumentNr}
            error={errDoc.length === 0 ? false : true}
            helperText={errDoc}
          />
        </Grid>
      </Grid>
    </React.Fragment>
  );
}
