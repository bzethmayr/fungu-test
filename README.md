# fungu-test

Lightly opinionated test utilities.

### Why would I want another testing library?

Assuming you use JUnit 5+, 
this provides stuff you would almost certainly end up writing yourself anyway,
especially if you're also using Hamcrest.

If you're using AssertJ, you still might want this...

### Features
* Static class verification with `TestHelper#invokeDefaultConstructor`
* Composite matcher creation with `MatcherFactory#has`
* A small vocabulary of test values
* A handy `Irrelevant` class 
* Punctiliously complete javadoc.

### Future features
* Anemic model verification
* Fake value factory
* Concurrent runner
