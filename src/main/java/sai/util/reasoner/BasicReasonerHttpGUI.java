package sai.util.reasoner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;













import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * A basic http GUI
 * @author maiquel
 *
 */

public class BasicReasonerHttpGUI implements IReasonerHttpGUI{

	private List<IReasoner> reasoners = new ArrayList<IReasoner>();

	//protected static BasicReasonerHttpGUI gui = null;

	protected List<String> beliefs = new ArrayList<String>();


	public BasicReasonerHttpGUI(int port) {
		super();

		try {
			InetSocketAddress addr = new InetSocketAddress(port);			
			HttpServer server;
			server = HttpServer.create(addr, 0);
			server.createContext("/", new MyHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void addReasoner(IReasoner reasoner){
		reasoners.add(reasoner);
	}





	class MyHandler implements HttpHandler {

		public void handle(HttpExchange exchange) throws IOException {						
			String requestMethod = exchange.getRequestMethod();
			if (requestMethod.equalsIgnoreCase("GET")) {
				Headers responseHeaders = exchange.getResponseHeaders();
				responseHeaders.set("Content-Type", "text/plain");
				exchange.sendResponseHeaders(200, 0);


				OutputStream responseBody = exchange.getResponseBody();
				Headers requestHeaders = exchange.getRequestHeaders();


				responseBody.write(handleFacts().getBytes());
				responseBody.close();

			}
		}
	}





	@Override
	public String handleFacts() {
		String s = "";
		ArrayList<String> others = new ArrayList<String>();

		beliefs.clear();
		for(IReasoner r:reasoners){
			Iterator<?> it = r.getBeliefs();
			while(it.hasNext()){
				beliefs.add(it.next().toString());
			}
		}

		Collections.sort(beliefs.subList(1, beliefs.size()));


		for(String bel:beliefs){
			others.add(bel);


		}


		for(String st:others){
			s = s + st+ "\n";
		}


		return s;
	}






}
