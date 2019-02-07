package befaster.solutions.CHK;

import java.util.Map;

public final class Basket {
	
	Map<String, Long> quantities;
	int total;

	public Basket(Map<String, Long> quantities, int total) {
		this.quantities = quantities;
		this.total = total;
	}
}
