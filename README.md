
Chat TCP using netty framework Can chat TCP continuously

* 1) Run Entry.class
  2) Run ClientMain.class
  3) At ClientMain.class, you can using readTxt or readFile, if you chat TCP, using readTxt and scan String terminal (Can scan continuously)
  4) In ServerHandler.class at channelRead0: you can scan String and send to Client
