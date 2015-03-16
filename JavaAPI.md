# The Java API #
Backyard provides a simple Java API.

## Backyard ##



|`public void startAsync();`|
|:--------------------------|
|Stops the incomming HTTP-Request|


|`public void stopAsync();`|
|:-------------------------|
|Returns the HTTP-Response|


|`public static void autoDetectServer(Servlet)`|
|:---------------------------------------------|
|Detects the running server. If you whant to use server detection this should be the first call on backyard, e.g in the init() method|




### Channels ###

|`puclic static Channel getMetaChannel()`|
|:---------------------------------------|
|Returns the Meta channel, which every member of the applications listen to.|



|`puclic static Channel  addChannel(String)`|
|:------------------------------------------|
|Adds an channel|


|`public static void removeChannel(String)`|
|:-----------------------------------------|
|removes a channel.|

|`Backyard.listentoChannel(String, Member)`|
|:-----------------------------------------|
|adds a Member to an channel. The member object will identify by the member id, which is by default the session id.|




|`Backyard.addChannelListener(String, ChannelListener)`|
|:-----------------------------------------------------|
|adds a ChannelListener to an channel. The ChannelLister interface have the following methods:|



## ChannelListener ##
|` Member newMember(Member member); `|
|:-----------------------------------|
|will invoked when a member joins a channel to block the member return null e.g. for password protected channels. simply return null if the Message.getMemberlId()(SessionID) is not premitted to join.|

|`Message newMessage(Message message);`|
|:-------------------------------------|
|will invoked befor a message will send over the channel. To block the message, return  null.|


## Additional informations ##
You found all functions and a detail overview in the javadoc.