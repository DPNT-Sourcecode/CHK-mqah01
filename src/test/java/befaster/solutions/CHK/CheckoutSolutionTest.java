package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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


	@Test
	public void multiDiscountOffer_fail142() {
		assertThat(solution.checkout("SSSZ"), is(65));
	}


	@Test
	public void multiDiscountOffer_fail144() {
		assertThat(solution.checkout("ZZZS"), is(65));
	}


	@Test
	public void multiDiscountOffer_fail146() {
		assertThat(solution.checkout("STXZ"), is(62));
	}
	
	
	@Test
	public void sortsPriceDescending() {
		List<String> list = Arrays.asList("S", "T", "X", "Y", "Z");
		solution.sortPriceDescending(list);
		assertThat(list.get(0), is(equalTo("Z")));
		assertThat(list.get(4), is(equalTo("X")));
	}


	/**
	 * This revealed a gap in testing: there were no unit tests to cover a MultiDiscountOffer
	 * where the Basket already had a non-zero total.
	 * Along with refactoring tests to cases, this case would be included
	 */
	@Test
	public void multiDiscountOffer_fail147() {
		assertThat(solution.checkout(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ"), is(1602));
	}


	@Test
	public void multiDiscountOffer_fail147b() {
		assertThat(solution.checkout(
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ"), is(
						
				50 * 2 + // A
				30 +     // B
				20 * 2 + // C
				15  * 2 + // D
				40  * 2 + // E
				10  * 2 + // F
				20  * 2 + // G
				10  * 2 + // H
				35  * 2 + // I
				60  * 2 + // J
				120 +    // K
				90  * 2 + // L
				15  * 2 + // M
				40  * 2 + // N
				10  * 2 + // O
				50  * 2 + // P
				30  * 2 + // Q
				50  * 2 + // R
				// S
				// T
				40  * 2 + // U
				90 +     // V
				20  * 2 + // W
				// X
				// Y
				// Z
				3 * 45 + 17
		));
	}


	@Test
	public void multiDiscountOffer_fail147c() {
		assertThat(solution.checkout(
				"ABCDEFGHIJKLM" +
				"ABCDEFGHIJKLM"), is(
						
				50 * 2 + // A
				30 +     // B
				20 * 2 + // C
				15  * 2 + // D
				40  * 2 + // E
				10  * 2 + // F
				20  * 2 + // G
				10  * 2 + // H
				35  * 2 + // I
				60  * 2 + // J
				120 +    // K
				90  * 2 + // L
				15  * 2   // M
		));
	}


	@Test
	public void multiDiscountOffer_fail147d() {
		assertThat(solution.checkout(
				"NOPQRSTUVWXYZ" +
				"NOPQRSTUVWXYZ"), is(
				40  * 2 + // N
				10  * 2 + // O
				50  * 2 + // P
				30  * 2 + // Q
				50  * 2 + // R
				// S
				// T
				40  * 2 + // U
				90 +     // V
				20  * 2 + // W
				// X
				// Y
				// Z
				3 * 45 + 17
		));
	}


	@Test
	public void multiDiscountOffer_fail147e() {
		assertThat(solution.checkout(
				"STUVWXYZ" +
				"STUVWXYZ"), is(
				// S
				// T
				40  * 2 + // U
				90 +     // V
				20  * 2 + // W
				// X
				// Y
				// Z
				3 * 45 + 17
		));
	}


	@Test
	public void multiDiscountOffer_fail147f() {
		assertThat(solution.checkout(
				"UVW" +
				"UVW"), is(
				40  * 2 + // U
				90 +     // V
				20  * 2  // W
		));
	}


	@Test
	public void multiDiscountOffer_fail147g() {
		assertThat(solution.checkout(
				"STXYZ" +
				"STXYZ"), is(
				// S
				// T
				// X
				// Y
				// Z
				3 * 45 + 17
		));
	}
}



