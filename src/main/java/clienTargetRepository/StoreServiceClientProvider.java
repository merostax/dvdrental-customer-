package clienTargetRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import util.Hrefs;

@ApplicationScoped
public class StoreServiceClientProvider {
    private Client client;
    private WebTarget storeServiceTarget;
    public StoreServiceClientProvider() {
        client = ClientBuilder.newClient();
        this.storeServiceTarget = client.target(Hrefs.STORE.getHref());
    }

    public WebTarget getStoreServiceTarget() {
        return storeServiceTarget;
    }
}
