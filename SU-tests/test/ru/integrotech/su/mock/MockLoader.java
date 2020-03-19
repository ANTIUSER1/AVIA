package ru.integrotech.su.mock;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import ru.integrotech.airline.register.RegisterCache;
import ru.integrotech.airline.register.RegisterLoader;
import ru.integrotech.airline.utils.PropertyHolder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


/**
 * Class is used in tests instead listOf RegisterLoader. Combines loading JSONs from local
 * files and outside for use in tests. Paths to JSONs saved in ikm-mock.properties
 */
public class MockLoader{

    private static final String PROPS_PATH = "test/ru/integrotech/su/resources/ikm-mock.properties";

    private static MockLoader instance;

    private PropertyHolder props;

    private TestsCache testsCache;

    public static MockLoader getInstance() {
        if (instance == null) {
            instance = new MockLoader();
        }

        return instance;
    }

    public void updateRegisters(REGISTERS_TYPE type, String...registerNames) {
        this.props = new PropertyHolder(getProperties(PROPS_PATH));

        if (type == REGISTERS_TYPE.MOCK) {
            this.testsCache = new TestsCache();
            String registryDir = props.getRegistryServiceBase();
            try {
                for (String registerName : registerNames) {
                    String filePath = String.format("%s%s%s", registryDir, registerName, ".json");
                    this.testsCache.update(registerName, loadJson(filePath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == REGISTERS_TYPE.REAL) {
            RegisterLoader registerLoader = RegisterLoader.updateInstance(registerNames);
            this.testsCache = new TestsCache(registerLoader.getRegisterCache());
        }
    }


    private MockLoader() {
    }

    public TestsCache getTestsCache() {
        return testsCache;
    }

    public RegisterCache getRegisterCache() {
        return testsCache.getRegisterCache();
    }

    private static Properties getProperties(String filePath) {
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream(filePath);
            props.load(is);
            is.close();
        } catch (Exception e) {
        e.printStackTrace();
    }
        return props;
    }

    public JsonElement loadJson(String filePath) throws JsonIOException, JsonSyntaxException, IOException {
        InputStream is = new FileInputStream(filePath);
        return this.parseJson(is);
    }

    private JsonElement parseJson(InputStream is) throws JsonIOException, JsonSyntaxException, IOException {
        JsonParser jp = new JsonParser();
        return jp.parse(new InputStreamReader(is, this.props.getEncoding()));
    }

    public enum REGISTERS_TYPE {
        REAL,
        MOCK
    }
}
