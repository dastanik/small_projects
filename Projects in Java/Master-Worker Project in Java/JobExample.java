import alpv.mwp.Job;
import alpv.mwp.Pool;
import alpv.mwp.Task;

public class JobExample implements Job {

	private Double[] _numbers;
	protected TaskExample _task;
	protected Double _argument;
	public JobExample(Double[] numbers){
		_task = new TaskExample();
		_numbers = numbers;
	}

	public void split(Pool<Double> argPool, int workerCount) {
		for (Double number : _numbers) {
			argPool.put(number);
		}
	}

	public Task<Double, Double> getTask() {
		return _task;
	}

	public void merge(Pool result) {
	    Integer i = 0;
	    Object[] resultArray;
		while (result.get() != null) {
		    resultArray[i] = result.get();
		    i++;
		}
	}
}