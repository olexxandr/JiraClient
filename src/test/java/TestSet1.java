import com.jiraclient.JiraTicket;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;



public class TestSet1 extends TestCase {

    @JiraTicket("APNA-5027")
    public void testGetString() throws Exception {

        Assert.assertTrue("APNA-5027: test description", "a".equals("a"));
    }
}