<?php
require_once("config.php"); // Include your database configuration
require_once("helper_classes.php"); // Include any necessary helper classes

// Array for response data
$response = array();

// Check if 'userId' is passed
if (!empty($_POST['userId'])) {
    $userId = $_POST['userId'];

    // Prepare and execute the first query to get cart items
    $stmt = $conn->prepare("SELECT c.cart_id, c.product_id, c.quantity, c.size
                            FROM cart c
                            JOIN products p ON c.product_id = p.product_id
                            WHERE c.id = ?");
    $stmt->bind_param("s", $userId);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($cartId, $productId, $quantity, $size);

    if ($stmt->num_rows > 0) {
        $cartItems = array();

        while ($stmt->fetch()) {
            // Prepare and execute the query to get product details
            $product = $conn->prepare("SELECT * FROM products WHERE product_id = ?");
            $product->bind_param("s", $productId);

            if ($product->execute()) {
                $productResult = $product->get_result(); // Corrected: Use $product for get_result()
                
                if ($productResult->num_rows > 0) {
                    $products = array();

                    while ($row = $productResult->fetch_assoc()) {
                        $products[] = array(
                            'product_id' => $row['product_id'],
                            'name' => $row['name'],
                            'description' => $row['description'],
                            'tag' => $row['tag'],
                            'type' => $row['type'],
                            'size' => $row['sizes'],
                            'gender' => $row['gender'],
                            'price' => $row['price'],
                            'discount' => $row['discount'],
                            'image' => $row['images']
                        );
                    }
                    // Add product details to cart item
                    $cartItems[] = array(
                        'cart' => $cartId,
                        'quantity' => $quantity,
                        'product' => $products,
                        'size' => $size
                    );
                } else {
                    $response["result"]["error"] = true;
                    $response["result"]["message"] = "No product details found for product_id: $productId.";
                }
            } else {
                $response["result"]["error"] = true;
                $response["result"]["message"] = "Failed to execute the query for product details.";
            }
        }

        // Set response data
        $response["result"]["error"] = false;
        $response["result"]["message"] = "Cart items fetched successfully.";
        $response["cart_items"] = $cartItems;
    } else {
        // No items in the cart
        $response["result"]["error"] = true;
        $response["result"]["message"] = "No items found in cart.";
    }

    $stmt->close();
} else {
    // If userId is not passed
    $response["result"]["error"] = true;
    $response["result"]["message"] = "User ID is missing.";
}

// Return the response as JSON
echo json_encode($response);
?>
