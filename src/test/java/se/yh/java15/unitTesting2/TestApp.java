package se.yh.java15.unitTesting2;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

public class TestApp {

	@Test
	public void testApp() {
		AssertJUnit.assertTrue(true);
	}
	
	@Test
	public void testApp2() {
		String kalle = new String("Kalle");
		String ada = new String("Kalle");
		AssertJUnit.assertEquals("Kalle", "Kalle");
	}
	
	@Test
	public void makeSureKalleIsKalle_verifyStringsAreEqual() {
		String kalle = new String("Kalle");
		String ada = new String("Kalle");
		AssertJUnit.assertSame(kalle, ada);
	}
	
	@Test
	public void GetAMessageFromTheTest() {
		Assert.assertEquals(true, false, "Did not match, big wonder");
	}
	
}
