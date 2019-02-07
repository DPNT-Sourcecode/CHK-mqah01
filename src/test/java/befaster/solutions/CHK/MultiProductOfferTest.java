package befaster.solutions.CHK;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class MultiProductOfferTest {

	
	@Test
	public void testName() {
		Offer offer = VolumeOffer.create(Collections.singletonMap("A", 50), "A", 3, 130);
		Basket basket = new Basket(Collections.singletonMap("A", 2L), 0);
		
		assertThat(offer.discount(basket).total, is(0));
	}
}
