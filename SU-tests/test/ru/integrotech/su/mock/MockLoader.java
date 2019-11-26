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

import static ru.integrotech.airline.register.RegisterCache.Type.*;


/*class is used in tests instead listOf RegisterLoader. Combines loading JSONs from local
  files and outside for use in tests. Paths to JSONs saved in test.properties*/
public class MockLoader{

    private PropertyHolder props;

    private final TestsCache testsCache;

    /*use restrict mock registers from /resources/registers*/
    public static MockLoader ofMockRegisters() {
        return new MockLoader();
    }

    /*use restrict real registers*/
    public static MockLoader ofRealRegisters() {
        MockLoader result = null;
        RegisterLoader registerLoader = RegisterLoader.getInstance();
        registerLoader.lock();
        result = new MockLoader(registerLoader.getRegisterCache());
        registerLoader.release();
        return result;
    }

    public TestsCache getTestsCache() {
        return testsCache;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream("test/ru/integrotech/su/resources/test.properties");
            props.load(is);
            is.close();
        } catch (Exception e) {
        e.printStackTrace();
    }
        return props;
    }

    private MockLoader() {
        this.props = new PropertyHolder(getProperties());
        this.testsCache = new TestsCache();
        try {
            this.testsCache.update(AIRLINES,             loadJson(props.getAirlinesApi()));
            this.testsCache.update(WORLD_REGIONS,        loadJson(props.getWorldRegionsApi()));
            this.testsCache.update(COUNTRIES,            loadJson(props.getCountriesApi()));
            this.testsCache.update(CITIES,               loadJson(props.getCitiesApi()));
            this.testsCache.update(AIRPORTS,             loadJson(props.getAirportApi()));
            this.testsCache.update(TARIFFS,              loadJson(props.getTariffApi()));
            this.testsCache.update(BONUS_ROUTES,         loadJson(props.getBonusRoutesApi()));
            this.testsCache.update(BONUSES,              loadJson(props.getBonusesApi()));
            this.testsCache.update(WRONG_ROUTES,         loadJson(props.getWrongRoutesApi()));
            this.testsCache.update(LOYALTY,              loadJson(props.getLoyaltyApi()));
            this.testsCache.update(FLIGHTS,              loadJson(props.getFlightsApi()));
            this.testsCache.update(SERVICE_CLASS_LIMITS, loadJson(props.getServiceClassLimitApi()));
            this.testsCache.update(CHARGE_RULES, 		 loadJson(props.getChargeRulesApi()));
            this.testsCache.update(TICKET_DESIGNATORS, 	 loadJson(props.getTicketDesignatorApi()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MockLoader(RegisterCache registerCache) {
        this.props = new PropertyHolder(getProperties());
        this.testsCache = new TestsCache(registerCache);
    }

    public JsonElement loadJson(String apiName) throws JsonIOException, JsonSyntaxException, IOException {
        String localPath = this.props.getRegistryServiceBase() + apiName;
        InputStream is = new FileInputStream(localPath);
        return this.parseJson(is);
    }

    public JsonElement loadJson(String pathToFolder, String fileName) throws JsonIOException, JsonSyntaxException, IOException {
        String localPath = pathToFolder + fileName;
        InputStream is = new FileInputStream(localPath);
        return this.parseJson(is);
    }

    private JsonElement parseJson(InputStream is) throws JsonIOException, JsonSyntaxException, IOException {
        JsonParser jp = new JsonParser();
        return jp.parse(new InputStreamReader(is, this.props.getEncoding()));
    }
}
