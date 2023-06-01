package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import jakarta.json.JsonObject;

public class OrderingService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepo;
	
	// TODO: Task 5
	// WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
	public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {
		try {
			//Make HTTP request to pricing service
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("name", order.getName());
            requestBody.add("email", order.getEmail());
            requestBody.add("sauce", order.getSauce());
            requestBody.add("size", String.valueOf(order.getSize()));
            requestBody.add("thickCrust", String.valueOf(order.getThickCrust()));
            requestBody.add("toppings", String.join(",", order.getToppings()));
            requestBody.add("comments", order.getComments());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://pizza-pricing-production.up.railway.app",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
			
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				String response = responseEntity.getBody();
				String[] responseFields = response.split(",");

				if (responseFields.length == 3) {
					String orderId = responseFields[0];
					long orderDateMillis = Long.parseLong(responseFields[1]);
					float totalPrice = Float.parseFloat(responseFields[2]);

					order.setOrderId(orderId);
					order.setDate(new Date(orderDateMillis));
					order.setTotal(totalPrice);

					ordersRepo.add(order);
					pendingOrdersRepo.add(order);

					return order;
				}
			} throw new OrderException("Invalid response from pricing service");
		} catch (Exception ex) {
			throw new OrderException(ex.getMessage());
		}
	}

	// For Task 6
	// WARNING: Do not change the method's signature or its implemenation
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		return ordersRepo.getPendingOrdersByEmail(email);
	}

	// For Task 7
	// WARNING: Do not change the method's signature or its implemenation
	public boolean markOrderDelivered(String orderId) {
		return ordersRepo.markOrderDelivered(orderId) && pendingOrdersRepo.delete(orderId);
	}

	public JsonObject getOrdersByEmail(String email) {
		return null;
	}


}
