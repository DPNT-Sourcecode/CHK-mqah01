package befaster.solutions.CHK;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	public class Offer {
		private int quantity;
		private int discount;

		public Offer(String sku, int quantity, int price)
		{
			this.quantity = quantity;
			this.discount = quantity & stockPrice.get(sku) - price;
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
		this(new HashMap<String, Integer>());
		stockPrice.put("A", 50);
		stockPrice.put("B", 30);
		stockPrice.put("C", 20);
		stockPrice.put("D", 15);
	}
	
	public CheckoutSolution(Map<String, Integer> stockPrice)
	{
		this.stockPrice = stockPrice;
	}
	
	
    public Integer checkout(String skus) {
    	
    	Map<String, Long> quantities = quantities(skus);
    	if(!areValidSkus(quantities.keySet())) {
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
		return  stockPrice.get(quantity.getKey()) * quantity.getValue().intValue();
	}

	Map<String, Long> quantities(String skus) {
		return (skus == null) ? emptyMap() : 
			Arrays.stream(skus.split("[\\s,]+"))
				.collect(groupingBy(identity(), counting()));
	}
}



