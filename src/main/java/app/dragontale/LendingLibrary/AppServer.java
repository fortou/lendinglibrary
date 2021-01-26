package app.dragontale.LendingLibrary;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLPool;

public class AppServer {

	public void run(Vertx vertx) {

		HttpServer server = vertx.createHttpServer();

		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());

		MySQLPool client = new Connector().run(vertx);

		router.route("/").handler(context -> {

			HttpServerResponse response = context.response();

			vertx.fileSystem().readFile("C:/Users/Nikos/Desktop/Java/LendingLibrary/src/main/java/app/dragontale/LendingLibrary/resources/Greeting.txt", ar -> {
				if (ar.succeeded()) {

					response.putHeader("content-type", "text/plain");
					response.end(ar.result());

					System.out.println(" ");
					System.out.println("Greeting.txt loaded.");

				} else {

					response.putHeader("content-type", "text/plain");
					response.end("Something went wrong...");

					System.out.println(" ");
					System.err.println("Error while reading from file: " + ar.cause().getMessage());
				}
			});

		});

		router.route("/lendings/borrow").handler(context -> {
			
			MultiMap queryParams = context.queryParams();
			String bookID = queryParams.get("bookID");
			String personID = queryParams.get("personID");
			String pinCode = queryParams.get("pinCode");

			new LendingAPI().addEntry(client, bookID, personID, pinCode);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

		});

		router.route("/lendings/return").handler(context -> {
			
			MultiMap queryParams = context.queryParams();
			String lendingID = queryParams.get("lendingID");
			String bookID = queryParams.get("bookID");

			new LendingAPI().closeEntry(client, lendingID, bookID);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

		});

		router.route("/lendings/list").handler(context -> {

			HttpServerResponse response = context.response();

			//MySQLPool client = new Connector().run(vertx);
			//new LendingAPI().personDetails(client, personID, pinCode);

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

		});

		router.route("/lendings").handler(context -> {

			HttpServerResponse response = context.response();

			MultiMap queryParams = context.queryParams();
			String lendingID = queryParams.get("lendingID");
			String personID = queryParams.get("personID");
			String pinCode = queryParams.get("pinCode");

			new LendingAPI().retrieveEntry(client, lendingID, personID, pinCode);

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

		});

		router.route("/books").handler(context -> {

			MultiMap queryParams = context.queryParams();
			String bookID = queryParams.get("bookID");

			new BooksAPI().bookTitle(client, bookID);

			HttpServerResponse response = context.response();

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

		});

		router.route("/people").handler(context -> {

			MultiMap queryParams = context.queryParams();
			String personID = queryParams.get("personID");
			String pinCode = queryParams.get("pinCode");

			HttpServerResponse response = context.response();

			new PeopleAPI().personDetails(client, personID, pinCode);

			response.putHeader("content-type", "text/plain");
			response.end("Observe system output for request status.");

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
