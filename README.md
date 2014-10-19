main
====

CS2103T project of team w09-2j

Current functions and how to use them 
--------------

1. Add
	- add ~ task name ~ description ~ start deadline ~ end deadline ~ location ~ priority
 	- Deadline format: 
		- DD/MM/YYYY HH:MMam
		- DD/MM/YYYY HH:MMpm
		- HH:MMam DD/MM/YYYY
		- HH:MMpm DD/MM/YYYY
	- Not all arguments have to be used. For example:
	   	- add~task
	- To skip an argument, leave it blank. For example:
		- add~task name~~~end deadline~~priority
	- The order must be strictly followed
  

2. Delete
  	- delete~task number
  	- The task number can be viewed from the table in the display

3. Update
  	- update~task number~indicator~new info
  	- The possible indicators are:
	  	- name
	  	- description
	  	- deadline
	  	- location
	  	- priority

4. Search
	- search~indicator~key
	- The possible indicators are:
		- name
		- id (seen from the index numbers in the table)
	- Input the task's name if searching through the name, and input the index number if searching through id

5. Sort
	- sort~indicator
	- The possible indicators are:
		- name
		- deadline (dd/mm/yyyy)
		- location
		- priority
	
6. Clear
  	- clear
  	- Clears all data

7. Help
	- help
	- Displays the commands available and how to use them
  
8. Exit
  	- exit
  	- This exits the program

To do 
--------------
1. Undo
