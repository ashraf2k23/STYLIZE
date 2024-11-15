<?php
require_once("config.php");
require_once("helper_classes.php");

$result = array();

if (!empty($_POST['userId']) && !empty($_POST['productId']) && !empty($_POST['quantity']) && !empty($_POST['size'])) {

    $product = $_POST['productId'];
    $user = $_POST['userId'];
    $quantity = $_POST['quantity'];
    $size = $_POST['size'];

    $stmt = $conn->prepare("SELECT * FROM users WHERE id = ?");
    $stmt->bind_param("s", $user);
    $stmt->execute();
    $stmt->store_result();
    $userExists = $stmt->num_rows > 0;
    $stmt->close();

    $stmt = $conn->prepare("SELECT * FROM products WHERE product_id = ?");
    $stmt->bind_param("s", $product);
    $stmt->execute();
    $stmt->store_result();
    $productExists = $stmt->num_rows > 0;
    $stmt->close();

    if (!$userExists || !$productExists) {
        $result["error"] = true;
        $result["message"] = "User ID or Product ID does not match.";
    } else {
        try {
            $stmt = $conn->prepare("SELECT quantity, size FROM cart WHERE product_id = ? AND size = ? AND id = ?");
            $stmt->bind_param("sss", $product, $size, $user);
            $stmt->execute();
            $reponse = $stmt->get_result();

            if ($reponse->num_rows > 0) {
                $row = $reponse->fetch_assoc();
                $newQuantity = $row['quantity'] + $quantity;

                $updateStmt = $conn->prepare("UPDATE cart SET quantity = ? WHERE product_id = ?");
                $updateStmt->bind_param("is", $newQuantity, $product);
                $updateStmt->execute();

                $result["error"] = false;
                $result["message"] = "Product updated in cart successfully.";
            } else {
                $stmt = $conn->prepare("INSERT INTO cart (id, product_id, quantity, size) VALUES (?, ?, ?, ?)");
                $stmt->bind_param("ssss", $user, $product, $quantity, $size);

                if ($stmt->execute()) {
                    $result["error"] = false;
                    $result["message"] = "Product added to cart successfully.";
                } else {
                    $result["error"] = true;
                    $result["message"] = "Failed to add product to cart.";
                }
                $stmt->close();
            }
        } catch (mysqli_sql_exception $e) {
            $result["error"] = true;
            $result["message"] = "Foreign key constraint violation: " . $e->getMessage();
        }
    }
} else {
    $result["error"] = true;
    $result["message"] = "Required parameters are missing.";
}

echo json_encode($result);
?>
