# JMeter shared objects plugin

## Description
The goal of this project is to prepare the global containers to save the variables, objects from the threads and treads group and share it to each other. 
Actually in the JMeter we have the built-in functionalities like BeanShell and JSR223. 
Is it not news that we can not use Beanshell for "heavy" operations, recommend considering switching to JSR223 Sampler and Groovy language otherwise you'll get performance comparable to native Java code.
It really already proved that Beanshell leads to issues with JVM while a huge load. From my opinion if you are really like Beanshell you can consider that while the functional testing but not while the load.
Even if we are using the JSR223 to share objets, values - it's not comfortable from the mechanism to use it, we have to work with global JMeter properties 'props' to play with HashMap, see examples bellow.

## The BeanShell example of the usage
* 1 Add BeanShell pre-processor under Test plan and define and share hash map.
  ~~~~
     Map myMap= new HashMap();
     bsh.shared.myMap;
     log.info(“Exporting Map”);

* 2 Add beanshell pre-processor in ThreadGroup-1 and add data to the map.
  ~~~~
     String key=vars.get(“key”);
     String value = vars.get(“value”);
     Map myMap = bsh.shared.myMap;
     myMap.put(key,value); 
  
* 3 Add below code to beanshell per processor in ThereadGroup-2 to get the implemented values in Hashmap
  ~~~~
     Map myMap = bsh.shared.myMap;
     String value=myMap.get(key)

## The JSR223 example of the usage
* 1 Add 'JSR223' sampler or pre-processor and defined the map in ThreadGroup-1.
    ~~~~
    def myMap = new HashMap()
    if (props.get(‘myMap’) != null) {
    myMap = props.get(‘myMap’)
    }
    myMap.put(key,value)
    props.put(‘myMap’, myMap)

* 2 Add ‘JSR223 sampler’ and get the implemented values from Hashmap in ThreadGroup-2
   ~~~~
    def myMap = props.get(‘myMap’)
    def value = refreshCodeMap.get(key)
    vars.put('key', 'value')
  
## The 'mdqa.jmeter.shared.objects' usage
* 1 Add 'JSR223' sampler or pre-processor and defined the map in ThreadGroup-1.
    ~~~~
    import mdqa.jmeter.shared.objects.*
  
    sharedHashMap = SharedHashMap.getInstance()
    sharedHashMap.put('key', 'value')

* 2 Add ‘JSR223 sampler’ and get the implemented values from Hashmap in ThreadGroup-2
    ~~~~
    import mdqa.jmeter.shared.objects.*
  
    sharedHashMap = SharedHashMap.getInstance()
    String tokenFromHash = sharedHashMap.get('key')
 
* 3 SharedStringQueue 
    
    There is 'SharedStringQueue' object has the similar functionality as 'SharedHashMap' - represents the 'ConcurrentLinkedQueue<String>()'.
    So the idea is to save values in the container from ThreadGroup-1 and read them in ThreadGroup-2.

    ~~~~  
    import mdqa.jmeter.shared.objects.*
    
    sharedStringQueue = SharedStringQueue.getInstance()
    sharedStringQueue.add('value')
  
    // get first item
    String myValue = sharedStringQueue[0]

* 4 To clean objects need to use 'tearDown' group and ‘JSR223 sampler’ with code
    ~~~~
    import mdqa.jmeter.shared.objects.*
  
    SharedHashMap.getInstance().clean()
    SharedStringQueue.getInstance().clear()
   
    // ingnore sampler results
    prev.setIgnore()    

## Conclusion 
 * The 'mdqa.jmeter.shared.objects' plugin can be used to solve the problem with objects sharing between threads and have easy access in the code.
 * Can be used for the simple tasks where you need to have only one shared 'HashMap' and 'ConcurrentLinkedQueue<String>()'.
 * The shared 'HashMap' ideally helps in the solution where you need to login all users in 'setUp' thread group with tokens saving. 
And reuse the 'HashMap' with logins (as key) and token (as value) in the main thread groups to invoke target requests.  
* JSR223 with HashMap and props can be used as alternative way for common tasks, but not 'BeanShell' :)

