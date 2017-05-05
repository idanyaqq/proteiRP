package ru.originld.controller.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.originld.utils.RestResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by redin on 5/4/17.
 */
public class ResponseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) servletResponse);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        filterChain.doFilter(servletRequest,responseWrapper);
        String responseContent = new String(responseWrapper.getDataStream());
        RestResponse restResponse = new RestResponse();

        int status = ((HttpServletResponse) servletResponse).getStatus();
        String contentType = servletResponse.getContentType();

        if(needToChangeStatus(status)){
            restResponse.setStatus(responseWrapper.getStatus());
            if(responseContent.equals("")){
                responseContent= null;
            }
            restResponse.setData(responseContent);
            byte[] responsetoSend = restResponseBytes(restResponse);
            servletResponse.getOutputStream().write(responsetoSend);

        } else {
            if(status!= NO_CONTENT.value()){
                if(status == FORBIDDEN.value()&& httpServletRequest.getSession(false)== null){
                    responseWrapper.setStatus(UNAUTHORIZED.value());
                    servletResponse.setContentType("text/html");
                    servletResponse.getOutputStream().write(unauthorizedresponse());
                } else {
                    servletResponse.getOutputStream().write(responseContent.getBytes());
                }
            }
        }

    }

    private boolean needToChangeStatus(int status){
        boolean changeresponse = true;
        if(status !=0
                && status!= OK.value()
                && status!= CREATED.value()
                && status!= UNAUTHORIZED.value()){
            changeresponse =false;
        }
        return changeresponse;
    }

    @Override
    public void destroy() {

    }

    private byte[] restResponseBytes(RestResponse restResponse) throws  IOException{
        String serialized = new ObjectMapper().writeValueAsString(restResponse);
        return serialized.getBytes();
    }

    private byte[] unauthorizedresponse()throws IOException{
        String serizlized = new ObjectMapper().writeValueAsString(new RestResponse(UNAUTHORIZED.value()));
        return serizlized.getBytes();
    }
}
