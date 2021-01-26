package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

public class BooksAPI {

	public void bookTitle (MySQLPool client, String bookID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
			  SqlConnection conn = ar1.result();
			  conn
				.preparedQuery("SELECT title,available FROM books WHERE book_id = ?")
				.execute(Tuple.of(bookID), ar2 -> {

					if (ar2.succeeded()) {

						if (ar2.result().iterator().hasNext() == true) {

							RowSet<Row> rowSet = ar2.result();
							Row row = rowSet.iterator().next();
							Object value1 = row.getValue(0);
							Object value2 = row.getValue(1);
							System.out.println(" ");
							System.out.println("Book title: " + value1);
							System.out.println("Is available: " + value2);
							conn.close();

						}else{

							System.out.println(" ");
							System.out.println("The ID does not exist.");
							conn.close();
						}

					} else {

						System.out.println(" ");
						System.out.println("Failure: " + ar2.cause().getMessage());
						conn.close();
					}
				});
			}
		});
	}
}