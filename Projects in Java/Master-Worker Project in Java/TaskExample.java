import alpv.mwp.Task;
import java.lang.Math;

public abstract class TaskExample implements Task {

	public Double exec(Double val) {
	    Double result = Math.ceil(val);
		return result;
	}
}