<?php

// Tell the compiler to print any errors that occur
error_reporting(E_ALL);

// Notify the administrator that the client class is executing
echo "[Status] Initializing</br>";

// Get the message to be sent
$in = $_POST['input'];

// Notify the administrator that the input message has been received
echo "[Status] Message: " . $in . "</br>";

// Notify the administrator that the client socket is being created
echo "[Status] Creating client socket</br>";

// Create the client socket
$socket = stream_socket_client("tcp://code.cis.uafs.edu:5000", $errno, $errstr, 30);

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

// Notify the administrator that the client is receiving a message from the server
echo "[Status] Receiving message from server connection</br>";

// Receive message from server connection
while (!feof($socket)) 
{
    echo fgets($socket, 4096);
}
echo "</br>";

// Notify the administrator that the connection is being closed
echo "[Status] Closing connection</br>";

// Close the socket
fclose($socket);

// Notify the administrator that the program is terminating
echo "[Status] Terminating";

?>