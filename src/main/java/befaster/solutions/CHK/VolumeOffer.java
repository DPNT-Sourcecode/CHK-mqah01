package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;


public final class VolumeOffer implements Offer {
	private int quantity;
	private int price;
	private String sku;

	public VolumeOffer(String sku, int quantity, int price) {
		this.sku = sku;
		this.quantity = quantity;
		this.price = price;
	}

	public static VolumeOffer create(Map<String, Integer> stockPrice, String sku, int quantity, int price) {
		return new VolumeOffer(sku, quantity, price);
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


