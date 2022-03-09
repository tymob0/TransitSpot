package nl.fontys.TransitSpot.Controllers;

import nl.fontys.TransitSpot.DTO.Message.RequestMessageDTO;
import nl.fontys.TransitSpot.DTO.Message.ResponseMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseMessageDTO sendResponse(RequestMessageDTO request) throws Exception{
        Thread.sleep(1000);
        return new ResponseMessageDTO("Message:" +
                HtmlUtils.htmlEscape(request.getName()));
    }
}
