## Release Notes

Libraries/Components I used

1. I used Android Jetpack Architecture, UI and Foundation components.
2. Used Koin library for DI (dependency injection).
3. Used Koil library to load/cache the images.
4. For Unit testing, I used Mokito and Koin.
5. Retrofit for API calls and GSON for data parsing.

Here is why I use these

1. Jetpack ViewModel to handle All the logical operations by adding an abstraction layer between UI and Data.
   The ViewModel class store and manage UI-related data in a lifecycle conscious way & allows data to survive configuration changes such as screen rotations which means data will remain intact.
   In Our case, we are fetching the data only one and is being shared between both fragments.

2. Jetpack LiveData because its bound to life cycle objects and clean up itself when associated lifecycle is destroyed.
   So, in the case of an activity in the back stack, it will not send any new events.

3. Jetpack DataBinding to bind the layouts and views with UI to avoid all the boilerplate code as binding class contains direct references to all views that have an ID in the corresponding layout. 
   Its Null safe, type safe, uses annotations, so compiles faster & is easy to use.

4. Jetpack Navigation component for handling both fragments because its very simple to setup, handles backStack
   and automatic transaction between fragments.

5. I used Jetpack Coroutines to call APIs which are totally thread non-blocking and run in a particular scope at a time.

6. Added imagesView's default placeholder drawables & progressBar in `CarListFragment.kt` while loading the data to make user experience better.

7. Created separated classes for `CarAdapter` & `CarViewHolder` and added support for `DataBinding`.

8. Put all the hardcoded xml resource values into `strings.xml` & `dimens` accordingly.

9. Used Koin library for DI instead of Dagger because it's lightweight, very easy to implement and totally Kotlin based using functional resolution only: no proxy, no code generation, no reflection!.

10. Koil for loading/caching images over Picasso/Glide becasue its totally Kotlin based and used Coroutines under the hood.

11. Used Mokito and Koin for unit testing. I wrote 3 unit tests to check error, empty and list of data cases.

12. Retrofit is dead-simple to use. It essentially lets you treat API calls as simple method calls, 
    so you only define which URLs to hit and the types of the request/response parameters as models.
    The entire network call + JSON/XML parsing is completely handled by it (with help from Gson for JSON parsing),
    along with support for arbitrary formats with pluggable serialization / deserialization.


Note: I couldn't comment/document the code due to short time. 

Looking forward to hear from you!
