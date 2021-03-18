package winepairer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;


public class App {
    public static void main(String[] args) throws Exception {
        Server server = startServer(8080);
        ServletContextHandler context = createStatefulContext(server);
        registerServlets(context);

        server.start();
        System.out.println("Started server.");
        System.out.println("Listening on http://localhost:8080/");
        System.out.println("Press CTRL+C to exit.");
        server.join();
    }

    private static Server startServer(int port) {
        return new Server(port);
    }

    private static ServletContextHandler createStatefulContext(Server server) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        return context;
    }

    private static void registerServlets(ServletContextHandler context) {
        // Use the Jersey framework to translate the classes in the
        // winepairer.api package to server endpoints (servlets).
        // For example, the ObtainWinepairing class will become an endpoint at
        // http://localost:8080/winepairer/api/pairwine
        ServletHolder serverHolder = context.addServlet(ServletContainer.class, "/winepairer/api/*");
        serverHolder.setInitOrder(1);
        serverHolder.setInitParameter("jersey.config.server.provider.packages", 
                "winepairer.api");
    }
}