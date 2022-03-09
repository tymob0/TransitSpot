import UserService from "../services/UserService";
import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import StationsService from "../services/StationsService";
import { useEffect, useState } from "react";
import AddStationDialog from "../components/AddStationDialog";
import UpdateStationDialog from "../components/UpdateStationDialog";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Delete from "../components/Delete";

const columns = [
  { field: "code", headerName: "Code", flex: 1, minWidth: 200, },
  { field: "name", headerName: "Name", flex: 1, minWidth: 200, },
  { field: "city", headerName: "City", flex: 1, minWidth: 200, },
  {
    field: "Actions",
    flex: 1,
    minWidth: 200,
    renderCell: (cellValues) => {
      return (
        <Grid container>
          <Grid item >
          <Box>
            <UpdateStationDialog
              name={cellValues.row.name}
              code={cellValues.row.code}
              city={cellValues.row.city}
              id={cellValues.row.id}
            />
              </Box>
          </Grid>
          <Grid item>
            <Box>
              <Delete
                id={cellValues.row.id}
                service={StationsService.deleteStation}
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
  const [page, setPage] = useState(0);
  const [stationItems, setStations] = useState([]);
  const [rowCount, setRowCount] = useState(100);

  useEffect(() => {
    getStations(page);
    getCount();
  }, [page]);

  const getStations = async (page) => {
    await StationsService.getStationsFromPage(page).then((response) => {
      setStations(response.data);
    });
  };

  const getCount = async () => {
    await StationsService.getStationsCount().then((response) => {
      setRowCount(response.data);
    });
  };

  return (
    <div style={{ height: 510, width: "100%", padding: "20px" }}>
      <AddStationDialog />
      <DataGrid
        density="comfortable"
        rows={stationItems}
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



