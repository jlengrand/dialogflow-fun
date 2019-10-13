import java.io.IOException;
import java.util.List;

import com.google.cloud.dialogflow.v2beta1.EntityType;
import com.google.cloud.dialogflow.v2beta1.EntityTypeName;
import com.google.cloud.dialogflow.v2beta1.EntityTypesClient;
import com.google.cloud.dialogflow.v2beta1.ProjectAgentName;
import com.google.cloud.dialogflow.v2beta1.Agent;
import com.google.cloud.dialogflow.v2beta1.AgentsClient;
import com.google.cloud.dialogflow.v2beta1.ProjectName;
import com.google.cloud.dialogflow.v2beta1.EntityType.Entity;

public class CreateAgent {

    private static final String GOOGLE_AUTH_ENV_NAME = "GOOGLE_APPLICATION_CREDENTIALS";
    private static final String PROJECT_NAME = "dialogflow-fun";
    private static final String AGENT_NAME = "dialogflow-fun-agent";
    private static final String AGENT_ID = "ac522b80-e75b-40cd-9493-269fbb4ef634";
    private static final String ENTITY_TYPE_ID = "Developer";

    public static void main(String[] args) {

        if (!googleAuthOk()) {
            System.out.println("Error with Google Authentication. Exiting...");
            System.exit(0);
        }

        getAgentInfo();
        getEntitiesInfo();

        System.out.println("Done");
    }

    public static void getEntitiesInfo() {

        // ENTITY TYPES
        // https://googleapis.dev/java/google-cloud-clients/latest/index.html?com/google/cloud/dialogflow/v2/package-summary.html
        // * **System** - entities that are defined by the Dialogflow API for common
        // data types such as date, time, currency, and so on. A system entity is
        // represented by the `EntityType` type.
        // * **Developer** - entities that are defined by you that represent actionable
        // data that is meaningful to your application. For example, you could define a
        // `pizza.sauce` entity for red or white pizza sauce, a `pizza.cheese` entity
        // for the different types of cheese on a pizza, a `pizza.topping` entity for
        // different toppings, and so on. A developer entity is represented by the
        // `EntityType` type.
        // * **User** - entities that are built for an individual user such as
        // favorites, preferences, playlists, and so on. A user entity is represented by
        // the [SessionEntityType][google.cloud.dialogflow.v2.SessionEntityType] type.

        System.out.println("==========");
        System.out.println("Getting Entities info");

        try {
            EntityTypesClient entityTypesClient = EntityTypesClient.create();
            ProjectAgentName parent = ProjectAgentName.of(PROJECT_NAME);
            for (EntityType entityType : entityTypesClient.listEntityTypes(parent).iterateAll()) {
                System.out.println(entityType.getDisplayName());
                List<Entity> entities = entityType.getEntitiesList();
                entities.forEach(CreateAgent::printEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==========");

    }

    public static void printEntity(Entity entity) {
        System.out.println(entity.getValue());
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