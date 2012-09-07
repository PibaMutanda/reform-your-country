package reformyourcountry.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class StreamUtils {

	public static byte[] getByteArrayFromInputStream(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] singleByteBuffer = new byte[1];
		while ((inputStream.read(singleByteBuffer, 0, 1)) != -1) {
			out.write(singleByteBuffer);
		}
		out.close();
		return out.toByteArray();
	}
}