package mdqa.jmeter.shared.objects

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * The class represents container of {@link Queue<String>}. Allows you to save and share String objects between different threads and thread groups in the JMeter.
 * @author Michael Derevyanko
 */
class SharedQueue
{
    private static final Queue<String> sharedQueue = new ConcurrentLinkedQueue<String>()

    /**
     * Gets the instance of the {@link Queue<String>}
     * @return  the instance of the {@link Queue<String>}
     */
    static Queue<String> getInstance()
    {
         return sharedQueue
    }
}