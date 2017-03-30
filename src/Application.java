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
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveType;
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

        // Preprocess by removing string attributes
        RemoveType rt = new RemoveType();
        rt.setInputFormat(rawSet);
        Instances preprocessedSet = Filter.useFilter(rawSet, rt);

        // Preprocess by removing unessesary features
        Remove r = new Remove();
        String[] rOptions = new String[2];
        rOptions[0] = "-R";
        rOptions[1] = "1,2";
        //rOptions[2] = "1";
        r.setOptions(rOptions);
        r.setInputFormat(rawSet);
        preprocessedSet = Filter.useFilter(rawSet, r);

        RemovePercentage rp_train = new RemovePercentage();
        rp_train.setOptions(options_train);
        rp_train.setInputFormat(preprocessedSet);
        RemovePercentage rp_val = new RemovePercentage();
        rp_val.setOptions(options_val);
        rp_val.setInputFormat(preprocessedSet);
        Instances train_data = Filter.useFilter(preprocessedSet, rp_train);   // apply filter
        Instances val_data = Filter.useFilter(preprocessedSet, rp_val);   // apply filter
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




    /*public void removeFeatures(String inputFolder, String[] features) {

        File inFolder = new File(inputFolder);
        String parentPath = inFolder.getParentFile().getPath();
        String outFolderName = "/DEV_PREPROCESS";
        File outFolder = new File(parentPath + outFolderName);
        int[] featIndex = new int[features.length];

        if (!outFolder.exists()) {
            try {
                outFolder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try(Stream<Path> paths = Files.walk(Paths.get(inputFolder))) {

            paths.forEach(filePath -> {

                if (Files.isRegularFile(filePath)) {

                    File arffFile = filePath.toFile();

                    try {
                        BufferedWriter writer = new BufferedWriter(
                                new FileWriter(outFolder.getPath() + "/" + arffFile.getName()));
                        FileInputStream fStream = new FileInputStream(arffFile);
                        BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
                        int lineCounter = 0;
                        int attribute

                        while (in.ready()) {
                            String line = in.readLine();
                            lineCounter ++;

                            if (line.startsWith("@attribute")) {
                                for (int i=0; i<features.length; i++) {
                                    if (line.split(" ")[1].toLowerCase().equals(features[i].toLowerCase())) {
                                        featIndex[i] = ;
                                    }
                                }
                            }

                            writer.write(line);
                        }
                        writer.close();
                        in.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    System.out.println(filePath);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("output folder " + parentPath);
        *//*try {
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
        }*//*

    }*/

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
