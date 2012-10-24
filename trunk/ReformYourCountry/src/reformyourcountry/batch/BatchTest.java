package reformyourcountry.batch;

import org.springframework.stereotype.Service;

@Service
public class BatchTest implements Runnable{

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(BatchTest.class);
	}

	@Override
	public void run() {
		System.out.println("Hello world!!!");
	}

}
