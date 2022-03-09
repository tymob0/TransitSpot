import * as React from 'react';
import TicketService from '../services/TicketService';
import Button from '@mui/material/Button';



export default function DownloadTicket(props) {
    const getPDF = async () => {
        await TicketService.getTicketPDF(props.ticket);
      };  
  return(
    <Button variant="contained" onClick={getPDF}>Download</Button>
  )
}

