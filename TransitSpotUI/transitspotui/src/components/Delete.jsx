
import Button from "@mui/material/Button";

function Delete(props) {
    const deleteStationHandler = () =>{
        props.service(props.id);
        window.location.reload();
    }

    return(
        <Button variant="contained" color="error" onClick={deleteStationHandler}>
                Delete
        </Button>
    )
 
}

export default Delete;
