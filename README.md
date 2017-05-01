# Duct compiler.sass

[![Build Status](https://travis-ci.org/duct-framework/compiler.sass.svg?branch=master)](https://travis-ci.org/duct-framework/compiler.sass)

[Integrant][] methods for compiling [Sass][] files to CSS in
the [Duct][] framework.

[integrant]: https://github.com/weavejester/integrant
[sass]:      http://sass-lang.com/
[duct]:      https://github.com/duct-framework/duct

## Installation

To install, add the following to your project `:dependencies`:

    [duct/compiler.sass "0.1.0-SNAPSHOT"]

## Usage

The library provides the `:duct.compiler/sass` key, and is used to
compile Sass files to CSS in development and production environments.

```edn
{:duct.compiler/sass
 {:source-paths ["resources"]
  :output-path  "target/resources"}}
```

The two mandatory options are `:source-paths`, which is a collection
of directory paths that the compiler searches, and `:output-path`,
which is where the compiler will put the CSS it generates.

So if you have a file `resources/public/main.scss`, the compiler will
create a file `target/resources/public/main.css`.

There are also options for setting the `:indent`, which by default is
two spaces:

```edn
{:duct.compiler/sass
 {:source-paths ["resources"]
  :output-path  "target/resources"
  :indent       "\t"}}
```

And for setting the `:output-style`, which can be `:nested` (the
default), `:expanded`, `:compact` or `:compressed`:

```edn
{:duct.compiler/sass
 {:source-paths ["resources"]
  :output-path  "target/resources"
  :output-style :compressed}}
```

## License

Copyright © 2017 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
