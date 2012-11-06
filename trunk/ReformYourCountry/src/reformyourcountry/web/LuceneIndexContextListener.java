package reformyourcountry.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import reformyourcountry.service.IndexManagerService;
import reformyourcountry.util.FileUtil;



/** Checks at application startup that the Lucene index exists.
 * Should not be useful in production, because the index is never deleted (unless manually)
 * But on a new developper's machine, there is no index...
 * 
 * @author Delphine
 *
 */
public class LuceneIndexContextListener implements ServletContextListener{


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		IndexManagerService indexManagerService = ContextUtil.getSpringBean(IndexManagerService.class);
		File file = new File(FileUtil.getLuceneIndexDirectory());
		if(file.isDirectory()) {  // The directory exists
			if(file.list().length>0){  // It's full of index files.
				// Do nothing, the index is there !!!
			} else { // Index has to be built.
				indexManagerService.createIndexes();
			}
		} else {  // Dir and index have to be built.
			file.mkdirs();
			indexManagerService.createIndexes();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Nothing to do here.
	}


}
