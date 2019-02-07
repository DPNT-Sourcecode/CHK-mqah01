package befaster.solutions.CHK;




public class DiscountOffer extends AbstractDiscountOffer {
	String sku;

	public DiscountOffer(String sku, int quantity, int price) {
		this.sku = sku;
		this.quantity = quantity;
		this.price = price;
	}
}


