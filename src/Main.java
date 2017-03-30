import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by nandane on 09/02/17.
 */
public class Main {

    public static void main(String[] args) throws java.lang.Exception {

        Application wekaApp = new Application();

        String fileName = "msd-trh_dev";
        String inFolder = "/home/nandane/Documents/Cours_ETS_MTL/LOG770_Intelligence_machine/LAB4/DEVNEW/";
        String outFolder = "/home/nandane/Documents/Cours_ETS_MTL/LOG770_Intelligence_machine/LAB4/DEV_PREPARED/"
                + fileName.split("_")[0].toUpperCase() + "/";

        // Prepocess of the dataset to create
        // training and validation sets
        wekaApp.createTrainAndValidFile(inFolder, outFolder, fileName);


        /*try(Stream<Path> paths = Files.walk(Paths.get(inFolder))) {

            paths.forEach(filePath -> {

                if (Files.isRegularFile(filePath)) {

                    File arffFile = filePath.toFile();
                    String fName = arffFile.getName().replace(".arff", "");
                    System.out.println("fname = " + fName.replace(".arff", ""));
                    String outFolderName = "/home/nandane/Documents/Cours_ETS_MTL/LOG770_Intelligence_machine/LAB4/DEV_PREPARED/"
                            + fName.split("_")[0].toUpperCase() + "/";

                    try {
                        wekaApp.createTrainAndValidFile(inFolder, outFolderName, fName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }*/
    }

}
