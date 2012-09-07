package reformyourcountry.batch;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.exception.ExceptionUtil;
import reformyourcountry.web.ContextUtil;

public abstract class BatchUtil {
	
	/** Called by a main method to start a batch easily. That batch class must be a spring bean (@Service).
	 *  BatchUtil.startSpringBatch(CreateTestDataBatch.class);
	 */ 
//	public static void startSpringBatch(Class<? extends Runnable> batchClass) {
	public static void startSpringBatch(Class<? extends Runnable> batchClass) {
		ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{
		"applicationContext.xml" });
		//sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		//session = SessionFactoryUtils.getSession(sessionFactory, true);
		//TransactionSynchronizationManager.bindResource(sessionFactory,
		//		new SessionHolder(session));
		ContextUtil.contextInitialized(applicationContext);
//		Runnable batchObject = (Runnable)ContextUtil.getSpringBean("batchCreate");
		Runnable batchObject = ContextUtil.getSpringBean(batchClass);
		
		try{
			batchObject.run();

		} catch (Exception e) {
	        ExceptionUtil.printBatchUpdateException(e, System.err);
			throw new RuntimeException("Error in batch", e);

		} finally{
		    applicationContext.close();
		}
		
	}
		
}