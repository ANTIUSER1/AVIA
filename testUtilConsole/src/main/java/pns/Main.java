package pns;

import pns.inputs.attraction.TestEquals;
import pns.utility.ReqestResponseReader;

public class Main {

    public static void main(String[] args) throws Exception {


        if (args.length == 4) {
            ReqestResponseReader reqestResponseReader =
                    ReqestResponseReader.of(args[0], args[1],
                            args[2], args[3]);
            TestEquals data = TestEquals.of(
                    reqestResponseReader.getDataOriginal(),
                    reqestResponseReader.getDataTemplate(),
                    args[0], args[1],
                    args[2], args[3]
            );
            data.compareJSONS();

        }

    }
}
