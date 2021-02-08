package mdqa.jmeter.shared.objects

import java.util.concurrent.ConcurrentHashMap;

/**
 * The class represents container of {@link ConcurrentHashMap}. Allows you to save and share objects as kay with values between different threads and thread groups in the JMeter.
 * @author Michael Derevyanko
 */
class SharedHashMap
{
    private static final ConcurrentHashMap sharedHashMap = new ConcurrentHashMap()

    /**
     * Gets the instance of the {@link ConcurrentHashMap}
     * @return  the instance of the {@link ConcurrentHashMap}
     */
    static ConcurrentHashMap getInstance()
    {
        sharedHashMap
    }
}