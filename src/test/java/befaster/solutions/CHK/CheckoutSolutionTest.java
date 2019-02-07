package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class CheckoutSolutionTest {

	CheckoutSolution solution = new CheckoutSolution();
	
	@Test
	public void quantities() {
		
		Map<String, Long> quantities = solution.quantities("A B C D A A A");

		assertThat(quantities, allOf(
				aMapWithSize(4),
				hasEntry("A", 4L),
				hasEntry("B", 1L),
				hasEntry("C", 1L),
				hasEntry("D", 1L)
		));
	}
	
	
	@Test
	public void invalidSkus() {
		
		assertThat(solution.areValidSkus(Collections.singleton("Z")), is(false));
	}
	
	
	@Test
	public void validSkus() {
		Set<String> validSkus = new HashSet<String>();
		validSkus.add("A");
		validSkus.add("B");
		assertThat(solution.areValidSkus(validSkus), is(true));
	}
	
	
	@Test
	public void totalPrice() {
		assertThat(solution.checkout("A B C"), is(100));
	}
	
	
	@Test
	public void totalPriceDuplicateSkus() {
		assertThat(solution.checkout("A A A B C"), is(200));
	}
}

