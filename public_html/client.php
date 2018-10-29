<?php

// Tell the compiler to print any errors that occur
error_reporting(E_ALL);

// Notify the administrator that the client class is executing
echo "[Status] Initializing</br>";

// Get the message to be sent
$in = $_POST['input'];

// Notify the administrator that the input message has been received
echo "[Status] Input received</br>";
echo "         Message: " . $in . "</br>";

// Get the IP address by host name
$address = gethostbyname("code.cis.uafs.edu");

// Notify the administrator that the host name has been retrieved
echo "[Status] Retrieved host</br>";
echo "         Host: " . $address . "</br>";

// Set the port for communication
$port = "5000";

// Notify the administrator that the client socket is being created
echo "[Status] Creating client socket</br>";

// Create the client socket
$conn = "tcp://code.cis.uafs.edu:5000";
echo "Conn: " . $conn . "</br>";
$socket = stream_socket_client($conn, $errno, $errstr, 30);

echo "HERE</br>";

// Test the client socket
if(!$socket)
{
	echo "$errstr ($errno)<br />\n";
}

// Notify the administrator that the client is writing a message to the server
echo "[Status] Sending message to server connection</br>";

// Append end of message to input message
$in = $in . "\r\n";

// Write message to server connection
fwrite($socket, $in);

// Receive message from server connection
$response = fread($socket, 1048);
echo "Response: " + $response;

// Notify the administrator that the connection is being closed
echo "[Status] Closing connection</br>";

// Close the socket
fclose($socket);

// Notify the administrator that the program is terminating
echo "[Status] Terminating";

?>