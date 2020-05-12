package pns.inputs.attraction;

import pns.utility.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestEquals {

    public static TestEquals of(
            String jsonrOriginal, String jsonTemplate,
            String fileOriginal, String fileTemplate,
            String rootOriginal, String rootTemplate) {
        TestEquals res = new TestEquals();

        res.dataOriginal = jsonrOriginal;
        res.dataTemplate = jsonTemplate;
        res.fileOriginal = fileOriginal;
        res.fileTemplate = fileTemplate;
        res.rootTemplate = rootTemplate;
        res.rootOriginal = rootOriginal;


        return res;
    }

    private String dataOriginal = "";
    private String dataTemplate = "";

    private String fileOriginal = "";
    private String fileTemplate = "";

    private String rootOriginal = "";
    private String rootTemplate = "";

    private TestEquals() {

    }

    public String getDataOriginal() {
        return dataOriginal;
    }

    public String getDataTemplate() {
        return dataTemplate;
    }

    public String getFileOriginal() {
        return fileOriginal;
    }

    public String getFileTemplate() {
        return fileTemplate;
    }

    public String getRootOriginal() {
        return rootOriginal;
    }

    public String getRootTemplate() {
        return rootTemplate;
    }

    public void compareJSONS() throws Exception {
        System.out.println(new Date() + "        Test comparation for files " + System.lineSeparator()
                + fileOriginal + " [Root " + rootOriginal + "]" + System.lineSeparator()
                + "   with content: " + System.lineSeparator()
                + dataOriginal + System.lineSeparator() + System.lineSeparator()
                + "  and " + System.lineSeparator()
                + fileTemplate + " [Root " + rootTemplate + "]" + System.lineSeparator()
                + "   with content: " + System.lineSeparator()
                + dataTemplate + System.lineSeparator()
        );

        List<String> listOrigin = Utils.makeClearData(dataOriginal);
        List<String> listTemplate = Utils.makeClearData(dataTemplate);

        Map<Integer, String> comparingData = Utils.compareList(listOrigin, listTemplate);

        List<Integer> keysList = new ArrayList<>(comparingData.keySet());
        List<String> valList = new ArrayList<>(comparingData.values());
        int key = keysList.get(0);

        String jsonEquals = new Date() + " " + valList.get(0) + System.lineSeparator()
                + "    ... ... ...    ... ... ...    ... ... ... ";

        if (key == 0) {
            System.out.println(jsonEquals);
            System.out.println("ALL DONE");
        } else {
            Exception e = new Exception("  ---   " + jsonEquals + "  ---");
            System.out.println(jsonEquals);

            throw e;
        }
    }

    @Override
    public String toString() {
        return "TestEquals{" +
                "dataOriginal='" + dataOriginal + '\'' +
                ", dataTemplate='" + dataTemplate + '\'' +
                ", fileOriginal='" + fileOriginal + '\'' +
                ", fileTemplate='" + fileTemplate + '\'' +
                '}';
    }
}
