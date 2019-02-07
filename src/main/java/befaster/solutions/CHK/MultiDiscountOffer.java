package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.List;

public class MultiDiscountOffer implements Offer {

	List<String> skus;
	int quantity;
	int price;

	/**
	 * @param skus a sorted list of SKUs (price descending order)
	 * @param quantity
	 * @param price
	 */
	public MultiDiscountOffer(List<String> skus, int quantity, int price) {
		this.skus = skus;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public Basket discount(Basket basket) {
		
		// best val for customer - sort submap by highest value items
		
		HashMap<String, Long> sub = new HashMap<>(basket.quantities);
		sub.keySet().retainAll(skus);
		if(sub.isEmpty()) {
			return basket;
		}

		int totalBasketQuantity = sub.values().stream().mapToInt(Long::intValue).sum();
		int subTotal = (totalBasketQuantity / quantity) * price;
		int rem = totalBasketQuantity % quantity;
		totalBasketQuantity -= rem;
		
		for(String sku : skus) {
			long basketQuantity = sub.getOrDefault(sku, 0L);
			long remove = Math.min(basketQuantity, totalBasketQuantity);
			basketQuantity -= remove;
			if(basketQuantity == 0L) {
				sub.remove(sku);
			} else {
				sub.put(sku, basketQuantity);
			}
			totalBasketQuantity -= remove;
		}
		HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
		newContents.putAll(sub);
		return new Basket(newContents, subTotal);
	}
}
