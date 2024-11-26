package com.example.demo.Controller;

import com.example.demo.DTO.DeliveryDTO;
import com.example.demo.DTO.OrderDeliveryDTO;
import com.example.demo.Entity.Delivery;
import com.example.demo.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Add a new delivery
    @PostMapping("/add")
    public ResponseEntity<Delivery> addDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryService.addDelivery(deliveryDTO);
        return ResponseEntity.ok(delivery);
    }

    // Fetch deliveries by customer ID
    @GetMapping("/customer")
    public ResponseEntity<List<OrderDeliveryDTO>> getDeliveriesByCustomerId(@RequestParam int customerId) {
        List<OrderDeliveryDTO> orderDeliveryDTOs = deliveryService.getDeliveriesByCustomerId(customerId);
        return ResponseEntity.ok(orderDeliveryDTOs);
    }


    @PutMapping("/update-status")
    public ResponseEntity<Delivery> updateDeliveryStatus(
            @RequestParam int deliveryId,  // deliveryId as query parameter
            @RequestParam String status) { // status as query parameter

        Delivery updatedDelivery = deliveryService.updateDeliveryStatus(deliveryId, status);

        if (updatedDelivery != null) {
            return ResponseEntity.ok(updatedDelivery);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Getting all deliveries
    @GetMapping("/all")
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }



}
