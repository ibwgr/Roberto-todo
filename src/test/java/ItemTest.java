
import com.despegar.http.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class ItemTest extends FunctionalTest {


    @Test
    public void getItemsIsOk(){
        HttpResponse httpResponse = this.executeGet("/items");
        assertThat(httpResponse.code(), is(200));
    }

    @Test
    public void getItemsInhalt() throws IOException {
        HttpResponse httpResponse = this.executeGet("/items");
        String body = new String (httpResponse.body());
        List<Item> deserializedItem = new JSONSerializer().deserialize(body, new TypeReference<ArrayList<Item>>() {});
        Assert.assertEquals("Hallo World", deserializedItem.get(0).description);
    }


    @Test
    public void getAcceptTypeErrorXML(){
        HttpResponse httpResponse = this.executeGet("/items", "application/xml");
        assertThat(httpResponse.code(), is(406));
    }

    @Test
    public void getAcceptTypeErrorHTML(){
        HttpResponse httpResponse = this.executeGet("/items", "text/html");
        assertThat(httpResponse.code(), is(406));
    }

    @Test
    public void checkExpectedType(){
        HttpResponse httpResponse = this.executeGet("/items", "application/json");
        assertThat(httpResponse.code(), is(200));
    }
}
