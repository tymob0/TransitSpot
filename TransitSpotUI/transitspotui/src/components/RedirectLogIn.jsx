import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useHistory} from 'react-router';


const theme = createTheme();

export default function RedirectLogIn() {
  const history = useHistory();

  const handleSubmitSignIn = (event) => {
    event.preventDefault();
    let path = '/LogIn';
    history.push(path);
  };

  const handleSubmitSignUp = (event) => {
    event.preventDefault();
    let path = '/SignUp';
    history.push(path);
  };

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Please Log In to continue with your purchase. Have no account yet - Sign Up! It's really easy. See you! 
          </Typography>
          <Box component="form" onSubmit={handleSubmitSignIn} noValidate sx={{ mt: 1 }}>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
          </Box>
          <Box component="form" onSubmit={handleSubmitSignUp} noValidate sx={{ mt: 1 }}>
          <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign Up
            </Button>
          </Box>   
        </Box>
      </Container>
    </ThemeProvider>
  );
}