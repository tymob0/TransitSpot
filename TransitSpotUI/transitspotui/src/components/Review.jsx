import * as React from 'react';
import Typography from '@mui/material/Typography';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';


export default function Review(props) {
  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        {props.data.name}
      </Typography>
      <List disablePadding>
        {props.data.routes.map((element) => (
            <ListItem key={element.name} sx={{ py: 1, px: 0 }}>
              <ListItemText primary={element.name} secondary={'Duration: ' + element.duration} />
            <Typography variant="body2">{element.price + '€'}</Typography>
          </ListItem>
          ))}

        <ListItem sx={{ py: 1, px: 0 }}>
          <ListItemText primary="Total" />
          <Typography variant="subtitle1" sx={{ fontWeight: 700 }}>
          {props.data.price + '€'}
          </Typography>
        </ListItem>
      </List>
      
    </React.Fragment>
  );
}
