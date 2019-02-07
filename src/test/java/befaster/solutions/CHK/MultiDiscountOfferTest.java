package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class MultiDiscountOfferTest {

	
	@Test
	public void testName() throws Exception {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Y", "Z"), 3, 10);
		
		Basket basket = new Basket(Collections.singletonMap("X", 2L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(0));
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("X", 2L)
		));
	}
}

