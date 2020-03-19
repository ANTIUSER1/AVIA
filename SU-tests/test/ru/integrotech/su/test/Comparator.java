package ru.integrotech.su.test;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 *  Class for comparing collections
 *  May be used in all tests
 */
public class Comparator {

    public static Comparator of(Class type) {
        Comparator result =  new Comparator();
        result.typeOfTestingClass = type;
        return result;
    }

    private Class<?> typeOfTestingClass;

    private Comparator() {
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
