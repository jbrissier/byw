#How to enable Websocket Support

# Introduction #
Look at the backyard.xml. There is an entry (Websocketsupport)  for each server, to see if your server supports websockets.

### Define the socket in the web.xml ###
Next step define the socket servlet in your web.xml


```
 <servlet>
    <servlet-name>socket</servlet-name>
    <servlet-class>de.jochenbrissier.backyard.web.BackyardSocket</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>socket</servlet-name>
    <url-pattern>/socket</url-pattern>
  </servlet-mapping>
```


### Define the url to the socket ###
In the jquery plugin you have to define the url to your socket. Socket urls starts with the prefix "ws://" instead of "http://" e.g "ws://${server}/$(webapp)/socket".

To define the socket url there is an option "socket\_url" available in the plugin

```
jQuery.backyard({
url:"aservlet",
socket_url:"ws://${server}/$(webapp)/socket"

});


```



## Thats it ##
The plugin checks the browser websocket support an connect to the socket, othwise the plugin use the comet strategie. You can also have a Browser A(Firefox 3.6) and a Browser B (Apple Safarie) in the same application. The Browser B commuincates via Sockets and Browser A communicates via comet in the same application.