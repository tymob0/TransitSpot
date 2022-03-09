import UserService from "../services/UserService";
import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import { useEffect, useState } from "react";
import Grid from "@mui/material/Grid";
import DownloadTicket from "../components/DownloadTicket";
import TicketService from "../services/TicketService";

const columns = [
  { field: "tripDateTime", headerName: "DateTime", flex: 1, minWidth: 150, },
  { field: "origin", headerName: "Origin", flex: 1, minWidth: 150,  },
  { field: "destination", headerName: "Destination", flex: 1, minWidth: 150, },
  {
    field: "Actions",
    flex: 1,
    minWidth: 140, 
    renderCell: (cellValues) => {
      return (
        <Grid container>
          <Grid item>
          <DownloadTicket ticket={cellValues.row.id}/>
          </Grid>
        </Grid>
      );
    },
  },
];

export default function DataTable() {
  UserService.checkToken();
  const [page, setPage] = useState(0);
  const [ticketItems, setTickets] = useState([]);
  const [rowCount, setRowCount] = useState(100);

  useEffect(() => {
    getTickets(page);
    getCount();
  }, [page]);

  const getTickets = async (page) => {
    await TicketService.getTickets(page).then((response) => {
      setTickets(response.data);
    });
  };

  const getCount = async () => {
    await TicketService.getTicketsCount().then((response) => {
      setRowCount(response.data);
    });
  };


  return (
    <div style={{ height: 510, width: "100%", padding: "20px" }}>
      <DataGrid
        density="comfortable"
        rows={ticketItems}
        columns={columns}
        pagination
        pageSize={5}
        rowsPerPageOptions={[5]}
        rowCount={rowCount}
        paginationMode="server"
        onPageChange={(newPage) => setPage(newPage)}
      />
    </div>
  );
}



