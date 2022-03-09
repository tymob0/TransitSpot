import axios from 'axios';
import authHeader from './AuthHeader';

const TICKET_API_BASE_URL = "http://localhost:8080/tickets";

class TicketsService {
    getTickets(page){
        return axios.get(TICKET_API_BASE_URL + '/ticketByUserID/' + page, {headers:authHeader()});
    }
    getTicketsCount(){
        return axios.get(TICKET_API_BASE_URL + "/Count", {headers:authHeader()});
    }
    // getStationsCount(){
    //     return axios.get(TICKET_API_BASE_URL + "/count", {headers:authHeader()});
    // }
    createTicket(origin, destination, firstName, lastName, travelDocumentNr, dateTime) {
        const ticket = {
            origin: origin,
            destination: destination,
            tripDateTime : dateTime,
            firstName : firstName,
            lastName : lastName,
            travelDocumentNr : travelDocumentNr
          }
        return axios.post(TICKET_API_BASE_URL ,ticket, {headers:authHeader()});
    } 
    getTicketPDF(TicketId){
        // axios.get("http://localhost:8080/tickets/ticketPDF/98eb9dc8-7543-4b11-8b0a-537ae0e98317", {headers:authHeader()});
        return axios({
            url: TICKET_API_BASE_URL + "/ticketPDF/" + TicketId,
            method: 'GET',
            responseType: 'blob',
            headers:authHeader(), // important
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'file.pdf'); //or any other extension
            document.body.appendChild(link);
            link.click();
        });
    }
}

export default new TicketsService()