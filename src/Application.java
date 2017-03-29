import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * Created by nandane on 09/02/17.
 */
public class Application {

    public Instances loadArffFile(File arffFile) throws java.io.IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arffFile));
        Instances data = new Instances(reader);

        reader.close();
        // set class attributea
        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    public void createTrainAndValidFile(String inFolder, String outFolder, String fileName)
            throws java.lang.Exception {

        String train_suffixe = "_train";
        String val_suffixe = "_val";
        String extension = ".arff";
        File rawFile = new File(inFolder + fileName + extension);
        File trainFile = new File(outFolder + fileName + train_suffixe + extension);
        File valFile = new File(outFolder + fileName + val_suffixe + extension);
        String[] options_train = new String[2];
        options_train[0] = "-P";            // remove 25%
        options_train[1] = "25";            // remove 25%
        String[] options_val = new String[3];
        options_val[0] = "-P";              // remove 25%
        options_val[1] = "25";              // remove 25%
        options_val[2] = "-V";              // invert selection

        // Load the training sets
        Instances rawSet = loadArffFile(rawFile);
        RemovePercentage rp_train = new RemovePercentage();
        rp_train.setOptions(options_train);
        rp_train.setInputFormat(rawSet);
        RemovePercentage rp_val = new RemovePercentage();
        rp_val.setOptions(options_val);
        rp_val.setInputFormat(rawSet);
        Instances train_data = Filter.useFilter(rawSet, rp_train);   // apply filter
        Instances val_data = Filter.useFilter(rawSet, rp_val);   // apply filter
        System.out.println("finished loading data");

        // Save the filtered data sets
        ArffSaver saver = new ArffSaver();
        // train data set
        saver.setInstances(train_data);
        saver.setFile(trainFile);
        saver.writeBatch();
        System.out.println("saved training dataset");
        // val data set
        saver.setInstances(val_data);
        saver.setFile(valFile);
        saver.writeBatch();
        System.out.println("saved validation dataset");

    }



    public void removeFeatures(String inputFolder, String[] features, String outputFolder) {

        try(Stream<Path> paths = Files.walk(Paths.get(inputFolder))) {

            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {

                    File file = filePath.toFile();



                    System.out.println(filePath);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("./" + outputFileName));

            FileInputStream fStream = new FileInputStream(arffFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
            boolean firstline = true;
            while (in.ready()) {
                String line = in.readLine();

                if (!line.isEmpty() && !line.startsWith("@")) {
                    if (firstline) firstline = false;
                    else writer.newLine();

                    String[] arr = line.split(",");
                    writer.write(arr[arr.length - 1]);
                }
            }
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println("File input error");
        }*/

    }

    public void generateTextDataFile(File arffFile, String outputFileName) {

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("./" + outputFileName));

            FileInputStream fStream = new FileInputStream(arffFile);
            BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
            boolean firstline = true;
            while (in.ready()) {
                String line = in.readLine();

                if (!line.isEmpty() && !line.startsWith("@")) {
                    if (firstline) firstline = false;
                    else writer.newLine();

                    String[] arr = line.split(",");
                    writer.write(arr[arr.length - 1]);
                }
            }
            writer.close();
            in.close();
        } catch (IOException e) {
            System.out.println("File input error");
        }
    }
}
