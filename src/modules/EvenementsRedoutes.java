package modules;

public class EvenementsRedoutes {
	
	private 

	
	private EvenementsRedoutes(){
		
	}
	
	private static class EvenementsRedoutesHolder{
		private final static EvenementsRedoutes instance=new EvenementsRedoutes();
	}
	
	public static EvenementsRedoutes getEvenementsRedoutes(){
		return EvenementsRedoutesHolder.instance;
	}
}
