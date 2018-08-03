import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {


		final HttpServer httpServer = HttpServer
				.builder()
				.port(8090)
				.build();

		Hooks.onOperatorDebug();
		final Consumer<HttpServerRoutes> serverRoutes = routes ->
				routes.get("/test/{param}", (req, res) -> {
							return res
									.sendString(Mono.just(' ' + req.param("param") + '!'));
						}
				);


		httpServer
				.startRouterAndAwait(serverRoutes, blockingNettyContext -> System.out.println("Started"));


	}
}