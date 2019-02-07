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
+------+-------+------------------------+
| Item | Price | Special offers         |
+------+-------+------------------------+
| A    | 50    | 3A for 130, 5A for 200 |
| B    | 30    | 2B for 45              |
| C    | 20    |                        |
| D    | 15    |                        |
| E    | 40    | 2E get one B free      |
+------+-------+------------------------+
 */
public class CheckoutSolution {
	
	
	
	

	final Map<String, Integer> stockPrice;
	final List<Offer> offers;

	public CheckoutSolution()
	{
		this(new HashMap<String, Integer>(), new ArrayList<Offer>());
		stockPrice.put("A", 50);
		stockPrice.put("B", 30);
		stockPrice.put("C", 20);
		stockPrice.put("D", 15);
		stockPrice.put("E", 40);
		
		offers.add(DiscountOffer.create(stockPrice, "A", 5, 200));
		offers.add(DiscountOffer.create(stockPrice, "A", 3, 130));
		
		// Am not refactoring yet - as expect spec changes on subsequent rounds...
		offers.add(new MultiOffer());
		
		offers.add(DiscountOffer.create(stockPrice, "B", 2, 45));

	}
	
	public CheckoutSolution(Map<String, Integer> stockPrice, List<Offer> offers)
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




