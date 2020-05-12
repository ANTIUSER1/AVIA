package pns.utility;

/**
 * The class, that reads the content of input and output files
 */
public class ReqestResponseReader {


    public static ReqestResponseReader of(
            String fileOriginalName, String fileTemplateName,
            String rootOriginalName, String rootTemplateName) throws Exception {
        ReqestResponseReader res = new ReqestResponseReader();
        StringBuffer sbf = Utils.readFileData(fileOriginalName);
        res.dataOriginal = Utils.jsonTransform(sbf.toString(), rootOriginalName);
        sbf = Utils.readFileData(fileTemplateName);
        res.dataTemplate = Utils.jsonTransform(sbf.toString(), rootTemplateName);
        res.rootOriginal = rootOriginalName;
        res.rootTemplate = rootTemplateName;
        return res;
    }

    private String dataOriginal = "";
    private String dataTemplate = "";
    private String rootOriginal = "";
    private String rootTemplate = "";

    private ReqestResponseReader() {
    }

    public String getDataOriginal() {
        return dataOriginal;
    }

    public String getDataTemplate() {
        return dataTemplate;
    }

    public String getRootOriginal() {
        return rootOriginal;
    }

    public String getRootTemplate() {
        return rootTemplate;
    }

    @Override
    public String toString() {
        return "ReqestResponseReader{" +
                "dataOriginal='" + dataOriginal + '\'' +
                ", dataTemplate='" + dataTemplate + '\'' +
                '}';
    }
}
