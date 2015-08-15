
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
	private static int verticalCount = 0;
	
    @Override
    public void start() {
    	initId();
    }
	
	
    /**
     * Start method to bootstrap the vert.x
     */

    @Override
    public void start(final Future<Void> result) {
    	start();
    	logMessage("\nStarting Verticle: " + verticalID);

    	beginLoggedStep("load configuration");
        JsonObject config = container.config(); // service-config.json
        final int port = Integer.valueOf(config.getString("port"));  
        final int sleep = Integer.valueOf(config.getString("sleep")); 
        finishLoggedStep();
       	
        beginLoggedStep("Starting http server");
		vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
			public void handle(HttpServerRequest req) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
				}
				req.response().sendFile("index.html");
			}
		}).listen(port);
		finishLoggedStep();
    	
    	result.setResult(null);
	
    }
    
    private synchronized void initId() {
    	verticalCount++;
    	
    	verticalID = Integer.toString(verticalCount);
    }
    
    
    private final void beginLoggedStep(String stepName) {
    	logMessage("\n["+ verticalID + "] " + "Starting " + stepName + "...");
    }

    private final void finishLoggedStep() {
    	logMessage("complete\n");
    }
    
    private final void logMessage(String msg) {
    	System.out.print(msg);
    }
	
	
}
