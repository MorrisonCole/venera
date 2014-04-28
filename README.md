Venera needs to know your top-level 'Set Up' and 'Tear Down' methods, along with the name of the Type where they are defined in order to insert initial instrumentation.

It also needs to know the location of your test / debug APKs.

The tool assumes methods whose names match the regex 'test*' are tests and instruments them.

JSON log files are created for each test and are named like: TODO!