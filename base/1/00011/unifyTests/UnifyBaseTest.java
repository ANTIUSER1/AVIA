package ru.aeroflot.fmc.unifyTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.aeroflot.fmc.mock.MockLoader;
import ru.aeroflot.fmc.register.RegisterLoader;
import ru.aeroflot.fmc.test.CommonTest;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/*Base class for unify tests*/
public abstract class UnifyBaseTest {

    protected static final String ENCODING_FORMAT = "UTF-8";
    protected static final String PROPERTIES = "unifyTest.properties";
    protected static final String REQUEST_FILE_NAME = "request.json";
    protected static final String EXPECTED_RESPONSE_FILE_NAME = "expectedResponse.json";
    protected static final String ACTUAL_RESPONSE_FILE_NAME = "actualResponse.json";

    final CommonTest common;


    public <T extends Comparable> UnifyBaseTest(MockLoader loader, Class<T> type) {
        this.common = CommonTest.of(loader, type);
    }

    public <T extends Comparable> UnifyBaseTest(Class<T> type) {
        this.common = CommonTest.of(type);

    }

    protected boolean executeTest(String rootTestDirectoryPath) {
        InputStream is = RegisterLoader.class.getClassLoader().getResourceAsStream(PROPERTIES);
        int testCaseCounter = 0;
        int properTestCaseCounter = 0;

        if (is != null) {
            Properties props = new Properties();
            try {
                props.load(is);
                String rootDirectoryPath = props.getProperty(rootTestDirectoryPath);
                File rootDirectory = new File(rootDirectoryPath);
                if (rootDirectory.exists() && rootDirectory.isDirectory()) {
                    for (File file : Objects.requireNonNull(rootDirectory.listFiles())) {
                        if (file.isDirectory()) {
                            testCaseCounter++;
                            if (isCorrectCase(String.format("%s%s", file.getAbsolutePath(), "\\"))) {
                                properTestCaseCounter++;
                            }
                        }
                    }
                } else {
                    System.out.printf("Root charge test directory is not exist or not a folder: %s", rootDirectoryPath);
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testCaseCounter == properTestCaseCounter;
    }

    protected abstract boolean isCorrectCase(String pathToCaseFolder) throws IOException;

    protected <T> void writeActualResponseToFile(List<T> routes, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, ENCODING_FORMAT);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(routes);
        writer.println(jsonResult);
        writer.close();
    }

    protected boolean removeRedundantFile(String fileName) {
        File redundantFile = new File(fileName);
        if (redundantFile.exists()) {
            return redundantFile.delete();
        } else return false;
    }

    protected <T> void printTestResults(boolean testIsOK, List<T> actualRoutes, String pathToCaseFolder) throws FileNotFoundException, UnsupportedEncodingException {
        if (testIsOK) {
            this.removeRedundantFile( String.format("%s%s%s", pathToCaseFolder, "/", ACTUAL_RESPONSE_FILE_NAME));
            System.out.printf("Case in directory %s is OK\n", pathToCaseFolder);
        } else {
            this.writeActualResponseToFile(actualRoutes, String.format("%s%s%s", pathToCaseFolder, "/", ACTUAL_RESPONSE_FILE_NAME));
            System.out.printf("Case in directory %s is not correct. See actual response in this directory in %s\n", pathToCaseFolder, ACTUAL_RESPONSE_FILE_NAME);
        }
    }

}
