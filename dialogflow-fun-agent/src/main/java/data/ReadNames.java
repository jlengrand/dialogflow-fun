package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadNames {

    private static final String NAMES_FILE_LOCATION = "names_composite";

    public List<String> getListOfNames() throws FileNotFoundException {

        List<String> names = new ArrayList<>();
        File namesFile = new File(getClass().getClassLoader().getResource(NAMES_FILE_LOCATION).getFile());
        Scanner scanner = new Scanner(namesFile);

        while (scanner.hasNextLine()) {
            names.add(scanner.nextLine().trim());
        }

        scanner.close();
        return names;
    }

}