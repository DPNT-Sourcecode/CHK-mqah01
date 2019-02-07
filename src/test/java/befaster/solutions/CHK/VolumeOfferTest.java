package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Collections;

import org.junit.Test;

public class VolumeOfferTest {
	
	
	@Test
	public void whenCheckoutQuantityIsBelowOfferQuantityThenDiscountIsZero() {
		Offer offer = new DiscountOffer("A", 3, 130);
		Basket basket = new Basket(Collections.singletonMap("A", 2L), 0);
		
		assertThat(offer.discount(basket).total, is(0));
	}


	@Test
	public void whenCheckoutQuantityIsExactlyDoubleTheOfferQuantityThenDiscountIsDoubled() {
		Offer offer = new DiscountOffer("A", 3, 130);
		Basket basket = new Basket(Collections.singletonMap("A", 6L), 0);
		
		assertThat(offer.discount(basket).total, is(130 * 2));
	}


	@Test
	public void whenCheckoutQuantityIsMoreThanDoubleTheOfferQuantityThenDiscountIsDoubled() {
		Offer offer = new DiscountOffer("A", 3, 130);
		Basket basket = new Basket(Collections.singletonMap("A", 8L), 0);
		
		assertThat(offer.discount(basket).total, is(130 * 2));
	}

}
