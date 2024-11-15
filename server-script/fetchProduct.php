<?php
require_once("config.php");
require_once("helper_classes.php");

//Array for response data
$response = array();
$result = new Result();

if (!empty($_POST['query'])) {
    $product = $_POST['query'];

    if(strtolower($product) == "men" ){
        $stmt = $conn->prepare("SELECT * FROM products WHERE LOWER(gender) LIKE LOWER(?) OR LOWER(gender) = 'unisex'");
        $stmt->bind_param("s", $product);
    }else{
        $stmt = $conn->prepare("SELECT * FROM products
            WHERE LOWER(description) LIKE LOWER(?) 
            OR LOWER(name) LIKE LOWER(?) 
            OR LOWER(type) LIKE LOWER(?) 
            OR LOWER(tag) LIKE LOWER(?) 
            OR LOWER(gender) LIKE LOWER(?)");

        $search_term = '%' . $product . '%';


        // Binding parameters to the query
        $stmt->bind_param("sssss", $search_term, $search_term, $search_term, $search_term, $search_term);
    }
    // Execute the query
    if ($stmt->execute()) {
        // Get the result
        $report = $stmt->get_result();

        // Check if results exist
        if ($report->num_rows > 0) {
            $response['data'] = []; // Initialize the 'data' array
            $count = 0;

            // Fetch the results
            while ($row = $report->fetch_assoc()) {
                $response['data'][$count] = [
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
                ];
                $count++;
            }
            $result->setErrorStatus(false);
            $result->setMessage("Success");
        } else {
            $result->setErrorStatus(true);
            $result->setMessage("No products found.");
        }
    } else {
        $result->setErrorStatus(true);
        $result->setMessage("Failed to execute the query: " . $stmt->error);
    }
} else {
    $result->setErrorStatus(true);
    $result->setMessage("Insufficient parameters");
}

$response['result']['error'] = $result->isError();
$response['result']['message'] = $result->getMessage();

echo json_encode($response);

?>