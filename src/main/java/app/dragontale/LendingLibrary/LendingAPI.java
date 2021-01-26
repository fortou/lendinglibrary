package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.SqlConnection;

public class LendingAPI {
	
	public void addEntry(MySQLPool client, String bookID, String personID, String pinCode){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  	SqlConnection conn = ar1.result();
			  	conn
				.query("INSERT INTO lending(book_id,person_id,lend_state) VALUES ('" + bookID + "','" + personID + "','OPEN')")
				.execute(ar2 -> {
					if (ar2.succeeded()) {

						System.out.println(" ");
						System.out.println("Book lended.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar2.cause().getMessage());
						conn.close();
					}
				});
				conn
				.query("UPDATE books SET available = 'NO' WHERE book_id ='" + bookID + "'")
				.execute(ar3 -> {
					if (ar3.succeeded()) {

						System.out.println(" ");
						System.out.println("Library registry updated.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar3.cause().getMessage());
						conn.close();
					}
				});
			}
		});
	}

	public void closeEntry(MySQLPool client, String lendingID, String bookID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  	SqlConnection conn = ar1.result();
			  	conn
			  	.query("UPDATE lending SET lend_state = 'CLOSED' WHERE book_id = '" + lendingID + "'")
				.execute(ar2 -> {
					if (ar2.succeeded()) {

						System.out.println(" ");
						System.out.println("Book returned.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar2.cause().getMessage());
						conn.close();
					}
				});
				conn
			  	.query("UPDATE books SET available = 'YES' WHERE book_id = '" + bookID + "'")
				.execute(ar3 -> {
					if (ar3.succeeded()) {

						System.out.println(" ");
						System.out.println("Library registry updated.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar3.cause().getMessage());
						conn.close();
					}
				});
			}
		});
	}

	public void retrieveEntry(MySQLPool client, String lendingID, String personID, String pinCode){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  	SqlConnection conn = ar1.result();
			  	conn
			  	.query("UPDATE lending SET lend_state = 'CLOSED' WHERE book_id = '" + lendingID + "'")
				.execute(ar2 -> {
					if (ar2.succeeded()) {

						System.out.println(" ");
						System.out.println("Book returned.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar2.cause().getMessage());
						conn.close();
					}
				});
				conn
			  	.query("UPDATE books SET available = 'YES' WHERE book_id = ''")
				.execute(ar3 -> {
					if (ar3.succeeded()) {

						System.out.println(" ");
						System.out.println("Library registry updated.");
						conn.close();

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar3.cause().getMessage());
						conn.close();
					}
				});
			}
		});
	}
}