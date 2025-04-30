## This repo is for the Solventum Coding Challenge. This challenge was started on 4/28/2025 and completed 4/29/2025.

### My Java version is 24, my Spring Boot version is 3.4.5, and my gradle version is 8.14

In this challenge I was expected to create two endpoints: one that encodes a url into a shorter url and one that decodes that same url back into the original url.
Both endpoints are expected to return JSON outputs and a limitation of concurrent requests is supposed to be able to be placed on launch.

I decided to implement the concurrency limitation as an argument. First, the application has to be built and because I am on Windows I use

`.\gradlew.bat build`

in the command prompt or terminal. If you are on Mac or Linux, you run `.\gradlew build` instead. I then run 

`.\gradlew.bat bootRun --args='--max=5'`.

I'm unsure if the Unix OSes allow for just normal argument flags like `.\gradlew run --max=5` and I would have to do some testing before being certain that it would work that way.
If no maximum concurrent requests flag is given, the default is 10.

In order to hit the endpoints, you have to Get request (I manually tested it via curl) your localhost with default port of 8080. You then add the /encode or /decode endpoint and
a query string with "url=" as the parameter and your URL. For example, I used 

`curl http://localhost:8080/encode?url=example.com`

when testing it manually. There are defaults for both /encode and /decode based on the examples given in the writeup. The encode one works well because any URL can be encoded,
but obviously the decode one fails because the URLS are randomly generated and the default is hardcoded.

In my app I decided to check for a valid URL in the encode endpoint by adding a regex check on the `?url=` parameter. I didn't know how much this should be enforced and because there are a lot of top-level domains
I just decided to add a simple regex check of if there is a "something"."something" pattern. Any urls that don't follow that format will get a bad request error as a response.
More of the reasoning for this is explained in comments in the /encode implementation.
