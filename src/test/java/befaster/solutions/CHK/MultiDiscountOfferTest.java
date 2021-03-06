package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MultiDiscountOfferTest {


	@Test
	public void noDiscountAppliedWhenNoProductsInOffer() {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Y", "Z"), 3, 10);
		
		Basket basket = new Basket(Collections.singletonMap("A", 20L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(0));
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("A", 20L)
		));
	}


	@Test
	public void noDiscountAppliedWhenDiscountThresholdNotMet() {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Y", "Z"), 3, 10);
		
		Basket basket = new Basket(Collections.singletonMap("X", 2L), 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(0));
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("X", 2L)
		));
	}


	@Test
	public void discountAppliedAcrossSingleProductInPriceOrder() {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Z", "Y"), 3, 10);
		
		Map<String, Long> map = new HashMap<>();
		map.put("X", 3L);
		map.put("Y", 1L);
		map.put("Z", 1L);
		
		Basket basket = new Basket(map, 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(10));
		assertThat(discounted.quantities, allOf(
				hasEntry("Y", 1L),
				hasEntry("Z", 1L),
				is(aMapWithSize(2))
		));
	}


	@Test
	public void discountAppliedAcrossMultipleProductsInPriceOrder() {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Z", "Y"), 3, 10);
		
		Map<String, Long> map = new HashMap<>();
		map.put("X", 2L);
		map.put("Y", 1L);
		map.put("Z", 1L);
		
		Basket basket = new Basket(map, 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(10));
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("Y", 1L)
		));
	}


	@Test
	public void discountsAppliedInPriceOrder() {
		MultiDiscountOffer offer = new MultiDiscountOffer(Arrays.asList("X", "Z", "Y"), 3, 10);
		
		Map<String, Long> map = new HashMap<>();
		map.put("X", 5L);
		map.put("Y", 2L);
		map.put("Z", 1L);
		
		Basket basket = new Basket(map, 0);
		Basket discounted = offer.discount(basket);
		
		assertThat(discounted.total, is(20));
		assertThat(discounted.quantities, allOf(
				is(aMapWithSize(1)),
				hasEntry("Y", 2L)
		));
	}
}
