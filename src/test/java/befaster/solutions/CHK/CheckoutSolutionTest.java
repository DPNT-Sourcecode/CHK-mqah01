package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
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
//		HashMap<String, Integer> stockPrice = new HashMap<String, Integer>();
//		stockPrice.put("A", 50);
//		stockPrice.put("B", 30);
//		stockPrice.put("C", 20);
//		stockPrice.put("D", 15);
//		
//		HashMap<String, Offer> offers = new HashMap<String, Offer>();
//		offers.put("A", Offer.create(stockPrice, "A", 3, 130));
//		offers.put("B", Offer.create(stockPrice, "B", 2, 45));
//		solution = new CheckoutSolution(stockPrice, offers);
		solution = new CheckoutSolution();
	}
	
	
	
	@Test
	public void quantities() {
		
		Map<String, Long> quantities = solution.quantities("ABCDAAA");

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
	public void checkoutOfNullSkusIsInvalid() {
		
		assertThat(solution.checkout(null), is(-1));
	}
	
	
	@Test
	public void checkoutOfEmptyStringIsValid() {
		
		assertThat(solution.checkout(""), is(0));
	}
	
	
	@Test
	public void totalPrice() {
		assertThat(solution.checkout("ABC"), is(100));
	}
	
	
	@Test
	public void totalPriceDuplicateSkus() {
		assertThat(solution.checkout("ADABC"), is(165));
	}
	

	@Test
	public void totalPriceIncludingOneOffer() {
		assertThat(solution.checkout("AAABC"), is(200 - 20));
	}
	

	@Test
	public void totalPriceIncludingOneOfferAppliedTwice() {
		assertThat(solution.checkout("AAAAAAA"), is(350 - 40));
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


	@Test
	public void whenCheckoutQuantityIsMoreThanDoubleTheOfferQuantityThenDiscountIsDoubled() {
		Offer offer = Offer.create(Collections.singletonMap("A", 50), "A", 3, 130);
		
		assertThat(offer.discount(8), is(20 * 2));
	}
	
	
	@Test
	public void checks() throws Exception {
		assertThat(solution.checkout(""), is(-1));
		assertThat(solution.checkout("A"), is(50));
		assertThat(solution.checkout("B"), is(30));
		assertThat(solution.checkout("C"), is(20));
		assertThat(solution.checkout("D"), is(15));
		assertThat(solution.checkout("a"), is(-1));
		assertThat(solution.checkout("-"), is(-1));
		assertThat(solution.checkout("ABCa"), is(-1));
		assertThat(solution.checkout("AxA"), is(-1));
		assertThat(solution.checkout("ABCD"), is(115));
		assertThat(solution.checkout("A"), is(50));
		assertThat(solution.checkout("AA"), is(100));
		assertThat(solution.checkout("AAA"), is(130));
		assertThat(solution.checkout("AAAA"), is(180));
		assertThat(solution.checkout("AAAAA"), is(230));
		assertThat(solution.checkout("AAAAAA"), is(260));
		assertThat(solution.checkout("B"), is(30));
		assertThat(solution.checkout("BB"), is(45));
		assertThat(solution.checkout("BBB"), is(75));
		assertThat(solution.checkout("BBBB"), is(90));
		assertThat(solution.checkout("ABCDABCD"), is(215));
		assertThat(solution.checkout("BABDDCAC"), is(215));
		assertThat(solution.checkout("AAABB"), is(175));
		assertThat(solution.checkout("ABCDCBAABCABBAAA"), is(505));		
	}
}

