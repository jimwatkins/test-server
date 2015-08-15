
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

    @Override
    public void start() {
    }
	
	
    /**
     * Start method to bootstrap the vert.x
     */

    @Override
    public void start(final Future<Void> result) {
    	start();
    	System.out.println("Starting Verticle");
    	
    	
    	
    	
    	result.setResult(null);
 
    	
    }
	
	
}
