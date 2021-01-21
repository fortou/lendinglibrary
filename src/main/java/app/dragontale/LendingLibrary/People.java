package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;

public class People {

	public void personDetails(MySQLPool client, String personID){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
				SqlConnection conn = ar1.result();
			  	conn
			  	.query("SELECT isStaff FROM people WHERE id= '" + personID + "'")
				.execute(ar2 -> {
					if (ar2.succeeded()) {
						RowSet<Row> row = ar2.result();
						System.out.println("This person a staff member: " + ((Row) row).getString(1));
					} else {
						System.out.println("Failure: " + ar2.cause().getMessage());
					}
				});
			  	conn
			  	.query("SELECT lendedBooks FROM people WHERE id= '" + personID + "'")
				.execute(ar3 -> {
					if (ar3.succeeded()) {
						RowSet<Row> row = ar3.result();
						System.out.println("Number of lended books is: " + ((Row) row).getString(1));
					} else {
						System.out.println("Failure: " + ar3.cause().getMessage());
					}
				});
				conn
			  	.query("SELECT isStaff FROM people WHERE id= '" + personID + "'")
				.execute(ar4 -> {
					if (ar4.succeeded()) {
						RowSet<Row> row = ar4.result();
						System.out.println("This person a staff member: " + ((Row) row).getString(1));
					} else {
						System.out.println("Failure: " + ar4.cause().getMessage());
					}
				});
			}
		});
	}	
}
