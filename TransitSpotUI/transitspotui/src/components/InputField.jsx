import TextField from '@mui/material/TextField';

const InputField = (props) => {
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
    autoFocus
  />
  );
};

export default InputField;
