package bg.sofia.uni.fmi.ces.model.facade;

public interface IModelFacade {

	public void close();
	
	public void persist(Object entityObject);
	
	public void beginTransaction();
	
	public void commitTransaction();
	
	public void rollbackTransaction();
}
