![Bridge International Academies Logo](BannerLogo280x60.png)

# Android Technical Test

## Architecture

I have followed MVVM architecture to divide the application into View, Model & ViewModel components.
I have implemented repository pattern to consolidate data from network and database. 

TODO: CLEAN architecture can be followed where all the business requirements can be encapsulated in well designed use cases.
All the enterprise rules can be implemented in pure Kotlin modules and framework dependencies can be plugged to them.

## Requirements & assumptions

1. I need to be able to see a list of all pupils.
List is fetched from network when application is launched. User can initiate sync from server from option menu within the app.

2. I need to be able to add a new pupil and submit.
User can navigate to add pupil screen by pressing add floating button on main screen.

3. The above requirements should continue when I am offline. With data synchronising when I'm next online.
Assumptions: No validation is performed on offline cached data. If server validation fails when upload is attempted after online,
records with validation errors are discarded.


## Additional Libraries

1. androidx.navigation : To handle navigation between the fragments
2. androidx.paging : To support paged list in RecyclerView
3. com.github.bumptech.glide : To load pupil image in image view.
4. org.mockito : Mocking objects during tests
