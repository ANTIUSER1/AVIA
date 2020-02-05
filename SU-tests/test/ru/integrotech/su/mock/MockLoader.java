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


/*class is used in tests instead listOf RegisterLoader. Combines loading JSONs from local
  files and outside for use in tests. Paths to JSONs saved in ikm-mock.properties*/
public class MockLoader{

    private PropertyHolder props;

    private final TestsCache testsCache;

    /*use restrict mock registers from /resources/registers*/
    public static MockLoader ofMockRegisters(String... registerNames) {
        return new MockLoader(registerNames);
    }

    /*use restrict real registers*/
    public static MockLoader ofRealRegisters(String... registerNames) {
        MockLoader result = null;
        RegisterLoader registerLoader = RegisterLoader.getInstance(registerNames);
        registerLoader.lock();
        result = new MockLoader(registerLoader.getRegisterCache());
        registerLoader.release();
        return result;
    }

    private MockLoader(String... registerNames) {
        this.props = new PropertyHolder(getProperties("test/ru/integrotech/su/resources/ikm-mock.properties"));
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
    }

    private MockLoader(RegisterCache registerCache) {
        this.props = new PropertyHolder(getProperties("test/ru/integrotech/su/resources/ikm-mock.properties"));
        this.testsCache = new TestsCache(registerCache);
    }

    public TestsCache getTestsCache() {
        return testsCache;
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
}
