package winepairer.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class WinePairerTest{

    @Test
    public void emptyMealInputGivesNoPairings() {
        WinePairer wpTest = new WinePairer("");
        assertTrue(wpTest.getFoods().isEmpty());
    }

    @Test
    public void mealInputZalmGivesPairings() {
        WinePairer wpTest = new WinePairer("zalm");
        assertFalse(wpTest.getFoods().isEmpty());
    }

    @Test
    public void upperCaseAndLowerCaseGiveSameAmountOfResults() {
        WinePairer wpTest1 = new WinePairer("zalm");
        WinePairer wpTest2 = new WinePairer("ZaLm");
        assertEquals(wpTest1.getFoods().size(), wpTest2.getFoods().size());
    }
}