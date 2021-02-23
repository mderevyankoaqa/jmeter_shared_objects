package mdqa.jmeter.shared.objects

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * The class represents container of {@link Queue<T>}. Allows you to save and share String objects between different threads and thread groups in the JMeter.
 * @author Michael Derevyanko
 */
class SharedStringQueue
{
    private static final Queue<String> sharedQueue = new ConcurrentLinkedQueue<String>()

    /**
     * Gets the instance of the {@link Queue<T>}
     * @return  the instance of the {@link Queue<T>}
     */
    static Queue<String> getInstance()
    {

         return sharedQueue
    }
}