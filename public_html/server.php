<?php
$address = "127.0.0.1";
$port = "5000";
$in = $_POST['input'];

$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
$result = socket_connect($socket, $address, $port);
socket_write($socket, $in  . "\r\n", strlen($in . "\r\n"));
socket_close($socket);

?>