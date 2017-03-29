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
        // wekaApp.createTrainAndValidFile(inFolder, outFolder, fileName);

        wekaApp.removeFeatures(inFolder, null);




    }

}
