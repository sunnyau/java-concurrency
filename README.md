# java-concurrency

java concurrency example and the special completablefuture


# Steps to Write a Unit Test for Thread Safety

1. Identify Critical Sections: Determine which parts of your class are critical for thread safety (e.g., methods that modify shared state).
2. Create Multiple Threads: Simulate concurrent access by creating multiple threads.
3. Introduce Synchronization Points: Use synchronization points like latches to ensure threads run concurrently.
4. Monitor for Race Conditions: Check for race conditions by validating the final state of shared resources.
5. Run Tests Repeatedly: Execute the test multiple times to increase the chance of catching intermittent issues.

# Software required

> openjdk version "21.0.3"

> Apache Maven 3.8.7

> Gradle 8.5

# How to build by maven

mvn compile test

# How to build by gradle

gradle check

# Tidy up java source code
Alt-Shift-F

# How to start VSCode with wsl2

1. Open Terminal (Ubuntu) and type code.
2. VSCode will open and you should see WSL: Ubuntu-24.04 at the bottom left corner.

# Alternatively

1. Open VSCode on Windows
2. Click the bottom left corner and choose "Connect to WSL"

![Screenshot (8)](https://github.com/sunnyau/java-stream/assets/37674904/8c5cf79f-4a9c-4f40-8970-e4f851e91a9b)

VSCode with WSL2 Reference : https://joe.blog.freemansoft.com/2020/10/java-8-development-on-linuxws-with.html


# Reference

> https://jenkov.com/tutorials/java-util-concurrent/index.html

> https://www.baeldung.com/java-testing-multithreaded

> https://www.baeldung.com/java-util-concurrent

> https://www.baeldung.com/java-completablefuture

> https://www.baeldung.com/java-completablefuture-runasync-supplyasync 