package befaster.solutions.CHK;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class MultiOfferTest {

	MultiOffer offer = new MultiOffer("F", 2, "F", 1);
	
	@Test
	public void quantityBelowOfferThreshold() {
		
		Basket basket = new Basket(singletonMap("F", 1L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("F", 1L)
		));
	}


	@Test
	public void twoForOne() {
		
		Basket basket = new Basket(singletonMap("F", 2L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("F", 2L)
		));
	}


	@Test
	public void twoForOneWithResidualProduct() {
		
		Basket basket = new Basket(singletonMap("F", 3L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("F", 2L)
		));
	}
}
