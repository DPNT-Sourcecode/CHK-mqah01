package befaster.solutions.CHK;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.HashMap;
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
	
	public static class Offer {
		
		public static final Offer NO_OFFER = new Offer(1, 0);
		
		private int quantity;
		private int discount;

		public Offer(int quantity, int discount)
		{
			this.quantity = quantity;
			this.discount = discount;
		}
		
		public static Offer create(Map<String, Integer> stockPrice, String sku, int quantity, int price)
		{
			return new Offer(quantity, quantity * stockPrice.get(sku) - price);
		}
		
		public int discount(int quantity)
		{
			int actual = quantity / this.quantity;
			return actual * discount;
		}
	}
	
	
	final Map<String, Integer> stockPrice;
	final Map<String, Offer> offers;

	public CheckoutSolution()
	{
		this(new HashMap<String, Integer>(), new HashMap<String, Offer>());
		stockPrice.put("A", 50);
		stockPrice.put("B", 30);
		stockPrice.put("C", 20);
		stockPrice.put("D", 15);
		
		offers.put("A", Offer.create(stockPrice, "A", 3, 130));
		offers.put("B", Offer.create(stockPrice, "B", 2, 45));
	}
	
	public CheckoutSolution(Map<String, Integer> stockPrice, Map<String, Offer> offers)
	{
		this.stockPrice = stockPrice;
		this.offers = offers;
	}
	
	
    public Integer checkout(String skus) {
    	
    	Map<String, Long> quantities = quantities(skus);
    	if(quantities == null || !areValidSkus(quantities.keySet())) {
    		return -1;
    	}
    	
    	
    	return totalPrice(quantities);
    }

	boolean areValidSkus(Set<String> skus) {
		for(String sku : skus) {
			if(!stockPrice.containsKey(sku)) {
				return false;
			}
		}
		return true;
	}

	private Integer totalPrice(Map<String, Long> quantities) {
		
		return quantities.entrySet().stream()
			.mapToInt(this::price)
			.sum();
	}
	
	private int price(Map.Entry<String, Long> quantity)
	{
		String sku = quantity.getKey();
		int checkoutQuantity = quantity.getValue().intValue();

		return  stockPrice.get(sku) * checkoutQuantity
				- offers.getOrDefault(sku, Offer.NO_OFFER).discount(checkoutQuantity);
	}

	Map<String, Long> quantities(String skus) {
		Predicate<? super String> isEmpty = String::isEmpty;
		return (skus == null) ? null :
			Arrays.stream(skus.split(""))
				.filter(isEmpty.negate())
				.collect(groupingBy(identity(), counting()));
	}
}


