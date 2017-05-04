package controller.filters;

import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author miganko
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream output;
    private FilterServletOutputStream filterOutput;
    private PrintWriter pw;
    private int status = HttpStatus.OK.value();

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new FilterServletOutputStream(output);
        }
        return filterOutput;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (filterOutput == null) {
            filterOutput = new FilterServletOutputStream(output);
        }
        if (pw == null) {
            pw = new PrintWriter(filterOutput, true);
        }
        return pw;
    }

    public byte[] getDataStream() {
        return output.toByteArray();
    }

    @Override
    public final void setStatus(int sc) {
        status = sc;
        super.setStatus(sc);
    }

    @Override
    public int getStatus() {
        return status;
    }
}