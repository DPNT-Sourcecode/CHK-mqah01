package befaster.solutions.CHK;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
+------+-------+----------------+
| Item | Price | Special offers |
+------+-------+----------------+
| A    | 50    | 3A for 130     |
| B    | 30    | 2B for 45      |
| C    | 20    |                |
| D    | 15    |                |
+------+-------+----------------+

 *
 */
public class CheckoutSolution {
	
	
	public static class Basket {
		
		Map<String, Long> quantities;
		int total;

		public Basket(Map<String, Long> quantities, int total) {
			this.quantities = quantities;
			this.total = total;
		}
	}
	
	public static class VolumeOffer implements Offer {
		private int quantity;
		private int price;
		private String sku;

		public VolumeOffer(String sku, int quantity, int price)
		{
			this.sku = sku;
			this.quantity = quantity;
			this.price = price;
		}
		
		public static VolumeOffer create(Map<String, Integer> stockPrice, String sku, int quantity, int price)
		{
			return new VolumeOffer(sku, quantity, price);
		}
		
		@Override
		public Basket discount(Basket basket)
		{
			Long quantity = basket.quantities.get(sku);
			if(quantity == null) {
				return basket;
			}
			int actual = quantity.intValue() / this.quantity;
			
			HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
			newContents.put(sku, quantity % this.quantity);
			return new Basket(newContents, basket.total  + (actual * price));
		}
	}
	
	
	final Map<String, Integer> stockPrice;
	final List<VolumeOffer> offers;

	public CheckoutSolution()
	{
		this(new HashMap<String, Integer>(), new ArrayList<VolumeOffer>());
		stockPrice.put("A", 50);
		stockPrice.put("B", 30);
		stockPrice.put("C", 20);
		stockPrice.put("D", 15);
		
		offers.add(VolumeOffer.create(stockPrice, "A", 5, 200));
		offers.add(VolumeOffer.create(stockPrice, "A", 3, 130));
		offers.add(VolumeOffer.create(stockPrice, "B", 2, 45));
		
		
	}
	
	public CheckoutSolution(Map<String, Integer> stockPrice, List<VolumeOffer> offers)
	{
		this.stockPrice = stockPrice;
		this.offers = offers;
	}
	
	
    public Integer checkout(String skus) {
    	
    	Map<String, Long> quantities = quantities(skus);
    	if(quantities == null || !areValidSkus(quantities.keySet())) {
    		return -1;
    	}
    	Basket basket = new Basket(quantities, 0);
    	
    	return totalPrice(basket);
    }

	boolean areValidSkus(Set<String> skus) {
		for(String sku : skus) {
			if(!stockPrice.containsKey(sku)) {
				return false;
			}
		}
		return true;
	}

	private Integer totalPrice(Basket basket) {
		
		for(Offer offer : offers) {
			basket = offer.discount(basket);
		}
		
		int sumTotal = basket.quantities.entrySet().stream()
			.mapToInt(this::price)
			.sum();
		return sumTotal + basket.total;
	}

	private int price(Map.Entry<String, Long> quantity)
	{
		String sku = quantity.getKey();
		int checkoutQuantity = quantity.getValue().intValue();
		return  stockPrice.get(sku) * checkoutQuantity;
	}

	Map<String, Long> quantities(String skus) {
		Predicate<? super String> isEmpty = String::isEmpty;
		return (skus == null) ? null :
			Arrays.stream(skus.split(""))
				.filter(isEmpty.negate())
				.collect(groupingBy(identity(), counting()));
	}
}




