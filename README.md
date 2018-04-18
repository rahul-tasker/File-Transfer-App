# File-Transfer-App
File transfer protocol that operates with a psuedo-Napster framework

TO RUN:
run server with port as argument.
run client with server and port as arguments.
The commands requires very particular instructions. 
For example word "request" must be lowercase and the file must be spelled exactly right.
If there is a search result, input the desired ip and port number separated by a space.

Enter any of the commands as instructed on the terminal

MY APPROACH:
The most difficult part of this for me was organizing the search so that they would 
connect directly while allowing the sending peer to make its own commands
I created two threads to send and receive files and organized the commands in the client file
The rating system starts at 5, and can vary depending on the input of the user with the rate command
Unregister removes the file from the Broker's list but the thread stays open to re-register.
