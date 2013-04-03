Example of a Blog in the Play!-framework
============

Setting up the system in less than 10 minutes:
---------------------------------------------

1. Download: [play-2.1.1.zip](http://downloads.typesafe.com/play/2.1.1/play-2.1.1.zip)
2. Unzip into a `<directory of your choice>`
3. Add `<directory of your choice>/play-2.1.1/` to your `PATH` (or symlink `play-2.1.1/play/` into `~/bin/`)
4. cd into the repository base
5. Do *ONE* of the following two alternatives:

   5a (easy). Edit `conf/application.conf` and replace the postgres-config with this:

    db.default.driver=org.h2.Driver
    db.default.url="jdbc:h2:mem:play"
    db.default.user=sa
    db.default.password=

   5b (harder). Set up a postgres database with a superuser called `playblog` with password `playblog`

6. cd into the root of the repository and run: `play run`
7. Navigate to [http://localhost:9000/posts](http://localhost:9000/posts)

Introduction
------------
[http://www.playframework.com/](http://www.playframework.com/)

