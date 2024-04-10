# Duct compiler.sass [![Build Status](https://github.com/duct-framework/compiler.sass/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/compiler.sass/actions/workflows/test.yml)

[Integrant][] methods for compiling [Sass][] files to CSS in
the [Duct][] framework.

[integrant]: https://github.com/weavejester/integrant
[sass]:      http://sass-lang.com/
[duct]:      https://github.com/duct-framework/duct

## Installation

To install, add the following to your project `:dependencies`:

    [duct/compiler.sass "0.2.1"]

## Usage

The library provides the `:duct.compiler/sass` key, and is used to
compile Sass files to CSS in development and production environments.

```edn
{:duct.compiler/sass
 {:source-paths  ["resources"]
  :include-paths ["node_modules"]
  :output-path   "target/resources"}}
```

The two mandatory options are `:source-paths`, which is a collection
of directory paths that the compiler searches, and `:output-path`,
which is where the compiler will put the CSS it generates.

The non-mandatory `:include-paths` option specify additional paths to
lookup imported Sass files.

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

Copyright © 2018 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
