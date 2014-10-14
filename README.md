main
====

CS2103T project of team w09-2j

Current functions and how to use them 
--------------

1. Add
  - add~task name~description~deadline~location~priority
  - Not all arguments have to be used. For example:
   	- add~task
  - To skip an argument, leave it blank. For example:
	- add~task name~~deadline~~priority
  - Deadline format: DD/MM/YYYY

2. Delete
  - delete~task number
  - The task number can be viewed through the display command

3. Update
  - update~task number~indicator~new info
  - The indicators are:
  	- name
  	- description
  	- deadline
  	- location
  	- priority

4. Clear
  - clear
  - Clears all data
  
5. Exit
  - exit
  - This exits the program
