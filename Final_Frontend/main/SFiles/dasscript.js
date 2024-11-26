function loadCustomers() {
    fetch("http://localhost:8080/customer/all")  
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch customers");
            }
            return response.json();
        })
        .then(customers => {
            const customersTableBody = document.querySelector("#customers tbody");
            customersTableBody.innerHTML = ""; 

            customers.forEach(customer => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${customer.id}</td>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.email}</td>
                    <td>${customer.userName}</td>
                `;
                customersTableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error loading customers:", error));
}


function loadOrders() {
    fetch("http://localhost:8080/order/all")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch orders");
            }
            return response.json();
        })
        .then(orders => {
            const ordersTableBody = document.querySelector("#orders tbody");
            ordersTableBody.innerHTML = "";

            orders.forEach(order => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${order.orderId}</td>
                    <td>${order.customerId}</td>
                    <td>${order.description}</td>
                    <td>${order.status}</td>
                    <td>
                        <button class="action-btn" onclick="updateOrderStatus(${order.orderId})">Update Status</button>
                    </td>
                `;
                ordersTableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error loading orders:", error));
}


function loadDeliveries() {
    fetch("http://localhost:8080/api/deliveries/all")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch deliveries");
            }
            return response.json();
        })
        .then(deliveries => {
            const deliveryTableBody = document.getElementById("deliveryTableBody");
            deliveryTableBody.innerHTML = ""; 

            deliveries.forEach(delivery => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${delivery.deliveryId}</td>
                    <td>${delivery.orderId}</td>
                    <td>${delivery.description}</td>
                    <td>${delivery.date}</td>
                    <td>${delivery.status}</td>
                    <td>
                        <button class="action-btn" onclick="updateDeliveryStatus(${delivery.deliveryId})">Update Status</button>
                    </td>
                `;
                deliveryTableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error loading deliveries:", error));
}

function showAddDeliveryModal() {
    const modal = `
    <div id="addDeliveryModal" class="modal show">
        <div class="modal-content">
            <span class="close-btn" onclick="closeAddDeliveryModal()">&times;</span>
            <h2>Add New Delivery</h2>
            <form id="addDeliveryForm">
                <label for="orderId">Order ID:</label>
                <input type="number" id="orderId" name="orderId" required><br><br>

                <label for="description">Delivery Description:</label>
                <textarea id="description" name="description" required></textarea><br><br>

                <label for="date">Delivery Date:</label>
                <input type="date" id="date" name="date" required><br><br>

                <label for="status">Delivery Status:</label>
                <input type="text" id="status" name="status" required><br><br>

                <button type="submit">Add Delivery</button>
            </form>
        </div>
    </div>
    `;
    document.body.insertAdjacentHTML("beforeend", modal);

   
    const addDeliveryForm = document.getElementById('addDeliveryForm');
    addDeliveryForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const orderId = document.getElementById('orderId').value;
        const description = document.getElementById('description').value;
        const date = document.getElementById('date').value;
        const status = document.getElementById('status').value;

        if (orderId && description && date && status) {
            const deliveryData = {
                orderId: orderId,
                description: description,
                date: date,
                status: status
            };

            fetch('http://localhost:8080/api/deliveries/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(deliveryData),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to add delivery');
                }
                return response.json();
            })
            .then(newDelivery => {
                alert('Delivery added successfully');
                closeAddDeliveryModal();
                loadDeliveries(); 
            })
            .catch(error => {
                console.error('Error adding delivery:', error);
                alert('Failed to add delivery. Please try again later.');
            });
        } else {
            alert("Please fill out all required fields.");
        }
    });
}


function closeAddDeliveryModal() {
    const modal = document.getElementById('addDeliveryModal');
    if (modal) {
        modal.remove(); 
    }
}


function updateOrderStatus(orderId) {
    const newStatus = prompt("Enter new order status:");

    if (newStatus) {
        fetch(`http://localhost:8080/order/update-status?orderId=${orderId}&status=${newStatus}`, {
            method: "PUT",
        })
            .then(response => response.json())
            .then(updatedOrder => {
                alert("Order status updated successfully!");
                loadOrders(); 
            })
            .catch(error => {
                console.error("Error updating order status:", error);
            });
    }
}

function updateDeliveryStatus(deliveryId) {
    const newStatus = prompt("Enter new delivery status:");

    if (newStatus) {
        fetch(`http://localhost:8080/api/deliveries/update-status?deliveryId=${deliveryId}&status=${newStatus}`, {
            method: "PUT",
        })
            .then(response => response.json())
            .then(updatedDelivery => {
                alert("Delivery status updated successfully!");
                loadDeliveries(); 
            })
            .catch(error => {
                console.error("Error updating delivery status:", error);
            });
    }
}
