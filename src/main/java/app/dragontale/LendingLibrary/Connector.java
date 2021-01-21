package app.dragontale.LendingLibrary;

import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Connector extends AbstractVerticle {

	public MySQLPool run(Vertx vertx) {

		MySQLConnectOptions connectOptions = new MySQLConnectOptions()
			.setPort(3306)
			.setHost("127.0.0.1")
			.setDatabase("lendlib_db")
			.setUser("root")
			.setPassword("root");

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		MySQLPool client = MySQLPool.pool(vertx, connectOptions, poolOptions);

		return client;

	}

}
