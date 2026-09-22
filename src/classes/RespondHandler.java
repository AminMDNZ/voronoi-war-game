package classes;

import java.io.Serializable;

public class RespondHandler implements Serializable {
    private int statusCode;
    private String jsonResult;

    public RespondHandler(int statusCode , String jsonResult){
        this.statusCode = statusCode;
        this.jsonResult = jsonResult;
    }
    public RespondHandler(){

    }


    public int getStatusCode(){return statusCode; }
    public String getJsonResult(){return jsonResult; }

    public void setStatusCode(int statusCode){this.statusCode = statusCode; }
    public void setJsonResult(String jsonResult) {this.jsonResult = jsonResult; }
}
