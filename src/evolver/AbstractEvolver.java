package evolver;
import java.util.ArrayList;
import java.util.List;


public class AbstractEvolver implements Evolver{

	private List<EvolveListener> listeners;
	
	public AbstractEvolver(){
		listeners = new ArrayList<EvolveListener>();
	}
	
	public void addEvolveListener(EvolveListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void fireOnEvolve() {
		for (EvolveListener listener : listeners){
			listener.notifyEvolved();
		}		
	}	
}
