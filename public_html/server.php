<?php

// Tell the compiler to print any errors that occur
error_reporting(E_ALL);

// Get the message to be sent
$in = $_POST['input'];

// Get the IP address by host name
$address = gethostbyname("code.cis.uafs.edu");

// Set the port for communication
$port = "5000";

// Create the client socket
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);

// Test the client socket
if($socket === false)
{
	echo "socket_create() failed.";
	echo socket_strerror(socket_last_error()) . "\n";
}

// Attempt to connect to the server with the client socket
$result = socket_connect($socket, $address, $port);

// Test the server connection
if($result === false)
{
	echo "socket_connect() failed.";
	echo socket_strerror(socket_last_error($socket)) . "\n";
}

// Append end of message to input message
$in = $in . "\r\n\r\n";

// Write message to server connection
socket_write($socket, $in, strlen($in));

// Close the socket
socket_close($socket);

?>