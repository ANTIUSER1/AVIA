package ru.integrotech.su.toString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.su.inputparams.grouping.InputParamsGrouping;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.grouping.GroupingBuilder;
import ru.integrotech.su.outputparams.grouping.GroupingTable;

public class GroupingToString {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                MockLoader.REGISTERS_TYPE.REAL,
                GroupingBuilder.getRegisterNames());
    }

    private GroupingBuilder groupingBuilder;

    @Before
    public void init() {
        this.groupingBuilder = GroupingBuilder.of(MockLoader.getInstance()
                .getRegisterCache());
    }

    @Test
    public void printAll() {
        InputParamsGrouping input = new InputParamsGrouping();

        GroupingTable result = this.groupingBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printMapEngine() {
        InputParamsGrouping input = new InputParamsGrouping();
        input.setMapEngine("google");

        GroupingTable result = this.groupingBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }

    @Test
    public void printApp() {
        InputParamsGrouping input = new InputParamsGrouping();
        input.setMapEngine("google");
        input.setApp("desktop");

        GroupingTable result = this.groupingBuilder.buildResult(input);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResult = gson.toJson(result);
        System.out.println(jsonResult);
    }


}
