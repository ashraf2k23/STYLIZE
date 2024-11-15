<?php
require_once("config.php");
require_once("helper_classes.php");


$json = file_get_contents('php://input');
$data = json_decode($json);

$response = array();
$result = new Result();

if ($data != null) {
    if (!empty($data->name) && !empty($data->email) && !empty($data->password) &&!empty($data->phone)) {
        $name = $data->name;
        $email = $data->email;
        $password = $data->password;
        $phone = $data->phone;

        $stmt = $conn->prepare("SELECT * FROM users WHERE email=?");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $stmt->store_result();
        $rows = $stmt->fetch();
        $stmt->close();
        if ($rows == 0) {
            $passEnc = password_hash($password, PASSWORD_DEFAULT);
            $stmt = $conn->prepare("INSERT INTO users(name, email, password, phone) VALUES (?,?,?,?)");
            $stmt->bind_param("ssss", $name, $email, $passEnc, $phone); 
            if ($stmt->execute()) {
                $result->setErrorStatus(false);
                $result->setMessage("registered successfully, please login");
            } else {
                $result->setErrorStatus(true);
                $result->setMessage("Something went wrong. Please retry");
            }
        } else {
            $result->setErrorStatus(true);
            $result->setMessage("you are already registered, please login");
        }
    } else {
        $result->setErrorStatus(true);
        $a = json_encode($data);
        $result->setMessage("insufficient parameters");
    }
} else {
    $result->setErrorStatus(true);
    $result->setMessage("no data received");
}

$response['error'] = $result->isError();
$response['message'] = $result->getMessage();
echo json_encode($response);
?>