- Gitlab injects files and gradle into docker, which returns
exit code 0 if good and 1 if wrong. 
All stages in pipeline are run in a different order, the stages vertically
aligned can run parallel, the horizontally aligned ones do not.
When you run your client-test, then server-test is running on a different
machine, which could be ran on a different timing. If you want to run the server
first, you need to specify this. 
- Alternative -> Mocking: creating fake objects you can use in your tests.
- Spring integration testing for making tests for spring objects.