package ru.integrotech.su.test;

import org.junit.Assert;

import ru.integrotech.airline.searcher.BonusSearcher;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.mock.TestsCache;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbBuilder;
import ru.integrotech.su.outputparams.charge.ChargeBuilder;
import ru.integrotech.su.outputparams.route.RoutesBuilder;
import ru.integrotech.su.outputparams.spend.SpendBuilder;
import ru.integrotech.su.outputparams.spend.SpendLkBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/* Base class for all tests. Contains all functionality to get actual data (load from json's
 * and expected data) , has possibility to load registers from real server-side registers or
 * from minimize mock registers inside resources/registers json's*/
public class CommonTest {

    public static CommonTest of(Class type, String... registerNames) {
        CommonTest result =  new CommonTest(registerNames);
        result.typeOfTestingClass = type;
        return result;
    }

    public static CommonTest of(MockLoader loader, Class type) {
        CommonTest result =  new CommonTest(loader);
        result.typeOfTestingClass = type;
        return result;
    }

    public static CommonTest of(MockLoader loader) {
        CommonTest result =  new CommonTest(loader);
        return result;
    }

    public static CommonTest of(MockLoader loader, String... registerNames) {
        CommonTest result =  new CommonTest(loader);
        return result;
    }

    private Class<?> typeOfTestingClass;
    private TestsCache testsCache;
    private BonusSearcher bonusSearcher;
    private MockLoader loader;
    private RoutesBuilder routesBuilder;
    private ChargeBuilder chargeBuilder;
    private SpendBuilder spendBuilder;
    private SpendLkBuilder spendLkBuilder;
    private AttractionAbBuilder attractionAbBuilder;

    private CommonTest(String... registerNames) {
        this.loader =  MockLoader.ofMockRegisters(registerNames);;
        this.testsCache = loader.getTestsCache();
        this.bonusSearcher = BonusSearcher.of(this.testsCache.getRegisters());
        this.routesBuilder = RoutesBuilder.of(this.testsCache.getRegisters());
        this.chargeBuilder = ChargeBuilder.of(this.testsCache.getRegisters());
        this.spendBuilder = SpendBuilder.of(this.testsCache.getRegisters());
        this.spendLkBuilder = SpendLkBuilder.of(this.spendBuilder);
        this.attractionAbBuilder = AttractionAbBuilder.of(this.testsCache.getRegisters());
    }

    private CommonTest(MockLoader loader) {
        this.loader =  loader;
        this.testsCache = loader.getTestsCache();
        this.bonusSearcher = BonusSearcher.of(this.testsCache.getRegisters());
        this.routesBuilder = RoutesBuilder.of(this.testsCache.getRegisters());
        this.chargeBuilder = ChargeBuilder.of(this.testsCache.getRegisters());
        this.spendBuilder = SpendBuilder.of(this.testsCache.getRegisters());
        this.spendLkBuilder = SpendLkBuilder.of(this.spendBuilder);
        this.attractionAbBuilder = AttractionAbBuilder.of(this.testsCache.getRegisters());
    }

    public Class getTypeOfTestingClass() {
        return typeOfTestingClass;
    }

    public void setTypeOfTestingClass(Class<?> typeOfTestingClass) {
        this.typeOfTestingClass = typeOfTestingClass;
    }

    public TestsCache getTestsCache() {
        return testsCache;
    }

    public BonusSearcher getBonusSearcher() {
        return bonusSearcher;
    }

    public MockLoader getLoader() {
        return loader;
    }

    public RoutesBuilder getRoutesBuilder() {
        return routesBuilder;
    }

    public ChargeBuilder getChargeBuilder() {
        return chargeBuilder;
    }

    public SpendBuilder getSpendBuilder() {
        return spendBuilder;
    }

    public SpendLkBuilder getSpendLkBuilder() {
        return spendLkBuilder;
    }
    
    public AttractionAbBuilder getAttractionAbBuilder() {
		return attractionAbBuilder;
	}

	public <T extends Comparable<T>> void testIsEquals(List<T> expected, List<T> actual) {
        Assert.assertEquals(actual.size(), expected.size());

        this.sort(actual);
        this.sort(expected);

        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i), expected.get(i));
        }
    }

    public <T extends Comparable<T>> boolean isEquals(List<T> expected, List<T> actual) {

        this.sort(actual);
        this.sort(expected);

        if (actual.size() != expected.size()) return false;

        for (int i = 0; i < actual.size(); i++) {
            if (!actual.get(i).equals(expected.get(i))) return false;
        }

        return true;
    }

    public <T extends Comparable<T>>  void testIsPresent(List<T> expected, List<T> actual) {
        Assert.assertEquals(expected.size(), countEqualities(expected, actual));
    }

    public <T>  void testIsPresent(List<T> actual, T expectedObj) {

        boolean expectedIsPresent = false;

        for (T actualObj : actual) {
            expectedIsPresent = expectedObj.equals(actualObj);
            if (expectedIsPresent)
                break;
        }

        Assert.assertTrue(expectedIsPresent);
    }

    public <T extends Comparable<T>>  void testIsNotPresent(List<T> expected, List<T>  actual) {
        Assert.assertEquals(0, countEqualities(expected, actual));
    }



    private <T extends Comparable<T>> int countEqualities(List<T> expected, List<T> actual) {

        this.sort(actual);
        this.sort(expected);

        int counter = 0;
        for (T actualObj : actual) {
            for (T expextedObj : expected) {
                if (actualObj.equals(expextedObj)) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    public  <T extends Comparable<T>> void sort(List<T> list) {
       Collections.sort(list);
       this.sortInnerElements(list);
    }

    private <T> void sortInnerElements(List<T> list) {
        Method sort = null;
        try {
            sort = this.typeOfTestingClass.getMethod("sort", (Class<?>[])null);
            if (sort != null) {
                for (T obj : list) {
                    sort.invoke(obj, (Object[]) null);
                }
            }
        } catch (NoSuchMethodException e) {
            //do nothing, exception is not critical
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
