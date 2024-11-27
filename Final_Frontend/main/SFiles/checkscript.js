document.addEventListener('DOMContentLoaded', () => {
    const checkoutGrid = document.getElementById('checkoutGrid');
    const totalPriceElement = document.getElementById('totalPrice');

    // Load cart from localStorage
    let cart = JSON.parse(localStorage.getItem('checkoutCart')) || [];

    // Function to calculate total price
    const calculateTotalPrice = () => {
        const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
        totalPriceElement.textContent = total.toFixed(2);
    };

    // Function to render checkout items as a table
    const renderCheckout = () => {
        checkoutGrid.innerHTML = ''; // Clear current grid

        if (cart.length === 0) {
            checkoutGrid.innerHTML = '<p>Your cart is empty.</p>';
            return;
        }

        // Create table structure
        const table = document.createElement('table');
        table.classList.add('checkout-table');

        // Create table header
        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Image</th>
                <th>Product Name</th>
                <th>Price (LKR)</th>
                <th>Quantity</th>
                <th>Subtotal (LKR)</th>
            </tr>
        `;
        table.appendChild(thead);

        // Create table body
        const tbody = document.createElement('tbody');

        cart.forEach(item => {
            // Ensure quantity defaults to 1 if undefined or invalid
            item.quantity = item.quantity && item.quantity > 0 ? item.quantity : 1;
        
            const row = document.createElement('tr');
        
            row.innerHTML = `
                <td><img src="${item.imageUrl}" alt="${item.productName}" style="width: 50px; height: 50px;"></td>
                <td>${item.productName}</td>
                <td>${item.price.toFixed(2)}</td>
                <td>
                    <input type="number" value="${item.quantity}" min="1" data-id="${item.productId}" style="width: 60px;">
                </td>
                <td class="subtotal">${(item.price * item.quantity).toFixed(2)}</td>
            `;
        
            tbody.appendChild(row);
        });
        
        table.appendChild(tbody);
        checkoutGrid.appendChild(table);

        calculateTotalPrice();
    };

    // Event listener for quantity changes
    checkoutGrid.addEventListener('input', (event) => {
        if (event.target.type === 'number') {
            const productId = event.target.getAttribute('data-id');
            const newQuantity = parseInt(event.target.value, 10);

            cart = cart.map(item => {
                if (item.productId === parseInt(productId, 10)) {
                    item.quantity = newQuantity;
                }
                return item;
            });

            localStorage.setItem('checkoutCart', JSON.stringify(cart));

            // Update subtotal for the changed row
            const row = event.target.closest('tr');
            const price = parseFloat(row.children[2].textContent);
            const subtotalCell = row.querySelector('.subtotal');
            subtotalCell.textContent = (price * newQuantity).toFixed(2);

            calculateTotalPrice();
        }
    });

    // Initial render
    renderCheckout();
});