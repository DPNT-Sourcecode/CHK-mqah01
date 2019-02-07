package befaster.solutions.CHK;

import java.util.HashMap;


public final class DiscountOffer implements Offer {
	private int quantity;
	private int price;
	private String sku;

	public DiscountOffer(String sku, int quantity, int price) {
		this.sku = sku;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public Basket discount(Basket basket) {
		Long quantity = basket.quantities.get(sku);
		if (quantity == null) {
			return basket;
		}
		int actual = quantity.intValue() / this.quantity;
		HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
		newContents.put(sku, quantity % this.quantity);
		return new Basket(newContents, basket.total + (actual * price));
	}
}

