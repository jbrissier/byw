# How to use Backyard #



### Server config ###

First you have to change your Tomcat Http Connector if you are using Tomcat Server
otherwise you can skip this step.

Go to your server.xml and change the following entry:

```
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000"          redirectPort="8443" />
```
to:

```
<Connector connectionTimeout="20000" port="8080" protocol="org.apache.coyote.http11.Http11NioProtocol" redirectPort="8443"/>
```
Second you have to add the following to your web.xml
```
  <servlet>
    <description></description>
    <display-name>comet</display-name>
    <servlet-name>comet</servlet-name>
    <servlet-class>de.jochenbrissier.backyard.BackyardServlet</servlet-class>
  
  </servlet>
  <servlet-mapping>
    <servlet-name>comet</servlet-name>
    <url-pattern>/comet</url-pattern>
  </servlet-mapping>
```

Third you have to import the following jars to your lib folder

  * guice-2.0.jar
  * json-rpc-1.0.jar
  * commons-logging.jar
  * backyard0.3.jar


## Client configuration ##
Import jQuery and the plugin to your HTML / JSP / ...

```

<script type="text/javascript" src="js/jqueryjs"></script>
<script type="text/javascript" src="js/jquery.backyard.js"></script>

```
## The jQuery Plugin ##
The plugin has the following options

  * url -> url to the servlet
  * socket\_url -> if you want to use websockets the websocket url -> look at the websocket howto
  * failover: set it to false if you whant to disable the backyard failover strategy
  * websocket: set it to false if you whant to diable the websocket support

```


jQuery.backyard({
 url:'a_servlet_url',
 socket_url: 'ws://${server}/${webapp}/socket',
 failover:true, //default
 websocket:true //default
});

```

### Methods ###
The plugin has the following methods:

Listen to channel:
```
jQuery.backyard.listen("a_channel",callback);
```

Publish to channel:
```
jQuery.backyard.publish("a_channel",data);
```



## Sample ##
> First we define a simple html page

```
<body>
<h1>byw Test</h1>


<!-- input box -->
<input type="text" id="mes" value="test"></input>

<!-- button -->
<input type="submit" id="btn" value="test">


</body>

```


then we start the comet process

```

<script type="text/javascript">

jQuery.backyard({url:"comet"});

...

```
now we bind our button to a click event and publish to the "test" channel


```
jQuery(document).ready(function(){
jQuery("#btn").click(function(){


	jQuery.backyard.publish("test", jQuery("#mes").val());
	
});
});


```

define a listener to the "test" channel


```

jQuery.backyard.listen("test",function(msg){

alert(msg);

});

```