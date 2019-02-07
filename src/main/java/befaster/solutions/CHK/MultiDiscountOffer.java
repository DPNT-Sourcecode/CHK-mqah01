package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Set;

public class MultiDiscountOffer implements Offer {

	Set<String> skus;
	int quantity;
	int price;

	public MultiDiscountOffer(Set<String> skus, int quantity, int price) {
		this.skus = skus;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public Basket discount(Basket basket) {
		Long quantity = basket.quantities.getOrDefault(sku, 0L);
		if (quantity == 0L) {
			return basket;
		}
		int actual = quantity.intValue() / this.quantity;
		HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
		newContents.put(sku, quantity % this.quantity);
		return new Basket(newContents, basket.total + (actual * price));
	}
}

