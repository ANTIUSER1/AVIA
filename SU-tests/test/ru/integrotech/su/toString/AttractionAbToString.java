package ru.integrotech.su.toString;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.integrotech.airline.core.bonus.MilesRule;
import ru.integrotech.su.mock.MockLoader;
import ru.integrotech.su.outputparams.attractionAB.AttractionAbBuilder;

public class AttractionAbToString {

    @BeforeClass
    public static void updateRegisters() {
        MockLoader.getInstance().updateRegisters(
                MockLoader.REGISTERS_TYPE.REAL,
                AttractionAbBuilder.getRegisterNames());
    }

    @Test
    public void printMilesRules() {
        for (MilesRule milesRule : MockLoader.getInstance().getRegisterCache().getMilesRules()) {
            System.out.println(milesRule);
        }
    }
}
