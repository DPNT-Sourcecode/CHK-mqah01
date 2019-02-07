package befaster.solutions.CHK;

import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class CheckoutSolutionTest {

	CheckoutSolution solution = new CheckoutSolution();
	
	@Test
	public void quantities() {
		
		Map<String, Long> quantities = solution.quantities("A B C D A A A");
		
		
		MatcherAssert.assertThat(quantities, allOf(
				aMapWithSize(4),
				hasEntry("A", 4L),
				hasEntry("B", 1L),
				hasEntry("C", 1L),
				hasEntry("D", 1L)
		));
	}
}

