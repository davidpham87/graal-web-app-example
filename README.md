# A minimal GraalVM Clojure web application:

This is a modern take about the example from
[yogthos](https://github.com/yogthos/graal-web-app-example).

It follows the instructions from [Redpill
linpro](https://www.redpill-linpro.com/techblog/2021/03/31/faster-clojure-with-graalvm.html)
to build the native images with docker and adds
`com.github.clj-easy/graal-build-time` to ease compilation.

* Environment: [yogthos/config](https://github.com/yogthos/config)
* HTTP Server: [HTTP Kit](https://github.com/http-kit/http-kit)
* HTML templating: [Hiccup](https://github.com/weavejester/hiccup)
* Resource management: [Mount](https://github.com/tolitius/mount)
* Routing: [Reitit](https://metosin.github.io/reitit/)

## Requirements

* [GraalVM](https://github.com/oracle/graal/releases)
+ [Babashka](https://github.com/babashka/babashka)
* [Docker](https://www.docker.com/) or [Podman](https://podman.io/)

## Usage

The HTTP port for the application is declared in the `config.edn` file:

```clojure
{:port 3000}
```

Start the nREPL

    clojure -M:cider-repl

Compile native binary by running:

    bb native # creates the graalvm-http-kit-image image
    bb run # build the http-kit container
    bb cp # copy the app into the source project
    bb clean # stop the container

Run the app:

    ./app
