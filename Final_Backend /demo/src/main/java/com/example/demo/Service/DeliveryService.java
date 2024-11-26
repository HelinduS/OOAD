package com.example.demo.Service;

import com.example.demo.DTO.DeliveryDTO;
import com.example.demo.DTO.OrderDeliveryDTO;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Delivery;
import com.example.demo.Entity.Order;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.DeliveryRepository;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private SMSservice smsService;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public DeliveryService(SMSservice smsService) {
        this.smsService = smsService;
    }


    public Delivery addDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();


        orderRepository.findById(deliveryDTO.getOrderId()).ifPresentOrElse(
                delivery::setOrder,
                () -> {
                    throw new RuntimeException("Order not found with ID: " + deliveryDTO.getOrderId());
                }
        );

        delivery.setDescription(deliveryDTO.getDescription());
        delivery.setStatus(deliveryDTO.getStatus());
        delivery.setDate(deliveryDTO.getDate());

        return deliveryRepository.save(delivery);
    }

    public List<OrderDeliveryDTO> getDeliveriesByCustomerId(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));


        List<Order> orders = orderRepository.findByCustomer(customer);


        List<OrderDeliveryDTO> orderDeliveryDTOs = orders.stream()
                .map(order -> {

                    Delivery delivery = order.getDelivery();

                    if (delivery != null) {
                        return new OrderDeliveryDTO(
                                order.getOrderId(),
                                order.getDescription(),
                                customer.getFirstName() + " " + customer.getLastName(),
                                delivery.getDeliveryId(),
                                delivery.getDescription(),
                                delivery.getStatus(),
                                delivery.getDate()
                        );
                    } else {

                        return new OrderDeliveryDTO(
                                order.getOrderId(),
                                order.getDescription(),
                                customer.getFirstName() + " " + customer.getLastName(),
                                -1,   // Indicate no delivery with -1 or any other logic
                                "No delivery",
                                "Pending",
                                "TBD"
                        );
                    }
                })
                .collect(Collectors.toList());

        return orderDeliveryDTOs;
    }


    public Delivery updateDeliveryStatus(int deliveryId, String status) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId).orElse(null);

        if (existingDelivery != null) {
            existingDelivery.setStatus(status);


            try {
                String response = smsService.sendSms("+94725263276", "Your delivery is  " + status);
                System.out.println("SMS Response: " + response);
            } catch (Exception e) {
                System.err.println("Failed to send SMS: " + e.getMessage());
            }


            return deliveryRepository.save(existingDelivery);
        } else {
            return null; // Delivery not found
        }
    }

    public List<DeliveryDTO> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();


        return deliveries.stream()
                .map(delivery -> new DeliveryDTO(
                        delivery.getDeliveryId(),
                        delivery.getOrder().getOrderId(),
                        delivery.getDescription(),
                        delivery.getStatus(),
                        delivery.getDate()))
                .collect(Collectors.toList());
    }



}