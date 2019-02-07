package befaster.solutions.CHK;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	
	
	private static final Map<String, Integer> STOCK_PRICE = new HashMap<String, Integer>();
	static {
		STOCK_PRICE.put("A", 50);
		STOCK_PRICE.put("B", 30);
		STOCK_PRICE.put("C", 20);
		STOCK_PRICE.put("D", 15);
	}
	
	
    public Integer checkout(String skus) {
    	
    	Map<String, Long> quantities = quantities(skus);
    	
    	return 0;
    }

	Map<String, Long> quantities(String skus) {
		return (skus == null) ? emptyMap() : 
			Arrays.stream(skus.split("[\\s,]+"))
				.collect(groupingBy(identity(), counting()));
	}
}



