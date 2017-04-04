import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

import java.io.File;

/**
 * Created by nandane on 09/02/17.
 */
public class Main {

    public static void main(String[] args) throws java.lang.Exception {

        Application wekaApp = new Application();

        String fileName = "MSD-JMIRMOMENTS_dev";
        String inFolder = "/home/nandane/Documents/Cours_ETS_MTL/LOG770_Intelligence_machine/LAB4/DEVNEW/";
        String outFolder = "/home/nandane/Documents/Cours_ETS_MTL/LOG770_Intelligence_machine/LAB4/DEV_PREPARED/"
                + fileName.split("_")[0].toUpperCase() + "/";

        //Prepocess of the dataset to create
        //training and validation sets
        //wekaApp.createTrainAndValidFile(inFolder, outFolder, fileName);

        System.out.println("Hello word !");

        File train;
        File valid;

        if (args.length == 0) {
            train = new File("assets/msd-jmirmoments_dev_train.arff");
            valid = new File("assets/msd-jmirmoments_dev_val.arff");
        } else {
            train = new File(args[0]);
            valid = new File(args[1]);
        }


        Instances training = wekaApp.loadInstancesFromFile(train);
        Instances validation = wekaApp.loadInstancesFromFile(valid);

        NaiveBayes c = wekaApp.createBayesClassifier(training);

        Evaluation e = wekaApp.classify(c, training, validation);

        System.out.println("summary string");
        System.out.println(e.toSummaryString());
        System.out.println("class details");
        System.out.println(e.toClassDetailsString());
        System.out.println("matrix");
        System.out.println(e.toMatrixString());



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
