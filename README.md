# GitHub Users App
This application fetches and displays a list of GitHub users and allows navigation to a details screen for each user.

## Design Pattern
MVI (Model-View-Intent) + Clean Architecture

## Tech Stack
DI: Hilt 
Architecture: Jetpack Compose
Navigation: Compose Navigation 
Network Calls: Retrofit with OkHttp Interceptors
Testing Frameworks: JUnit, MockK and Turbine

### Main Components
- **UsersScreen**: Fetch and display the list of GitHub users.
- **DetailScreen**: Displays details for a selected user.
- **GitHubApiService**: Interface for Retrofit API calls.

### API Endpoint
The application fetches data from the following GitHub API endpoint:
- `GET https://api.github.com/users?since=2023`
- `GET https://api.github.com/users/{userId}/repos`
