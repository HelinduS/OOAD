// Select the logout button
const logoutButton = document.getElementById("logoutBtn");

// Add a click event listener
logoutButton.addEventListener("click", () => {
    // Redirect to the desired page 
    window.location.href = "index.html"; 
});

// Function to fetch and display products 
function loadProducts() {
    fetch("http://localhost:8080/api/products/all")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch products");
            }
            return response.json();
        })
        .then(products => {
            const productGrid = document.getElementById("productGrid");
            productGrid.innerHTML = ""; // Clear existing product boxes

            products.forEach(product => {
                const productBox = document.createElement("div");
                productBox.classList.add("product-box");
                productBox.id = `product-${product.productId}`;

                productBox.innerHTML = `
                    <img src="${product.imageUrl}" alt="${product.productName}" class="product-image">
                    <div class="product-info">
                        <h3>${product.productName}</h3>
                        <p>${product.description}</p>
                        <p>Price: LKR ${product.price}</p>
                        <p>Stock: ${product.stockAvailability ? 'Available' : 'Out of stock'}</p>
                        <div class="button-container">
                            <button class="delete-btn" onclick="deleteProduct(${product.productId})">Delete</button>
                            <button class="update-btn" onclick="updateProduct(${product.productId}, '${product.price}')">Update</button>
                        </div>
                    </div>
                `;
                productGrid.appendChild(productBox);
            });
        })
        .catch(error => console.error("Error loading products:", error));
}

// Call loadProducts when the page loads
loadProducts();

function deleteProduct(productId) {
    fetch(`http://localhost:8080/api/products/delete?productId=${productId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorMsg => {
                throw new Error(errorMsg);
            });
        }
        return response.text();
    })
    .then(message => {
        const productRow = document.getElementById(`product-${productId}`);
        if (productRow) {
            productRow.remove();
        }
        alert(message); // "Product deleted successfully."
    })
    .catch(error => {
        console.error("Error deleting product:", error);
        alert(error.message || "Failed to delete product");
    });
}

// Function to update product price
function updateProduct(productId, currentPrice, currentStockAvailability) {
    const modal = `
    <div id="updateProductModal" class="modal show">
        <div class="modal-content">
            <span class="close-btn" onclick="closeUpdateProductModal()">&times;</span>
            <h2>Update Product</h2>
            <form id="updateProductForm">
                <label for="newPrice">Price (Current: LKR ${currentPrice}):</label>
                <input type="number" id="newPrice" name="newPrice" value="${currentPrice}" step="0.01" required><br><br>

                <label for="stockAvailability">Stock Availability:</label>
                <input type="checkbox" id="stockAvailability" name="stockAvailability" ${currentStockAvailability ? 'checked' : ''}><br><br>

                <button type="submit">Update Product</button>
            </form>
        </div>
    </div>
    `;
    document.body.insertAdjacentHTML("beforeend", modal);

    // Event listener for form submission
    const updateProductForm = document.getElementById('updateProductForm');
    updateProductForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const newPrice = document.getElementById('newPrice').value;
        const stockAvailability = document.getElementById('stockAvailability').checked;

        if (newPrice && !isNaN(newPrice)) {
            updateProductDetails(productId, parseFloat(newPrice), stockAvailability);
        } else {
            alert("Invalid price. Please enter a valid number.");
        }
    });
}

function updateProductDetails(productId, newPrice, stockAvailability) {
    const stockStatus = stockAvailability ? true : false;

    const url = `http://localhost:8080/api/products/update?productId=${productId}&price=${newPrice}&stockAvailability=${stockAvailability}`;

    fetch(url, {
        method: 'PUT',
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(errorText => {
                throw new Error(`Failed to update product: ${errorText}`);
            });
        }
        return response.text();
    })
    .then(data => {
        alert("Product updated successfully!"); 
        closeUpdateProductModal();
        loadProducts(); 
    })
    .catch(error => {
        console.error('Complete error details:', error);
        alert('Error updating product: ' + error.message);
    });
}
function closeUpdateProductModal() {
    const modal = document.getElementById('updateProductModal');
    if (modal) {
        modal.remove(); 
    }
}
function showAddProductModal() {
    console.log("showAddProductModal function called");

    const modal = `
    <div id="addProductModal" class="modal show">
        <div class="modal-content">
            <span class="close-btn" onclick="closeAddProductModal()">&times;</span>
            <h2>Add New Product</h2>
            <form id="addProductForm">
                <label for="productName">Product Name:</label>
                <input type="text" id="productName" name="productName" required><br><br>

                <label for="productDescription">Product Description:</label>
                <textarea id="productDescription" name="productDescription" required></textarea><br><br>

                <label for="price">Product Price:</label>
                <input type="number" id="price" name="price" required><br><br>

                <label for="stockAvailability">Product Stock Availability:</label>
                <input type="checkbox" id="stockAvailability" name="stockAvailability"><br><br>

                <label for="productImage">Product Image (Upload or URL):</label>
                <input type="file" id="productImage" name="productImage" accept="image/*">
                <input type="url" id="imageUrl" name="imageUrl" placeholder="Enter external image URL"><br><br>

                <button type="submit">Add Product</button>
            </form>
        </div>
    </div>
    `;
    document.body.insertAdjacentHTML("beforeend", modal);

    // Event listener for form submission
    const addProductForm = document.getElementById('addProductForm');
    addProductForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const productName = document.getElementById('productName').value;
        const productDescription = document.getElementById('productDescription').value;
        const price = document.getElementById('price').value;
        const stockAvailability = document.getElementById('stockAvailability').checked;
        const imageUrlInput = document.getElementById('imageUrl').value;
        const productImageFile = document.getElementById("productImage").files[0];  

        const imageUrl = productImageFile ? `style/Images/${productImageFile.name}` : imageUrlInput || './images/default.jpg';
        
        if (productName && productDescription && price && imageUrl) {
            // Create a product object
            const productData = {
                productName: productName,
                productDescription: productDescription,
                price: price,
                stockAvailability: stockAvailability,
                imageUrl: imageUrl
            };

            // Send product data to the backend using Fetch API
            fetch('http://localhost:8080/api/products/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(productData),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(createdProduct => {
                alert('Product added successfully');
                closeAddProductModal();
                loadProducts();  
            })
            .catch(error => {
                console.error('Error adding product:', error);
                alert('Failed to add product. Please try again later.');
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while adding the product. Please try again later.');
            });
        } else {
            alert("Please fill out all required fields.");
        }
    });
}

// Function to close the modal
function closeAddProductModal() {
    const modal = document.getElementById('addProductModal');
    if (modal) {
        modal.remove(); 
    }
}



