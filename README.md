# java-concurrency

https://www.baeldung.com/java-util-concurrent
java concurrency example


# Steps to Write a Unit Test for Thread Safety

1. Identify Critical Sections: Determine which parts of your class are critical for thread safety (e.g., methods that modify shared state).
2. Create Multiple Threads: Simulate concurrent access by creating multiple threads.
3. Introduce Synchronization Points: Use synchronization points like latches to ensure threads run concurrently.
4. Monitor for Race Conditions: Check for race conditions by validating the final state of shared resources.
5. Run Tests Repeatedly: Execute the test multiple times to increase the chance of catching intermittent issues.


Reference

> https://www.baeldung.com/java-testing-multithreaded
