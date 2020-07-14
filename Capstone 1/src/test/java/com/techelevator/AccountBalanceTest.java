package com.techelevator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountBalanceTest {
	
private AccountBalance balance;

	
	@Before
	public void runBeforeEachTest() {
		balance = new AccountBalance();
	}
	
	@Test
	public void testBalanceInitialization() {
		Assert.assertNotNull(balance);
	}
	
	@Test
	public void testAddMoney() {
		Assert.assertEquals(1000.00, balance.addMoney(1000), 0);
		Assert.assertEquals(1000.00, balance.addMoney(5000.00), 0);
	}

	@Test
	public void testGetChange() {
		balance.addMoney(2000.00);
		Assert.assertEquals( 1000.00, balance.getChange(1000.00), 0);
		balance.addMoney(2000.00);
		Assert.assertEquals( 0, balance.getChange(2000.00), 0);
		balance.addMoney(1000.00);
		Assert.assertEquals(1000.00, balance.getChange(0), 0);
	}

	@Test
	public void testSplitToBills() {
		int[] bills = new int[] {1,1,1,1,1,1,1};
		balance.addMoney(36.41);
		balance.getChange(0);
		int[] newbills = balance.splitToBills();
		Assert.assertEquals( "20",bills[0] , newbills[0]);
		Assert.assertEquals( "10",bills[1] , newbills[1]);
		Assert.assertEquals( "5",bills[2] , newbills[2]);
		Assert.assertEquals( "1", bills[3] , newbills[3]);
		Assert.assertEquals( ".25",bills[4] , newbills[4]);
		Assert.assertEquals( ".10", bills[5] , newbills[5]);
		Assert.assertEquals( ".05", bills[6] , newbills[6]);
	}
}
