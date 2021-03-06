[![Build Status](https://app.bitrise.io/app/49324e41650e1ce9/status.svg?token=HI0HMS28EKG8t65W_YSWZw)](https://app.bitrise.io/app/49324e41650e1ce9)

## Release Notes

Libraries/Components I used:

1. Android [Jetpack](https://developer.android.com/jetpack) Architecture, UI and Foundation components.
2. [Koin](https://insert-koin.io) library for DI (dependency injection).
3. [Coil](https://github.com/coil-kt/coil) library to load/cache the images.
4. [Mokito](https://site.mockito.org) and [Coroutines](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/) for Unit testing.
5. [Retrofit](https://square.github.io/retrofit/) for API calls with [GSON](https://github.com/google/gson) for data parsing.

why I choose these?

1. Jetpack ViewModel to handle All the logical operations by adding an abstraction layer between UI and Data.
   The ViewModel class store and manage UI-related data in a lifecycle conscious way & allows data to survive configuration changes such as screen rotations which means data will remain intact.
   In Our case, we are fetching the data only once and is being shared between both fragments.

2. Jetpack LiveData as its bound to life cycle objects and clean up itself when associated lifecycle is destroyed.
   So, in the case of an activity in the back stack, it will not send any new events, hance no memory leaks and crashes.

3. Jetpack DataBinding to bind the layouts and views with UI to avoid all the boilerplate code as binding class contains direct references to all views that have an ID in the corresponding layout. 
   Its Null safe, type safe, uses annotations, so compiles faster & is easy to use.

4. Jetpack Navigation component for handling both of our fragments because its very simple to setup, handles backStack
   and automatic transaction between fragments.

5. I used Jetpack Coroutines to call APIs which are totally thread non-blocking and run in a particular scope at a time.

6. Added imagesView's default placeholder drawables & progressBar in `CarListFragment.kt` while loading the data to make user experience better.

7. Created separated classes for `CarAdapter` & `CarViewHolder` and added support for `DataBinding`.

8. Put all the hardcoded xml resource values into `strings.xml` & `dimens` accordingly.

9. Used Koin library for DI instead of Dagger because it's lightweight, very easy to implement and totally Kotlin based using functional resolution only: no proxy, no code generation, no reflection!.

10. Coil for loading/caching images over Picasso/Glide becasue its Kotlin-first and uses modern libraries including Coroutines, OkHttp, Okio, and AndroidX Lifecycles.

11. Used Mokito and Coroutines for unit testing. Wrote unit tests to check error, empty and list of data cases In [CarViewModelTest.kt](https://github.com/yourappsgeek/Sixt-android-coding-challenge/blob/master/app/src/test/java/com/sixt/codingtask/CarViewModelTest.kt).

12. Retrofit is dead-simple to use. It essentially lets you treat API calls as simple method calls, 
    so you only define which URLs to hit and the types of the request/response parameters as models.
    The entire network call + JSON/XML parsing is completely handled by it (with help from Gson for JSON parsing),
    along with support for arbitrary formats with pluggable serialization / deserialization.
