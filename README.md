# auto-rt

A script that listens for @ mentions using the Twitter Streaming API and automatically retweets them.

This isn't very robust, a loss of connection to the Streaming API will cause the process to die.  Heroku will restart it but it won't be instantaneous.  This script won't poll for updates that could've happened while the connection was lost.

It's deployed against http://twitter.com/gotanysnacks, an anonymous Twitter account.  (hint: send a txt/email to skcansynatog at gotanysnacks.com, or mention it from your account)

## Usage

This script is intended to be deployed on Heroku.  

There are several environment variables used for configuration:

* USER_ID: the id of the Twitter account this is running against.  Used to determine
if activity in the Streaming API is a mention or a post by this user.
* APP_CONSUMER_KEY: OAuth consumer key
* APP_CONSUMER_SECRET: OAuth consumer secret
* USER_ACCESS_TOKEN: OAuth access token for the account to watch
* USER_ACCESS_TOKEN_SECRET: OAuth secret for the account to watch

Procfile:

```
run: lein run -m auto-rt
```

Do something like

```
heroku ps:scale web=0 run=1
```

To run this script without requiring it to listen for HTTP connections.

## License

Copyright © 2012 DGAF

Distributed under the Eclipse Public License, the same as Clojure.
