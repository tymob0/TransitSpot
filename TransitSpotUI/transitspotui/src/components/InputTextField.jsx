import TextField from "@mui/material/TextField";
import * as React from "react";

const InputTextField = (props) => {
  const [error, setError] = React.useState('');
  const handleChange = (event) => {
    event.preventDefault();
    const value = event.target.value;

    if (value.match(props.rexEg)) {
      setError("");
    } else if (value.length > 0) {
      setError("Invalid "+props.label+" format");
    } else {
      setError("");
    }
  };

  return (
    <TextField
      margin="normal"
      required
      fullWidth
      id={props.id}
      label={props.label}
      name={props.name}
      autoComplete={props.autoComplete}
      type={props.type}
      onChange={handleChange}
      error={error.length === 0 ? false : true}
      helperText={error}
      autoFocus
    />
  );
};

export default InputTextField;
