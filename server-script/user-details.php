<?php
require_once("config.php");
require_once("helper_classes.php");

//Array for response data
$response = array();
$result = new Result();

if (!empty($_POST['email']) ) {
    $email = $_POST['email'];

    $stmt = $conn->prepare("SELECT name, phone, id FROM users WHERE email=?");
    $stmt->bind_param("s",$email);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($name, $phone, $id);
    $stmt->fetch();
    $rows = $stmt->num_rows;
    $stmt->close();
    if($rows>0) {      
        $response['user']['name'] = $name;
        $response['user']['phone'] = $phone;
        $response['user']['userId'] = $id;
        $response['user']['email'] = $email;

        $result->setErrorStatus(false);
        $result->setMessage("Retrieval Successful");
    } else {
        $result->setErrorStatus(true);
        $result->setMessage("Invalid credentials");
    }
} else {
    $result->setErrorStatus(true);
    $result->setMessage("insufficient parameters");
}

$response['result']['error'] = $result->isError();
$response['result']['message'] = $result->getMessage();

echo json_encode($response);
?>