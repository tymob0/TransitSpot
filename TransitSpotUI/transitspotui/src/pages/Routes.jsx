
import UserService from "../services/UserService";
import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import RoutesService from "../services/RoutesService";
import { useEffect, useState } from "react";
import AddRouteDialog from "../components/AddRouteDialog";
import UpdateRouteDialog from "../components/UpdateRouteDialog";
import Delete from "../components/Delete";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";

const columns = [
  { field: "code", headerName: "Code", flex: 1 ,  minWidth: 200,},
  { field: "duration", headerName: "Duration", flex: 1,  minWidth: 200, },
  { field: "name", headerName: "Name", flex: 1,  minWidth: 200, },
  { field: "price", headerName: "Price", flex: 1,  minWidth: 200, },
  {
    field: "Actions",
    flex: 1,
    minWidth: 200,
    renderCell: (cellValues) => {
      return (
        <Grid container>
          <Grid item>
            <UpdateRouteDialog
              name={cellValues.row.name}
              code={cellValues.row.code}
              duration={cellValues.row.duration}
              price={cellValues.row.price}
              origin={cellValues.row.origin}
              destination={cellValues.row.destination}
              id={cellValues.row.id}
            />
          </Grid>
          <Grid item>
            <Box>
              <Delete
                id={cellValues.row.id}
                service={RoutesService.deleteRoute}
              />
            </Box>
          </Grid>
        </Grid>
      );
    },
  },
];

export default function DataTable() {
  UserService.checkToken();
  const [routesItems, setRoutes] = useState([]);
  const [page, setPage] = useState(0);
  const [rowCount, setRowCount] = useState(100);

  useEffect(() => {
    getRoutes(page);
    getCount();
  }, [page]);

  const getRoutes = async (page) => {
    await RoutesService.getRoutesFromPage(page).then((response) => {
      setRoutes(response.data);
      console.log(routesItems);
    });
  };

  const getCount = async () => {
    await RoutesService.getRoutesCount().then((response) => {
      setRowCount(response.data);
      console.log(rowCount);
    });
  };

  return (
      <div style={{ height: 510, width: "100%", padding: "20px" }}>
        <AddRouteDialog />
        <DataGrid
          density="comfortable"
          rows={routesItems}
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



