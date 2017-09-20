import com.jiraclient.JiraTicket;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


public class TestSet2 extends TestCase {

    @JiraTicket("APNA-5025")
    public void testGetString() throws Exception {
        Assert.assertTrue("APNA-5025: test description", "a".equals("a"));
    }
}