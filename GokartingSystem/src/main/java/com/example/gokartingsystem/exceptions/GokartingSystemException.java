package com.example.gokartingsystem.exceptions;

import org.json.JSONObject;

public class GokartingSystemException extends RuntimeException{
    private final ExceptionBody exceptionBody;

    public GokartingSystemException(ExceptionBody exceptionBody) {
        this.exceptionBody = exceptionBody;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", this.exceptionBody.errorCode);
        jsonObject.put("message", this.exceptionBody.message);
        return jsonObject.toString();
    }
}
