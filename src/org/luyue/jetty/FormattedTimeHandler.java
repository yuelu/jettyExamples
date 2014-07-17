package org.luyue.jetty;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.FastDateFormat;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class FormattedTimeHandler extends AbstractHandler {

    private static final String OUTPUT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String INPUT_TIME_FORMAT = "yyyy-MM-ddHH:mm:ss";
    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat(OUTPUT_TIME_FORMAT);
    private static final FastDateFormat fastFormat = FastDateFormat.getInstance(OUTPUT_TIME_FORMAT);

    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        final String inputTime = request.getParameter("time");
        Date date = LocalDateTime.parse(inputTime, DateTimeFormat.forPattern(INPUT_TIME_FORMAT)).toDate();

        final String method = request.getParameter("method");
        if ("SimpleDateFormat".equalsIgnoreCase(method)) {
            response.getWriter().println(simpleFormat.format(date));
        } else if ("FastDateFormat".equalsIgnoreCase(method)) {
            response.getWriter().println(fastFormat.format(date));
        } else {
            response.getWriter().println(new SimpleDateFormat(OUTPUT_TIME_FORMAT).format(date));
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        server.setHandler(new FormattedTimeHandler());

        server.start();
        server.join();
    }
}