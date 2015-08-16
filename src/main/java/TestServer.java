
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.VoidHandler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.eventbus.ReplyException;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;


public class TestServer extends Verticle {

	private String verticalID = "unintialized";
	private static volatile int verticalCount = 0;
	private int sleep;
	
    /**
     * Start method to bootstrap the vert.x
     */

    @Override
//    public void start(final Future<Void> result) {
    public void start() {
    	verticalID = initId();
    	logMessage("Starting Verticle: " + verticalID, verticalID);

    	String step = "load configuration";
    	beginLoggedStep(step, verticalID);
        JsonObject config = container.config(); // service-config.json
        final int port = Integer.valueOf(config.getString("port"));  
        sleep = Integer.valueOf(config.getString("sleep")); 
        finishLoggedStep(step, verticalID);
       	
        step = "create http server";
        beginLoggedStep(step, verticalID);
        HttpServer server = vertx.createHttpServer();
        RouteMatcher routeMatcher = new RouteMatcher();
        
        routeMatcher.getWithRegEx(".*", getHandler());
        server.setAcceptBacklog(10000);
        server.requestHandler(routeMatcher).listen(port);

		finishLoggedStep(step, verticalID);
    	
//    	result.setResult(null);
    	
//    	logMessage("Exiting container", verticalID);
//    	container.exit();
	
    }
    
    private static String initId() {
    	synchronized (Verticle.class) {
	    	verticalCount++;
	    	logMessage("Incrementing verticalCount: " + verticalCount, "0");
	    	return Integer.toString(verticalCount);
    	}
    }
    
    private Handler<HttpServerRequest> getHandler() {
        return request -> {
            // handle the static content requests
        	try { Thread.sleep(sleep); } catch (InterruptedException ie) {}
			request.response().sendFile("index.html");
			
//            String[] filePath = request.path().split("/");
//            if (filePath.length == 3) {
//                String file = filePath[filePath.length - 1];
//                request.response().sendFile("webapp/" + file);
//            }
//            else {
//                request.response().setStatusCode(404);
//                request.response().end();
//            }

        };
    }    
    
    
    private static final void beginLoggedStep(String stepName, String vid) {
    	logMessage("Beginning " + stepName + "...", vid);
    }

    private static final void finishLoggedStep(String step, String vid) {
    	logMessage(step + " complete", vid);
    }
    
    private static final void logMessage(String msg, String vid) {
    	System.out.print("\n["+ vid + "] " + msg);
    }
	
	
}
