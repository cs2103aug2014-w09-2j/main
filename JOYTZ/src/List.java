//package V1;

//package mainComponent;

import java.util.ArrayList;
import java.util.Collections;

public class List {
	ArrayList<Task> taskList = new ArrayList<Task>();
	String taskListName;

	/**
	 * Constructor
	 */
	List() {
	}

	List(String des) {
		this.taskListName = des;
	}

	/**
	 * Methods
	 */
	
	public void setListName(String name){
		this.taskListName = name;
	}
	
	public int size(){
		return taskList.size();
	}
	public boolean addTask(Task t) {
		if (t == null){
			return false;
		}
		taskList.add(t);
		return true;
	}

	public boolean deleteTaskByIndex(int index) {
		if (index < 0 || index >= taskList.size()){
			return false;
		}
		taskList.remove(index);
		return true;
	}
	
	public boolean deleteTaskById(int id){
		for (int index=0; index<taskList.size(); index++){
			Task currTask = taskList.get(index);
			if (currTask.getTaskId() == id){
				taskList.remove(index);
				return true;
			}
		}
		return false;
	}
	
	public Task getTaskByIndex(int index){
		if (index < 0 || index >= taskList.size()){
			return null;
		}
		return taskList.get(index);
	}
	
	public void sortList(){
		Collections.sort(taskList);
	}
	
	public boolean setTask (int index, Task task){
		if (index < 0 || index >= taskList.size()){
			return false;
		}
		taskList.set(index, task);
		return true;
	}
	
	public void clean(){
		taskList.clear();
	}
}
