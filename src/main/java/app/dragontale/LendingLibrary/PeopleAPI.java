package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

public class PeopleAPI {

	public void personDetails(MySQLPool client, String personID, String pinCode){

		client.getConnection(ar1 -> {
			if (ar1.succeeded()) {
				SqlConnection conn = ar1.result();
			  	conn
			  	.preparedQuery("SELECT * FROM people WHERE person_id = ?")
				.execute(Tuple.of(personID), ar2 -> {
					if (ar2.succeeded()) {
						
						if (ar2.result().iterator().hasNext() == true) {

							RowSet<Row> rowSet = ar2.result();
							Row row = rowSet.iterator().next();
							String value1 = row.getValue(1).toString();
							Object value2 = row.getValue(2);
							Object value3 = row.getValue(3);
							Object value4 = row.getValue(4);
							Object value5 = row.getValue(5);

							if (pinCode.compareTo(value1) == 0){

								System.out.println(" ");
								System.out.println("User authenticated. Listing information...");

								System.out.println(" ");
								System.out.println("Details about person with ID: " + personID);
								System.out.println("This person's full name: " + value2 + " " + value3);
								System.out.println("Number of borrowed books is: " + value4);
								System.out.println("Is a staff member: " + value5);
								conn.close();

							}else{

								System.out.println(" ");
								System.out.println("User authentication failed.");
								conn.close();
							}


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
