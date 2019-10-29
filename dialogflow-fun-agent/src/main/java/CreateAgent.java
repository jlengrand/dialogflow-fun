import java.io.IOException;

import com.google.cloud.dialogflow.v2beta1.Agent;
import com.google.cloud.dialogflow.v2beta1.AgentsClient;
import com.google.cloud.dialogflow.v2beta1.ProjectName;

public class CreateAgent {

    private static final String GOOGLE_AUTH_ENV_NAME = "GOOGLE_APPLICATION_CREDENTIALS";
     static final String PROJECT_NAME = "dialogflow-fun-agent-live-jhwm";
//    private static final String PROJECT_NAME = "dialogflow-fun";

    private static EntityCreator entityCreator = new EntityCreator();

    public static void main(String[] args) throws IOException {

        if (!googleAuthOk()) {
            System.out.println("Error with Google Authentication. Exiting...");
            System.exit(0);
        }
        System.out.println(System.getenv().get(GOOGLE_AUTH_ENV_NAME));

        entityCreator.deleteAllEntityTypes();
        entityCreator.createContactEntityType();

        System.out.println("Done");
    }

    public static void getAgentInfo() {
        System.out.println("==========");
        System.out.println("Getting Agent info");

        try {
            AgentsClient agentsClient = AgentsClient.create();
            Agent myAgent = agentsClient.getAgent(ProjectName.of(PROJECT_NAME));
            System.out.println(myAgent.getDisplayName());
            System.out.println(myAgent.getDescription());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==========");
    }

    public static boolean googleAuthOk() {
        return System.getenv().get(GOOGLE_AUTH_ENV_NAME) != null;
    }
}
