package befaster.solutions.CHK;

import java.util.HashMap;

public class DiscountOffer implements Offer {
	String sku;
	int quantity;
	int price;

	public DiscountOffer(String sku, int quantity, int price) {
		this.sku = sku;
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
