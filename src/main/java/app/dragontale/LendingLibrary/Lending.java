package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.SqlConnection;

public class Lending {
	
	public void addEntry(MySQLPool client, String bookID, String lenderID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  	SqlConnection conn = ar1.result();
			  	conn
				.query("INSERT INTO lending(bookID,lenderID,state) VALUES ('" + bookID + "','" + lenderID + "','OPEN')")
				.execute(ar2 -> {
					if (ar2.succeeded()) {
						System.out.println("Book lended.");
					} else {
						System.out.println("Failure: " + ar2.cause().getMessage());
					}
				});
				conn
				.query("UPDATE books SET lenderID = '" + lenderID + "' WHERE id ='" + bookID + "'")
				.execute(ar3 -> {
					if (ar3.succeeded()) {
						System.out.println("Library registry updated.");
					} else {
						System.out.println("Failure: " + ar3.cause().getMessage());
					}
				});
			}
		});
	}

	public void closeEntry(MySQLPool client, String entryID, String bookID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  	SqlConnection conn = ar1.result();
			  	conn
			  	.query("UPDATE lending SET state = 'CLOSED' WHERE id = '" + entryID + "'")
				.execute(ar2 -> {
					if (ar2.succeeded()) {
						System.out.println("Book returned.");
					} else {
						System.out.println("Failure: " + ar2.cause().getMessage());
					}
				});
				conn
			  	.query("UPDATE books SET lenderID = '0' WHERE id = '" + bookID + "'")
				.execute(ar2 -> {
					if (ar2.succeeded()) {
						System.out.println("Library registry updated.");
					} else {
						System.out.println("Failure: " + ar2.cause().getMessage());
					}
				});
			}
		});
	}
}