<?php
$address = "localhost";
$port = "5000";
$in = $_POST['input'];

$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
$clientConnection = socket_connect($socket, $address, $port);
socket_write($socket, $in, strlen($in));
socket_close($socket);

header('index.php');
?>