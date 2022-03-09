import * as React from "react";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Button from "@mui/material/Button";
import Link from "@mui/material/Link";
import Typography from "@mui/material/Typography";
import AddressForm from "./AddressForm";
import Review from "./Review";
import PathSearch from "./PathSearch";
import { useState, useEffect } from "react";
import PathSearchService from "../services/PathSearchService";
import AuthService from "../services/AuthService";
import RedirectLogIn from "./RedirectLogIn";
import TicketService from "../services/TicketService";
import DownloadTicket from "./DownloadTicket";
import "../style.css";

function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {"Copyright © "}
      <Link color="inherit">
        TransitSpot
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

const steps = ["Select stations", "Review", "Payment details"];

function getStepContent(
  step,
  formOriginHandler,
  formDestinationHandler,
  pathState,
  formFirstName,
  formLastName,
  formDocumentNr,
  handleDateChange,
  useOriginState,
  useDestState,
  value
) {
  switch (step) {
    case 0:
      // return <Review />;
      return (
        <PathSearch
          originHandler={formOriginHandler}
          destinationHandler={formDestinationHandler}
          dateHandler={handleDateChange}
        />
      );
    case 1:
      return <Review data={pathState} />;
    case 2:
      if (AuthService.isLoggedIn() == true) {
        return (
          <AddressForm
            firstNamehandler={formFirstName}
            lastNamehandler={formLastName}
            documentNrHandler={formDocumentNr}
          />
        );
      } else {
        localStorage.setItem("origin", JSON.stringify(useOriginState));
        localStorage.setItem("destination", JSON.stringify(useDestState));
        localStorage.setItem("value", JSON.stringify(value));
        return <RedirectLogIn />;
      }
    default:
      throw new Error("Unknown step");
  }
}

export default function PlanTrip() {
  const [activeStep, setActiveStep] = useState(0);
  const [useOriginState, setOriginState] = useState(null);
  const [useDestState, setDestState] = useState(null);
  const [usePathState, setPathState] = useState(null);
  const [ticketID, setTicketID] = useState(null);

  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [documentNr, setDocumentNr] = useState(null);
  const [value, setValue] = React.useState(new Date());



  function handleDateChange(newValue) {
    setValue(newValue);
  }

  const displayButton = (step) => {
    if ((step === 2 && AuthService.isLoggedIn() === true) || step < 2) {
      return true;
    } else {
      return false;
    }
  };

  const handleNext = () => {
    setActiveStep(activeStep + 1);
  };

  const handleBack = () => {
    setActiveStep(activeStep - 1);
  };

  const formOriginHandler = (data) => {
    setOriginState(data);
  };

  const formDestinationHandler = (data) => {
    setDestState(data);
  };

  const formFirstName = (data) => {
    setFirstName(data);
  };

  const formLastName = (data) => {
    setLastName(data);
  };
  const formDocumentNr = (data) => {
    setDocumentNr(data);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    if (useOriginState !== null && useDestState !== null && value >= today) {
      if (useOriginState.id !== useDestState.id) {
        await PathSearchService.getPath(useOriginState.id, useDestState.id)
          .then((response) => {
            if (response.data !== null) {
              setPathState(response.data);
              handleNext();
            }
          })
          .catch((_err) => {
            alert("Something went wrong");
          });
      } else {
        alert("Origin and destination must be different.");
      }
    } else {
      alert("Wrong input format.");
    }
  };

  

  const getSavedData = async () => {
    const savedOrigin = JSON.parse(localStorage.getItem("origin"));
    const savedDest = JSON.parse(localStorage.getItem("destination"));
    const savedValue = JSON.parse(localStorage.getItem("value"));

    if(savedOrigin!==null && savedDest!==null && savedValue!==null){
      setOriginState(savedOrigin);
      setDestState(savedDest);
      setValue(value);

      await PathSearchService.getPath(savedOrigin.id, savedDest.id)
          .then((response) => {
            if (response.data !== null) {
              setPathState(response.data);
              localStorage.removeItem("origin");
              localStorage.removeItem("destination");
              localStorage.removeItem("value");
              handleNext();
            }
          })
          .catch((_err) => {
            alert("Something went wrong");
          });
      
    }
  };

  useEffect(() => {
    getSavedData();
  }, []);

  const handleSubmitPayment = async (event) => {
    if (activeStep === 2) {
      event.preventDefault();
      console.log(useOriginState);
      console.log(useDestState);
      console.log(firstName);
      console.log(lastName);
      console.log(documentNr);
      if (
        useOriginState !== null &&
        useDestState !== null &&
        firstName !== false &&
        lastName !== false &&
        documentNr !== false
      ) {
        await TicketService.createTicket(
          useOriginState.id,
          useDestState.id,
          firstName,
          lastName,
          documentNr,
          value
        ).then((response) => {
          setTicketID(response.data);
          handleNext();
        });
        handleNext();
      } else {
        alert("Provide correct details");
      }
    } else {
      handleNext();
    }
  };

  return (
    <div class="centered">
      <Paper
        variant="outlined"
        sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
      >
        <Typography component="h1" variant="h4" align="center">
          Plan the trip!
        </Typography>
        <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
          {steps.map((label) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          ))}
        </Stepper>
        <React.Fragment>
          {activeStep === steps.length ? (
            <React.Fragment>
              <Typography variant="h5" gutterBottom>
                Thank you for your order.
              </Typography>
              <Typography variant="subtitle1">
                Your order number is {ticketID}. We have emailed your order
                confirmation. You can download the ticket clicking on the button
                below.
              </Typography>
              <DownloadTicket ticket={ticketID} />
            </React.Fragment>
          ) : (
            <React.Fragment>
              {getStepContent(
                activeStep,
                formOriginHandler,
                formDestinationHandler,
                usePathState,
                formFirstName,
                formLastName,
                formDocumentNr,
                handleDateChange,
                useOriginState,
                useDestState,
                value
              )}
              <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
                {activeStep !== 0 && (
                  <Button onClick={handleBack} sx={{ mt: 3, ml: 1 }}>
                    Back
                  </Button>
                )}
                {activeStep !== 0 && displayButton(activeStep) === true && (
                  <Button
                    variant="contained"
                    onClick={handleSubmitPayment}
                    sx={{ mt: 3, ml: 1 }}
                    data-cy="submit_next_step"
                  >
                    {activeStep === steps.length - 1 ? "Pay" : "Next"}
                  </Button>
                )}
                {activeStep === 0 && (
                  <Box
                    component="form"
                    onSubmit={handleSubmit}
                    noValidate
                    sx={{ mt: 1 }}
                  >
                    <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      sx={{ mt: 3, mb: 2 }}
                    >
                      Search
                    </Button>
                  </Box>
                )}
              </Box>
            </React.Fragment>
          )}
        </React.Fragment>
        <Copyright />
      </Paper>
    </div>
  );
}
