package befaster.solutions.HLO;

import static org.hamcrest.Matchers.*;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class HelloSolutionTest {

	
	@Test
	public void testname() {
		MatcherAssert.assertThat(new HelloSolution().hello("friendName"), is(equalTo("Hello, friendName!")));
	}
}
