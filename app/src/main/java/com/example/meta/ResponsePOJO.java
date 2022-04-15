package com.example.meta;

public class ResponsePOJO {

        private boolean status;
        private String remarks;

    public ResponsePOJO(boolean status, String remarks) {
        this.status = status;
        this.remarks = remarks;
    }


    public boolean isStatus() {
        return status;
    }

    public String getRemarks(){
        return remarks;
    }
}
