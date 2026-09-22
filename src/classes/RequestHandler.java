package classes;

import java.io.Serializable;

public class RequestHandler implements Serializable {
    private String requestType;
    private String jsonObj;

    public RequestHandler(String requestType , String jsonObj){
        this.requestType = requestType;
        this.jsonObj = jsonObj;
    }

    public String getRequestType(){return requestType; }
    public String getJsonObj(){return  jsonObj; }
}
