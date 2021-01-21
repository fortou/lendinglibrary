package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;

public class Books {

	public void bookName(MySQLPool client, String bookID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  SqlConnection conn = ar1.result();
			  conn
				.query("SELECT name FROM books WHERE id = '" + bookID + "'")
				.execute(ar -> {
					if (ar.succeeded()) {
						RowSet<Row> row = ar.result();
						System.out.println("Book name: " + ((Row) row).getString(1));

					} else {
						System.out.println("Failure: " + ar.cause().getMessage());
					}
				});
			}
		});

	}
	
}
