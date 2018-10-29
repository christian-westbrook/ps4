<?php

// Tell the compiler to print any errors that occur
error_reporting(E_ALL);

// Notify the administrator that the server class is executing
echo "[Status] Initializing";

// Get the message to be sent
$in = $_POST['input'];

// Notify the administrator that the input message has been received
echo "[Status] Input received";
echo "         Input: " + $in;

// Get the IP address by host name
$address = gethostbyname("code.cis.uafs.edu");

// Notify the administrator that the host name has been retrieved
echo "[Status] Retrieved host";
echo "         Host: " + $address;

// Set the port for communication
$port = "5000";

// Notify the administrator that the client socket is being created
echo "[Status] Creating client socket";

// Create the client socket
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);

// Test the client socket
if($socket === false)
{
	echo "socket_create() failed.";
	echo socket_strerror(socket_last_error()) . "\n";
}

// Notify the administrator that the client is attempting to connect to the server
echo "[Status] Attempting connection to server";

// Attempt to connect to the server with the client socket
$result = socket_connect($socket, $address, $port);

// Test the server connection
if($result === false)
{
	echo "socket_connect() failed.";
	echo socket_strerror(socket_last_error($socket)) . "\n";
}

// Notify the administrator that the client is writing a message to the server
echo "[Status] Sending message to server connection";

// Append end of message to input message
$in = $in . "\r\n\r\n";

// Write message to server connection
socket_write($socket, $in, strlen($in));

// Notify the administrator that the connection is being closed
echo "[Status] Closing connection";

// Close the socket
socket_close($socket);

// Notify the administrator that the program is terminating
echo "[Status] Terminating";

?>