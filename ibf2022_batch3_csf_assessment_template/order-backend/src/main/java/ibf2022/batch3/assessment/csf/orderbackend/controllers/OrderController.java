package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping()
public class OrderController {

	@Autowired
    private OrdersRepository ordersRepository;

	@Autowired
	private OrderingService orderSvc;

	// TODO: Task 3 - POST /api/order
	@PostMapping(path = {"/api/order"})
	@ResponseBody
	public ResponseEntity<String> postOrder(@RequestBody PizzaOrder pizzaOrder){
		try{
			// SimpleDateFormat formatter =  new SimpleDateFormat("DD/MM/YYYY");
			// Date ordDate = formatter.parse(pizzaOrder.getDate());
			PizzaOrder order = this.orderSvc.placeOrder(pizzaOrder);
			JsonObject payload = Json.createObjectBuilder()
				.add("orderId", order.getOrderId())
				.add("date", order.getDate().getTime())
                .add("name", order.getName())
                .add("email", order.getEmail())
                .add("total", order.getTotal())
				.build();
			return ResponseEntity.status(HttpStatus.OK).body(payload.toString());
		// } catch (ParseException ex) {
        //     JsonObject error = Json.createObjectBuilder()
        //             .add("error", "Invalid date format")
        //             .build();
		// 	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
		} catch(Exception ex) {
			JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.toString());
		}
	}

	// TODO: Task 6 - GET /api/orders/<email>
	@GetMapping(path = {"/api/orders/{email}"})
	@ResponseBody
	public ResponseEntity<String> getOrders(@PathVariable String email){
		try {
			List<PizzaOrder> payload = this.orderSvc.getPendingOrdersByEmail(email);
			return ResponseEntity.status(HttpStatus.OK).body(payload.toString());

		} catch (Exception ex) {
			JsonObject error = Json.createObjectBuilder().add("error", ex.getMessage()).build();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
		}
	}

	// TODO: Task 7 - DELETE /api/order/<orderId>
	@DeleteMapping("/api/orders/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        try {
            // Check if the order exists
            if (ordersRepository.existsById(orderId)) {
                // Delete the order
                ordersRepository.deleteById(orderId);
                return ResponseEntity.ok().build();
            } else {
                // Order not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle any exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
