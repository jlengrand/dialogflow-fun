import java.io.IOException;

import com.google.cloud.dialogflow.v2beta1.Agent;
import com.google.cloud.dialogflow.v2beta1.AgentsClient;
import com.google.cloud.dialogflow.v2beta1.ProjectName;

public class CreateAgent {

    private static final String GOOGLE_AUTH_ENV_NAME = "GOOGLE_APPLICATION_CREDENTIALS";
    // private static final String AGENT_NAME = "dialogflow-fun-agent";
    // private static final String AGENT_ID =
    // "ac522b80-e75b-40cd-9493-269fbb4ef634";
    // private static final String ENTITY_TYPE_ID = "Developer";

    private static EntityCreator entityCreator = new EntityCreator();

    public static void main(String[] args) throws IOException {

        if (!googleAuthOk()) {
            System.out.println("Error with Google Authentication. Exiting...");
            System.exit(0);
        }

        entityCreator.deleteAllEntityTypes();
        entityCreator.createContactEntityType();

        System.out.println("Done");
    }

    public static void getAgentInfo() {
        System.out.println("==========");
        System.out.println("Getting Agent info");

        try {
            AgentsClient agentsClient = AgentsClient.create();
            Agent myAgent = agentsClient.getAgent(ProjectName.of("dialogflow-fun"));
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
