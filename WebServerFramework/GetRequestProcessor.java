import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
//
import java.nio.charset.StandardCharsets;
/**
 * A GetRequestProcessor contains the logic necessary for handling HTTP GET requests.
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */

public class GetRequestProcessor extends RequestProcessor {

    /**
     * Create a new GetRequestProcessor</br>
     * Calling <code>getRequestMethod()</code> on this object returns <code>HTTPMethodType.GET</code>.
     */
    public GetRequestProcessor() {
        super(HTTPMethodType.GET);
    }
    
    /**
     * Process a given HTTP GET Request message, returning the result in an HTTP Response message.</br>
     */
    public Response process(final Request request) throws Exception {
        // Code here
        assert(this.canProcess(request.getMethodType()));
        Response response = new Response(request.getHTTPVersion());
        String fileName;
        fileName = request.getURI().substring(1);
        File requestedFile = new File(fileName);
        FileInputStream fileInput;
        if (requestedFile.exists() == false){
            response.setStatus(HTTPStatus.NOT_FOUND);
            response.setHeaderField("Content-Type:", "text/html");
            requestedFile = new File("notfound.txt");
            fileInput = new FileInputStream(requestedFile);
            response.setBody(fileInput);
        }
        else {
            fileInput = new FileInputStream(requestedFile);
            String contentType = "";
            if (fileName.substring(fileName.indexOf('.') +1).equals("txt") || fileName.substring(fileName.indexOf('.') +1).equals("html")){
                contentType = "text/html";
            }
            response.setStatus(HTTPStatus.OK);
            response.setHeaderField("Connection:", "close");
            response.setHeaderField("Content-Type:", contentType);
            response.setBody(fileInput);
        }
        return response;
    }
}