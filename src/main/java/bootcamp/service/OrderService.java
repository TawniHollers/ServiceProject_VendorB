/*package bootcamp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import bootcamp.model.inventory.InventoryItem;
import bootcamp.model.invoice.Invoice;
import bootcamp.model.order.Order;

@Component
public class OrderService {
	@Value("${supplier-a.url}")
	String supplier_a_url;

	@Value("${supplier-b.url}")
	String supplier_b_url;

	@Value("${supplier-c.url}")
	String supplier_c_url;

	List<InventoryItem> supplier_A;
	List<InventoryItem> supplier_B;
	List<InventoryItem> supplier_C;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
    PaymentService paymentService;

	@Autowired
    InventoryService inventoryService;


	public void createOrderList(List<InventoryItem> lowInventoryList) {
		//check suppliers for lowest price
		//choose my supplier, and send them order
		makeApiCalls();
		List<Order> orderList = new ArrayList<>();
		int orderAmount;
		for(InventoryItem i : lowInventoryList){
		    if(i.getnumber_available() == 0){
		        orderAmount = 10;
            }
            else
                orderAmount = i.getnumber_available();
			Order order = new Order(i.getId(), orderAmount);
			orderList.add(order);
		}
		makeOrder(orderList);
	}

	public void makeOrder(List<Order> orders){

		//tells us which supplier has it cheaper
		UriComponentsBuilder builder;
		for(Order order: orders){

			int choice_supplier = cheapestSupplier(order.getId());
			if(choice_supplier == -1){
				//nothing was found
			}
			else{
				Invoice invoice = new Invoice();
				String url;
				switch(choice_supplier){
					case 0:
                        builder = UriComponentsBuilder.fromUriString("http://" + supplier_a_url + "/order").port(8080);
						invoice = restTemplate.postForObject(builder.toUriString(),order, Invoice.class);
						break;
					case 1:
                        builder = UriComponentsBuilder.fromUriString("http://" + supplier_b_url + "/order").port(8080);
						invoice = restTemplate.postForObject(builder.toUriString(),order, Invoice.class);
						break;
					case 2:
                        builder = UriComponentsBuilder.fromUriString("http://" + supplier_c_url + "/order").port(8080);
						invoice = restTemplate.postForObject(builder.toUriString(),order, Invoice.class);
						break;
					default:
						break;
				}

				double paymentTotal = invoice.getProduct().getRetail_price().doubleValue() * invoice.getCount();

				if(paymentService.makePayment(choice_supplier, paymentTotal)){
				    //todo: succeeded payment and need to update inventory and reduce cash amount
                    //inventoryService.addToInventory(order.getId(), order.getQty(), invoice.getProduct().getRetail_price().doubleValue());
				}
			}
		}
	}

	public int cheapestSupplier(int productID){
		BigDecimal price_1 = BigDecimal.valueOf(0.0);
		BigDecimal price_2 = BigDecimal.valueOf(0.0);
		BigDecimal price_3 = BigDecimal.valueOf(0.0);

		for(InventoryItem item: supplier_A) {
			if (item.getId() == productID) {
				price_1 = item.getRetail_price();
			}
		}
		for(InventoryItem item: supplier_B) {
			if (item.getId() == productID) {
				price_2 = item.getRetail_price();
			}
		}
		for(InventoryItem item: supplier_C) {
			if (item.getId() == productID) {
				price_3 = item.getRetail_price();
			}
		}

		BigDecimal[] prices = {price_1,price_2,price_3};
		BigDecimal minValue = BigDecimal.valueOf(0);
		boolean found = false;
		int lowestPriceIndex = -1;
		for (BigDecimal b: prices){
			if(b != BigDecimal.valueOf(0)) {
				minValue = b;
				found = true;
                break;
			}
		}
		if(found) {
			for (int i = 0; i < 3; i++) {
				if (prices[i] != BigDecimal.valueOf(0)) {
					if (prices[i].compareTo(minValue) <= 0) {
                        minValue = prices[i];
                        lowestPriceIndex = i;
                    }
				}
			}
		}
		return lowestPriceIndex;
	}

	public void makeApiCalls() {
		retrieveInventory(0);
		retrieveInventory(1);
		retrieveInventory(2);
	}

	@Async
	public void retrieveInventory(int i){
		UriComponentsBuilder builder;
		ResponseEntity<InventoryItem[]> responseEntity;
		switch(i){
			case 0:
				builder = UriComponentsBuilder.fromUriString("http://" + supplier_a_url + "/inventory/A").port(8080);
                responseEntity = restTemplate.getForEntity(builder.toUriString(), InventoryItem[].class);
                System.out.println(responseEntity.toString());
                supplier_A = Arrays.asList(responseEntity.getBody()); // restTemplate.getForObject(builder.toUriString(),List.class );
				break;
			case 1:
				builder = UriComponentsBuilder.fromUriString("http://" + supplier_b_url + "/inventory/B").port(8080);
                responseEntity = restTemplate.getForEntity(builder.toUriString(), InventoryItem[].class);
                System.out.println(responseEntity.toString());
                supplier_B = Arrays.asList(responseEntity.getBody());  // restTemplate.getForObject(builder.toUriString(),List.class );
				break;
			case 2:
				builder = UriComponentsBuilder.fromUriString("http://" + supplier_c_url + "/inventory/C").port(8080);
                responseEntity = restTemplate.getForEntity(builder.toUriString(), InventoryItem[].class);
                System.out.println(responseEntity.toString());
				supplier_C = Arrays.asList(responseEntity.getBody());
				break;
				default:
					break;
		}
	}
}
*/