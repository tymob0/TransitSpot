import { Card, Button } from "react-bootstrap";

function ResponsePath(props) {
    if(props.data!=null){
        return (
            <Card>
            <div>{JSON.stringify(props.data.time)}</div> 
            {props.data.routes.map((element) => (
            <div>
                {JSON.stringify(element.name)}
            </div>
          ))}
            </Card>
          );    
    }
    else return(<div></div>);
 
}

export default ResponsePath;
