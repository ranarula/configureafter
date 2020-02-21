# configureafter
The idea is to load 1 bean from the possibly 3 beans defined in nested configuration file. However I am not able to sequence the order in which I want the beans to be loaded. 


This is what I get, though the intent is to load 1 of the 3 bean in any scenario.

```
Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2020-02-20 21:22:33.141 ERROR 97856 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   : 

***************************
APPLICATION FAILED TO START
***************************

Description:

The bean 'configHelper', defined in class path resource [com/example/demo/ServiceConfiguration$NoopConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [com/example/demo/ServiceConfiguration$DefaultConfiguration.class] and overriding is disabled.

Action:

Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
```

In this case since the property `a.b.c=true` is defined only bean defined by `NoopConfiguration` should have been initialized. But since there is no way to control the ordering of beans, the been defined by `DefaultConfiguration` is getting loaded first and then bean defined by `NoopConfiguration`

I want to first check for the property and try to load the bean via `NoopConfiguration` , if not then either of `UndertowConfiguration` or `DefaultConfiguration` should be loaded.
