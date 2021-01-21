package app.dragontale.LendingLibrary;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLPool;

public class AppServer {

	public void run(Vertx vertx) {

		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);

		router.route("/").handler(context -> {

			HttpServerResponse response = context.response();

      		response.putHeader("content-type", "text/plain");
			response.end("HTTP server response test.");

		});

		router.route("/lending").handler(context -> {
			
			MultiMap queryParams = context.queryParams();
			String bookID = queryParams.get("bookID");
			String lenderID = queryParams.get("lenderID");

			MySQLPool client = new Connector().run(vertx);
			new Lending().addEntry(client, bookID, lenderID);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Book " + bookID + " lended to " + lenderID + ".");

		});

		router.route("/return").handler(context -> {
			
			MultiMap queryParams = context.queryParams();
			String entryID = queryParams.get("entryID");
			String bookID = queryParams.get("bookID");

			MySQLPool client = new Connector().run(vertx);
			new Lending().closeEntry(client, entryID, bookID);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Completed return for entry " + entryID + ".");

		});

		router.route("/books").handler(context -> {

			MultiMap queryParams = context.queryParams();
			String bookID = queryParams.get("bookID");

			MySQLPool client = new Connector().run(vertx);
			new Books().bookName(client, bookID);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Book " + bookID);

		});

		router.route("/people").handler(context -> {

			MultiMap queryParams = context.queryParams();
			String personID = queryParams.get("personID");

			HttpServerResponse response = context.response();

			MySQLPool client = new Connector().run(vertx);
			new People().personDetails(client, personID);

			response.putHeader("content-type", "text/plain");
			response.end("Person " + personID);

		});

		server
			.requestHandler(router)
			.listen(8888, http -> {
			if (http.succeeded()) {
				System.out.println("HTTP server started on port " + server.actualPort());
			} else {
				System.out.println("HTTP server failed to start");
			}
		});
	}

}
