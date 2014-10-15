main
====

CS2103T project of team w09-2j

Current functions and how to use them 
--------------

1. Add
	- add~task name~description~deadline~location~priority
 	- Deadline format: DD/MM/YYYY  
	- Not all arguments have to be used. For example:
	   	- add~task
	- To skip an argument, leave it blank. For example:
		- add~task name~~deadline~~priority
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
	- The indicator can be either the "name" or "id"
	- Input the task's name if searching through the name, and input the index number if searching through id

5. Clear
  	- clear
  	- Clears all data
  
6. Exit
  	- exit
  	- This exits the program
