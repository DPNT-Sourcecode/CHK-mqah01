package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import befaster.solutions.CHK.CheckoutSolution.Offer;

public class CheckoutSolutionTest {

	CheckoutSolution solution;
	
	@Before
	public void beforeEach()
	{
		HashMap<String, Integer> stockPrice = new HashMap<String, Integer>();
		stockPrice.put("A", 50);
		stockPrice.put("B", 30);
		stockPrice.put("C", 20);
		stockPrice.put("D", 15);
		
		HashMap<String, Offer> offers = new HashMap<String, Offer>();
		solution = new CheckoutSolution(stockPrice, offers);
	}
	
	
	
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
	public void checkoutOfInvalidSkus() {
		
		assertThat(solution.checkout("Z"), is(-1));
	}
	
	
	@Test
	public void checkoutOfNullSkus() {
		
		assertThat(solution.checkout(null), is(0));
	}
	
	
	@Test
	public void totalPrice() {
		assertThat(solution.checkout("A B C"), is(100));
	}
	
	
	@Test
	public void totalPriceDuplicateSkus() {
		assertThat(solution.checkout("A A A B C"), is(200));
	}
	
	
	@Test
	public void whenCheckoutQuantityIsBelowOfferQuantityThenDiscountIsZero() {
		Offer offer = Offer.create(Collections.singletonMap("A", 50), "A", 3, 130);
		
		assertThat(offer.discount(2), is(0));
	}
	
	
	@Test
	public void whenCheckoutQuantityIsExactlyDoubleTheOfferQuantityThenDiscountIsDoubled() {
		Offer offer = Offer.create(Collections.singletonMap("A", 50), "A", 3, 130);
		
		assertThat(offer.discount(6), is(20 * 2));
	}
}

