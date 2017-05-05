package ru.originld.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 *
 * @author miganko
 */
@JsonInclude(NON_NULL)
public class RestResponse {
    
    private int status;
    @JsonRawValue
    private String data;

    public RestResponse() {
    }

    public RestResponse(int status, String data) {
        this.status = status;
        this.data = data;
    }

    public RestResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}