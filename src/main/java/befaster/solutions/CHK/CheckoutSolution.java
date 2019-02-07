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
| F    | 10    | 2F get one F free      |
| G    | 20    |                        |
| H    | 10    | 5H for 45, 10H for 80  |
| I    | 35    |                        |
| J    | 60    |                        |
| K    | 80    | 2K for 150             |
| L    | 90    |                        |
| M    | 15    |                        |
| N    | 40    | 3N get one M free      |
| O    | 10    |                        |
| P    | 50    | 5P for 200             |
| Q    | 30    | 3Q for 80              |
| R    | 50    | 3R get one Q free      |
| S    | 30    |                        |
| T    | 20    |                        |
| U    | 40    | 3U get one U free      |
| V    | 50    | 2V for 90, 3V for 130  |
| W    | 20    |                        |
| X    | 90    |                        |
| Y    | 10    |                        |
| Z    | 50    |                        |
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
		stockPrice.put("F", 10);
		stockPrice.put("G", 20);
		stockPrice.put("H", 10);
		stockPrice.put("I", 35);
		stockPrice.put("J", 60);
		stockPrice.put("K", 70);
		stockPrice.put("L", 90);
		stockPrice.put("M", 15);
		stockPrice.put("N", 40);
		stockPrice.put("O", 10);
		stockPrice.put("P", 50);
		stockPrice.put("Q", 30);
		stockPrice.put("R", 50);
		stockPrice.put("S", 20);
		stockPrice.put("T", 20);
		stockPrice.put("U", 40);
		stockPrice.put("V", 50);
		stockPrice.put("W", 20);
		stockPrice.put("X", 17);
		stockPrice.put("Y", 20);
		stockPrice.put("Z", 21);

		// Order of offers is important, better value (for customer) offers must come
		// first - as remaining offers see a mutated (reduced) view of the original basket
		// could be sorted multi-offers first (sub-sorted by target quantity)
		// and the discount offers descending quantity
		
		offers.add(new DiscountOffer("A", 5, 200));
		offers.add(new DiscountOffer("A", 3, 130));
		
		offers.add(new MultiOffer("E", 2, "B", 1));
		offers.add(new MultiOffer("F", 2, "F", 1));
		
		offers.add(new DiscountOffer("B", 2, 45));

		offers.add(new DiscountOffer("H", 10, 80));
		offers.add(new DiscountOffer("H", 5, 45));

		offers.add(new DiscountOffer("K", 2, 150));
		
		offers.add(new MultiOffer("N", 3, "M", 1));

		offers.add(new DiscountOffer("P", 5, 200));
		
		offers.add(new MultiOffer("R", 3, "Q", 1));

		offers.add(new DiscountOffer("Q", 3, 80));

		offers.add(new MultiOffer("U", 3, "U", 1));

		offers.add(new DiscountOffer("V", 3, 130));
		offers.add(new DiscountOffer("V", 2, 90));
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

