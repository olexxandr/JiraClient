import com.jira.customtestrunner.ExecutionListener;
import org.junit.runner.JUnitCore;


public class TestExecutor
{
    public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
        runner.addListener(new ExecutionListener());
        runner.run(TestSet2.class, TestSet1.class);
    }
}