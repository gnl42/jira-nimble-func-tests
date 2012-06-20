package it;

import com.atlassian.jira.nimblefunctests.annotation.JiraBuildNumberDependent;
import com.atlassian.jira.nimblefunctests.annotation.LongCondition;
import com.atlassian.jira.nimblefunctests.framework.NimbleFuncTestCase;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This tests checks if @JiraBuildNumberDependent works as it was intended.
 *
 * @since v0.1
 */
public class JiraBuildNumberDependentTest extends NimbleFuncTestCase {

	public static final int BN_JIRA_4_3 = 600;
	public static final int BN_JIRA_4_4 = 640;
	public static final int BN_JIRA_5 = 700;

	private static boolean wasShouldRunAlwaysExecuted;

	@BeforeClass
	public static void beforeClass() {
		wasShouldRunAlwaysExecuted = false;
	}

	@JiraBuildNumberDependent(0)
	@Test
	public void testThatShouldRunAlways() throws Exception {
		wasShouldRunAlwaysExecuted = true;
	}

	@AfterClass
	public static void afterClass() {
		if (!wasShouldRunAlwaysExecuted) {
			Assert.fail("testThatShouldRunAlways wasn't executed!");
		}
	}

	@JiraBuildNumberDependent(Long.MAX_VALUE)
	@Test
	public void testThatShouldNeverRun() throws Exception {
		Assert.fail("This test should be ignored!");
	}

	@JiraBuildNumberDependent(BN_JIRA_5)
	@Test
	public void testOnlyForJira50OrNewerName() throws Exception {
		if (administration.getBuildNumber() < BN_JIRA_5) {
			Assert.fail("Build number is less than expected!");
		}
	}

	@JiraBuildNumberDependent(BN_JIRA_4_3)
	@Test
	public void testOnlyForJira43OrNewerName() throws Exception {
		if (administration.getBuildNumber() < BN_JIRA_4_3) {
			Assert.fail("Build number is less than expected!");
		}
	}

	@JiraBuildNumberDependent(value = BN_JIRA_4_4, condition = LongCondition.LESS_OR_EQUAL)
	@Test
	public void testOnlyForJira44OrOlderName() throws Exception {
		if (administration.getBuildNumber() > BN_JIRA_4_4) {
			Assert.fail("Build number is greather than expected!");
		}
	}
}
