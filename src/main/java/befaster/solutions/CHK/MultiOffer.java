package befaster.solutions.CHK;

import java.util.HashMap;

public final class MultiOffer implements Offer {
	
	
	private String givenSku;
	private int givenQuantity;
	private String applyToSku;
	private int applyToQuantity;


	public MultiOffer(String givenSku, int givenQuantity, String applyToSku, int applyToQuantity) {
		this.givenSku = givenSku;
		this.givenQuantity = givenQuantity;
		this.applyToSku = applyToSku;
		this.applyToQuantity = applyToQuantity;
	}
	
	
	@Override
	public Basket discount(Basket basket) {
		long quantity = basket.quantities.getOrDefault(givenSku, 0L);
		if (quantity < givenQuantity || !basket.quantities.containsKey(applyToSku)) {
			return basket;
		}
		int free = (int) (quantity / givenQuantity) * applyToQuantity;
		HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
		newContents.put(applyToSku, Math.max(newContents.get(applyToSku) - free, 0));
		return new Basket(newContents, basket.total);
	}
}
