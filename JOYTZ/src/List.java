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

	List(String name) {
		this.taskListName = name;
	}

	/**
	 * Methods
	 */

	public void setListName(String name) {
		this.taskListName = name;
	}

	public int size() {
		return taskList.size();
	}

	public boolean addTask(Task t) {
		if (t == null) {
			return false;
		}
		taskList.add(t);
		return true;
	}

	public boolean deleteTaskByIndex(int index) {
		if (index < 0 || index >= taskList.size()) {
			return false;
		}
		taskList.remove(index);
		return true;
	}

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

	public Task getTaskByIndex(int index) {
		if (index < 0 || index >= taskList.size()) {
			return null;
		}
		return taskList.get(index);
	}

	public void sortList() {
		Collections.sort(taskList);
	}

	public boolean setTask(int index, Task task) {
		if (index < 0 || index >= taskList.size()) {
			return false;
		}
		taskList.set(index, task);
		return true;
	}

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

	public List copy() {
		List copyList = new List();
		for (int index = 0; index < size(); index++) {
			copyList.addTask(taskList.get(index));
		}
		return copyList;
	}

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
