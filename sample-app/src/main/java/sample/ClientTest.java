package sample;

import com.pipedream.api.PipedreamClient;
import java.util.Optional;

public final class ClientTest {
    public static void main(String[] args) {
PipedreamClient client = PipedreamClient.builder()
                .clientId(System.getenv("PROD_CLIENT_ID"))
                .clientSecret(System.getenv("PROD_CLIENT_SECRET"))
                .projectId(System.getenv("PROD_PROJECT_ID"))
                .build();

        Optional<String> token = client.rawAccessToken();
        System.out.println("Access token: " + token.orElse("(none)"));
    }
}
