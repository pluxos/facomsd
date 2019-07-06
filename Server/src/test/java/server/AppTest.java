package server;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        
        Server.main(new String[] {"0","127.0.0.1","5000","127.0.0.1","5001","127.0.0.1","5002"});
        Server.main(new String[] {"1","127.0.0.1","5000","127.0.0.1","5001","127.0.0.1","5002"});
        Server.main(new String[] {"2","127.0.0.1","5000","127.0.0.1","5001","127.0.0.1","5002"});
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}
