package gamefully.service;

import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publisher
{
    private static final URI BASE_URI = URI.create("https://0.0.0.0:9090/gamefully/");
    private static final Logger logger = Logger.getLogger(Publisher.class.getName());
    private static final String KEYSTORE_LOC = "./keystore_server";
    private static final String KEYSTORE_PASS= "keystorePass";

    private static final String TRUSTSTORE_LOC = "./truststore_server";
    private static final String TRUSTSTORE_PASS = "truststorePass";

    public static void main(String[] args)
    {
        try {
            SSLContextConfigurator sslCon = new SSLContextConfigurator();

            // set up security context
            sslCon.setKeyStoreFile(KEYSTORE_LOC);
            sslCon.setKeyStorePass(KEYSTORE_PASS);

            sslCon.setTrustStoreFile(TRUSTSTORE_LOC);
            sslCon.setTrustStorePass(TRUSTSTORE_PASS);

            CustomApplicationConfig customApplicationConfig = new CustomApplicationConfig();
            // create and start a grizzly server
            GrizzlyHttpServerFactory.createHttpServer(BASE_URI, customApplicationConfig,true, new SSLEngineConfigurator(sslCon).setClientMode(false).setNeedClientAuth(false));

            logger.log(Level.INFO,"Try the following GET operations in your internet browser: ");
            String[] getOperations = {BASE_URI.toURL() + "test/hello"};
            for (String getOperation : getOperations) {
                logger.log(Level.INFO, getOperation);
            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }
}
