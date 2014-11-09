// @author A0119378U
import java.util.ArrayList;
import java.util.Collections;

/**
 * Description : 
 * 
 * List Object contains Task Objects.
 * @author Zhang Kai (A0119378U)
 *
 */
public class List {
	ArrayList<Task> taskList = new ArrayList<Task>();
	String taskListName;

	/**
	 * Constructor
	 */
	List() {
	}

	List(String name) {
		this.taskListName = name;
	}

	/**
	 * Methods
	 */
	
	/**
	 * Set the nameString of this List.
	 * @param name
	 */
	public void setListName(String name) {
		this.taskListName = name;
	}
	
	/**
	 * Get the size of taskList.
	 * @return
	 */
	public int size() {
		return taskList.size();
	}
	
	/**
	 * Add a new task into the taskList.
	 * @param t
	 * @return
	 */
	public boolean addTask(Task t) {
		if (t == null) {
			return false;
		}
		taskList.add(t);
		return true;
	}

	/**
	 * Delete a task by the index of the task in the ArrayList.
	 * 
	 * @param index
	 * @return
	 */
	public boolean deleteTaskByIndex(int index) {
		if (index < 0 || index >= taskList.size()) {
			return false;
		}
		taskList.remove(index);
		return true;
	}

	/**
	 * Delete a task by it unique taskId.
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteTaskById(int id) {
		for (int index = 0; index < taskList.size(); index++) {
			Task currTask = taskList.get(index);
			if (currTask.getTaskId() == id) {
				taskList.remove(index);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the Task object in the given position of taskList.
	 * @param index
	 * @return
	 */
	public Task getTaskByIndex(int index) {
		if (index < 0 || index >= taskList.size()) {
			return null;
		}
		return taskList.get(index);
	}
	
	/**
	 * Get the index of task with provided Id. 
	 * Search through the list.
	 * @param targetTaskId
	 * @return
	 */
	public int getIndexByTaskId(int targetTaskId){
		int result = -1;
		for (int index=0; index<taskList.size(); index++){
			Task currTask = taskList.get(index);
			if (currTask.getTaskId() == targetTaskId){
				result = index;
				break;
			}
		}
		return result;
	}
	/**
	 * Sort the taskList array. The key is in the Task object.
	 */
	public void sortList() {
		Collections.sort(taskList);
	}

	/**
	 * Add in a task to a certain position in taskList array, and remove the
	 * original one.
	 * 
	 * @param index
	 * @param task
	 * @return
	 */
	public boolean setTask(int index, Task task) {
		if (index < 0 || index >= taskList.size()) {
			return false;
		}
		taskList.set(index, task);
		return true;
	}

	/**
	 * Delete all tasks in this list.
	 */
	public void clean() {
		taskList.clear();
	}

	/**
	 * Reture true if taskList contains a task with the same Id as given
	 * targetId.
	 * 
	 * @param targetTaskId
	 * @return
	 */
	public boolean containsTaskId(int targetTaskId) {
		for (int index = 0; index < taskList.size(); index++) {
			Task currTask = taskList.get(index);
			if (currTask.getTaskId() == targetTaskId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return a new list contains the same task objects.
	 * 
	 * @return
	 */
	public List copy() {
		List copyList = new List();
		for (int index = 0; index < size(); index++) {
			copyList.addTask(taskList.get(index));
		}
		return copyList;
	}

	/**
	 * Return an arrayList contains the String format of every task inside the
	 * taskList.
	 * 
	 * @return
	 */
	public ArrayList<String> convertToString() {
		ArrayList<String> resultList = new ArrayList<String>();

		for (int index = 0; index < size(); index++) {
			resultList.add(taskList.get(index).toString());
		}
		return resultList;
	}

	/**
	 * Task has unique TaskId when the program is run. Use this feature to
	 * compare two list.
	 * 
	 * @param list
	 * @return
	 */
	public boolean equals(List list) {
		for (int index = 0; index < taskList.size(); index++) {
			Task currTask = taskList.get(index);
			int currId = currTask.getTaskId();
			if (!list.containsTaskId(currId)) {
				return false;
			}
		}
		return true;
	}
}
