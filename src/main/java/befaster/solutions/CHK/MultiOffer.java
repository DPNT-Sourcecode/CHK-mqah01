package befaster.solutions.CHK;
public final class MultiOffer implements Offer {
		@Override
		public Basket discount(Basket basket) {
			long quantity = basket.quantities.getOrDefault("E", 0L);
			if(quantity < 2L || !basket.quantities.containsKey("B")) {
				return basket;
			}
			int free = (int) (quantity / 2);
			HashMap<String, Long> newContents = new HashMap<>(basket.quantities);
			newContents.put("B", Math.max(newContents.get("B") - free, 0)); 
			return new Basket(newContents, basket.total);
		}
	}
