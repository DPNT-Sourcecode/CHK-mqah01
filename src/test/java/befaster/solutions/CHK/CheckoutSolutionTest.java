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
		
		assertThat(solution.areValidSkus(Collections.singleton("z")), is(false));
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
		
		assertThat(solution.checkout("z"), is(-1));
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
		assertThat(solution.checkout("BBBB"), is(45 * 2));
	}


	@Test
	public void totalPriceIncludingMultipleOffersOnSameSku() {
		assertThat(solution.checkout("AAAAA" + "AAA"), is(200 + 130));
	}


	@Test
	public void multiProductMultiOfferNoFreeProductInBasket() {
		assertThat(solution.checkout("EE"), is(80));
	}


	@Test
	public void multiProductMultiOfferSingle() {
		assertThat(solution.checkout("EEB"), is(80));
	}


	@Test
	public void multiProductMultiOfferSingleDiscountedProductRemaining() {
		assertThat(solution.checkout("EEBB"), is(110));
	}


	@Test
	public void multiProductMultiOfferSingleDiscountNoAdditionalFreeProduct() {
		assertThat(solution.checkout("EEEEB"), is(160));
	}


	@Test
	public void multiProductMultiOfferAppliedTwice() {
		assertThat(solution.checkout("EEBEEB"), is(160));
	}


	@Test
	public void singleProductMultiOfferAppliedTwice() {
		assertThat(solution.checkout("FFFFFF"), is(40));
	}
}


