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
	  	- start time
	  	- end time
	  	- location
	  	- priority

4. Search
	- search~indicator~key
	- The possible indicators are:
		- name
		- id (seen from the index numbers in the table)
	- The key is the word or letters that you are searching for

5. Sort
	- sort~indicator
	- The possible indicators are:
		- name
		- deadline (dd/mm/yyyy)
		- location
		- priority

6. Undo
 	- undo
 	- Undos the previous action

7. Redo
 	- redo
 	- Undos the previous undo

8. Clear
  	- clear
  	- Clears all data

9. Help
	- help
	- Displays the commands available and how to use them
  
10. Exit
  	- exit
  	- This exits the program
