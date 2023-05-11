package com.guavapay.courierservice.error;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandlerUtil {

    public static void buildHttpErrorResponse(ServletResponse response, BaseErrorResponse baseErrorResponse,
                                              Integer code) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        byte[] responseToSend = (new ObjectMapper()).writeValueAsString(baseErrorResponse).getBytes();
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(code);
        response.getOutputStream().write(responseToSend);
    }

}