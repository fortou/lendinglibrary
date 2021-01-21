package app.dragontale.LendingLibrary;

import io.vertx.core.AbstractVerticle;

public class Main extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		System.out.println("App starting...");
		new AppServer().run(vertx);
	}

}
