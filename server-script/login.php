<?php
require_once("config.php");
require_once("helper_classes.php");

//Array for response data
$response = array();
$result = new Result();

if (!empty($_POST['email']) && !empty($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $stmt = $conn->prepare("SELECT password FROM users WHERE email=?");
    $stmt->bind_param("s",$email);
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($dbPass);
    $stmt->fetch();
    $rows = $stmt->num_rows;
    $stmt->close();
    if($rows>0) {
        if (password_verify($password,$dbPass)) {
            $stmt = $conn->prepare("SELECT name,id FROM users WHERE email=?");
            $stmt->bind_param("s",$email);
            $stmt->execute();
            $stmt->store_result();
            $stmt->bind_result($name, $id);
            $stmt->fetch();
            $stmt->close();

            $response['user']['name'] = $name;
            $response['user']['email'] = $email;
            $response['user']['userId'] = $id;

            $result->setErrorStatus(false);
            $result->setMessage("login successful");
        } else {
            $result->setErrorStatus(true);
            $result->setMessage("Invalid password");
        }
    } else {
        $result->setErrorStatus(true);
        $result->setMessage("Mail-id not registered");
    }
} else {
    $result->setErrorStatus(true);
    $result->setMessage("insufficient parameters");
}

$response['result']['error'] = $result->isError();
$response['result']['message'] = $result->getMessage();

echo json_encode($response);
?>