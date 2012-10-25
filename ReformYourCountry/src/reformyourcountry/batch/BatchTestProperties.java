package reformyourcountry.batch;

import org.springframework.stereotype.Service;

import reformyourcountry.web.ContextUtil;

@Service
public class BatchTestProperties implements Runnable{

    public static void main(String[] args) {
        BatchUtil.startSpringBatch(BatchTest.class);
    }

    @Override
    public void run() {
        ContextUtil.springContext.getEnvironment().getProperty("name");
    }


}
