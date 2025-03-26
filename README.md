Hello!

This implementation fetches a default list of events with search functionality to fetch a list of events based on city queries. 
On launch, TicketMaster API is queried for events with city = “”
On user search query, TicketMaster API is queried for events with city = search query

Approach

To implement this code challenge, I have applied MVVM architecture to simplify and manage complexity by clear separation of concerns and to better organize the code making it easily testable.
I have implemented pagination using Android’s Paging library to maintain a single source of truth locally in the database for UI  and  making efficient updates using RemoteMediator to fetch data from the network. To support this, I have used the Room Persistent library for local cache and Retrofit2 library for API calls.  
For simplicity and improved performance, I have used Jetpack Compose to implement UI. For dependency injection, I have used the Hilt framework which made both development and testing easier. For testing, I have made use of coroutines, compose ui tests Junit4,  MockK, JUnit, espresso frameworks for testing different layers of the app. For optimized asynchronous loading of images from the network, I have used the Coil library. 

Error scenarios handled - No network scenario,When user enters an invalid city name

tested on Different Screen sizes 

Assumptions 

→ API_KEY is stored in source for simplicity. For security, API KEY should be stored on server or outside project source directory such as in gradle.properties or encrypted with Android Key store to avoid misuse.

→ Given the server response - “API Limits Exceeded: Max paging depth exceeded. (page * size) must be less than 1,000”, max page number queried is 1000/size


Set up 

→ Have used latest version of all dependencies in gradle mentioned in “TestApp\gradle\libs.versions.toml" as of 26th March 2025 
