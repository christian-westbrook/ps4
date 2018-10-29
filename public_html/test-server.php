<?php

// Tell the compiler to print any errors that occur
error_reporting(E_ALL);

// Notify the administrator that the server class is executing
echo "[Status] Initializing\n";

// Get the message to be sent
$in = $_POST['input'];

// Notify the administrator that the input message has been received
echo "[Status] Input received\n";
echo "         Input: " . $in . "\n";

// Get the IP address by host name
$address = gethostbyname("code.cis.uafs.edu");

// Notify the administrator that the host name has been retrieved
echo "[Status] Retrieved host\n";
echo "         Host: " . $address . "\n";

// Set the port for communication
$port = "5000";

// Notify the administrator that the client socket is being created
echo "[Status] Creating client socket\n";

// Create the client socket
$socket = stream_socket_client("tcp:" . $address . ":" . $port, $errno, $errstr, 30);

echo "HERE";

// Test the client socket
if(!$socket)
{
	echo "$errstr ($errno)<br />\n";
}

// Notify the administrator that the client is writing a message to the server
echo "[Status] Sending message to server connection\n";

// Append end of message to input message
$in = $in . "\r\n";

// Write message to server connection
fwrite($socket, $in);

// Notify the administrator that the connection is being closed
echo "[Status] Closing connection\n";

// Close the socket
fclose($socket);

// Notify the administrator that the program is terminating
echo "[Status] Terminating";

?>