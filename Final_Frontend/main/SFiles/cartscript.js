document.addEventListener('DOMContentLoaded', () => {
    
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    // Reference to the cart table body and empty cart message
    const cartTableBody = document.querySelector('#cart-table-body');
    const emptyCartMessage = document.querySelector('#empty-cart-message');

    // Function to render cart
    const renderCart = () => {
        
        cartTableBody.innerHTML = '';

        if (cart.length === 0) {
            emptyCartMessage.style.display = 'block';
            return;
        }

        emptyCartMessage.style.display = 'none';

        
        cart.forEach((product, index) => {
            if (product && product.productName) {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${product.productId}</td>
                    <td><img src="${product.imageUrl}" alt="${product.productName}" style="width: 50px; height: 50px;"></td>
                    <td>${product.productName}</td>
                    <td>${product.description}</td>
                    <td>LKR ${product.price}</td>
                    <td>${product.stockAvailability ? 'In Stock' : 'Out of Stock'}</td>
                    <td>
                        <button class="remove-btn" data-index="${index}" style="color: white; background-color: red; border: none; padding: 5px 10px; cursor: pointer;">
                            Remove
                        </button>
                    </td>
                `;
                cartTableBody.appendChild(row);
            }
        });
    };

    // Remove product 
    cartTableBody.addEventListener('click', (event) => {
        if (event.target.classList.contains('remove-btn')) {
            const index = event.target.getAttribute('data-index');
            
            
            cart.splice(index, 1);
            
            
            localStorage.setItem('cart', JSON.stringify(cart));
            
          
            renderCart();
        }
    });

   
    renderCart();
});