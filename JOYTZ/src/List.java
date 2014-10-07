package V1;

//package mainComponent;

import java.util.ArrayList;

public class List {
	ArrayList<Task> listOfTask;
	int numberOfTask;
	String description;
	
	/**
	 * Constructor
	 */
	List(){}
	List(String des){
		this.listOfTask = new ArrayList<Task>();
		this.numberOfTask = 0;
		this.description = des;
	}
	List(Task t){
		this.listOfTask = new ArrayList<Task>();
		this.numberOfTask = 0;
		this.description = "";
		
		addTask(t);
	}
	
	/**
	 * Methods
	 */
	
	public void addTask(Task t){
		
	}
	
	public void deleteTask(Task t){
		
	}
	/*
	public ArrayList<Task> filter(Date start, Date end){
		
	}
	
	public ArrayList<Task> getTaskBeforeDate(Date d){
		
	}
	
	public ArrayList<Task> getTaskInDay(Date d){
		
	}
	
	public ArrayList<Task> getTaskInWeek(Date d){
		
	}
	*/
	public void checkTaskStatus(){
		
	}
}
