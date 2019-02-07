package befaster.solutions.CHK;

import java.util.HashMap;

public class AbstractDiscountOffer implements Offer {

	protected int quantity;
	protected int price;

	public AbstractDiscountOffer() {
		super();
	}

	@Override
	public Basket discount(Basket basket) {
		Long quantity = quantity(basket);
		if (quantity == 0L) {
			return basket;
		}
		int actual = quantity.intValue() / this.quantity;
		HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
		newContents.put(sku, quantity % this.quantity);
		return new Basket(newContents, basket.total + (actual * price));
	}

	protected Long quantity(Basket basket) {
		return basket.quantities.getOrDefault(sku, 0L);
	}

}
