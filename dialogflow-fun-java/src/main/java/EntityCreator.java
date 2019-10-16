import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.cloud.dialogflow.v2beta1.EntityType;
import com.google.cloud.dialogflow.v2beta1.EntityTypesClient;
import com.google.cloud.dialogflow.v2beta1.ProjectAgentName;
import com.google.cloud.dialogflow.v2beta1.EntityType.Entity;
import com.google.cloud.dialogflow.v2beta1.EntityType.Kind;

import data.ReadNames;

public class EntityCreator {

    private static final String CONTACT_ENTITY_NAME = "names_composite";
    private static final String PROJECT_NAME = "dialogflow-fun";

    private ReadNames namesReader = new ReadNames();

    public void createContactEntityType() {
        try {
            EntityTypesClient entityTypesClient = EntityTypesClient.create();
            ProjectAgentName parent = ProjectAgentName.of(PROJECT_NAME);

            List<String> names = namesReader.getListOfNames();
            List<Entity> contactEntities = names.stream().map(n -> Entity.newBuilder().setValue(n).build())
                    .collect(Collectors.toList());

            EntityType contactEntityType = EntityType.newBuilder().setDisplayName(CONTACT_ENTITY_NAME)
                    .setKind(Kind.KIND_MAP).addAllEntities(contactEntities).build();

            entityTypesClient.createEntityType(parent, contactEntityType);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteAllEntityTypes() throws IOException {
        EntityTypesClient entityTypesClient = EntityTypesClient.create();
        ProjectAgentName parent = ProjectAgentName.of(PROJECT_NAME);

        for (EntityType entityType : entityTypesClient.listEntityTypes(parent).iterateAll()) {
            System.out.println("Deleting " + entityType.getDisplayName() + " with name " + entityType.getName());
            entityTypesClient.deleteEntityType(entityType.getName());
        }

    }

    public void getEntitiesInfo() {

        System.out.println("==========");
        System.out.println("Getting Entities info");

        try {
            EntityTypesClient entityTypesClient = EntityTypesClient.create();
            ProjectAgentName parent = ProjectAgentName.of(PROJECT_NAME);
            for (EntityType entityType : entityTypesClient.listEntityTypes(parent).iterateAll()) {
                System.out.println(entityType.getDisplayName());
                List<Entity> entities = entityType.getEntitiesList();
                entities.forEach(EntityCreator::printEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==========");

    }

    public static void printEntity(Entity entity) {
        System.out.println(entity.getValue());
    }

}