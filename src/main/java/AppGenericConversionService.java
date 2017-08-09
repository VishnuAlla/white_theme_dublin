import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Uday Shankar
 */
public class AppGenericConversionService extends GenericConversionService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AppGenericConversionService() {
        super();
        this.addConverter(new Converter<Object, byte[]>() {
            @Override
            public byte[] convert(Object source) {
                try {
                    String data = AppGenericConversionService.toString((Serializable) source);
                    return data.getBytes();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        this.addConverter(new Converter<byte[], Object>() {
            @Override
            public Object convert(byte[] source) {
                try {
                    String s = new String(source);
                    return fromString(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /** Read the object from Base64 string. */
    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.decodeBase64(s);;
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return  Base64.encodeBase64String(baos.toByteArray());
    }
}
