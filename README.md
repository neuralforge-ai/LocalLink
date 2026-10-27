# LocalLink – Nearby Services Finder

Android app (Java + XML, min SDK 24) built for **Assignment 2** of Mobile Application Development.
It is the first working prototype of the LocalLink project proposed in Assignment 1 (Feasibility Study):
customers and service providers can register, log in and browse service categories.
Authentication and password recovery are **simulated locally** (no backend).

## Features implemented
- Splash screen with logo, name and tagline that auto-navigates (to Login, or Home if already logged in)
- Login with email/password, "Show password" checkbox, links to Register and Forgot Password
- Register with name, email, password, confirm password and account type (Customer / Service Provider – FR-01)
- Forgot Password: email check and simulated reset link
- Reset Password: new password + confirm password
- Home: service categories (Electrician, Plumber, Mechanic, Tailor, Home Tutor, Favourites – FR-02) as icon cards in a grid
- Profile: shows name, email and account type, with Logout
- Input validation on every form (empty fields, email format, password >= 6 characters, passwords must match) shown with `setError()`
- Dark mode and Urdu translation through alternative resources

## Screens and layouts
| Screen | Activity | Layout used |
|---|---|---|
| Splash | `SplashActivity` | ConstraintLayout |
| Login | `LoginActivity` | LinearLayout |
| Register | `RegisterActivity` | LinearLayout (+ RadioGroup) |
| Forgot Password | `ForgotPasswordActivity` | LinearLayout |
| Reset Password | `ResetPasswordActivity` | LinearLayout |
| Home | `HomeActivity` | GridLayout |
| Profile | `ProfileActivity` | ConstraintLayout |

## Navigation flow (explicit Intents)
```
Splash -> Login <-> Register
            |
            +-> Forgot Password -> Reset Password -> Login
            |
            +-> Home (category grid) -> Profile -> Logout -> Login
```
Data is passed with `putExtra()` (email to Login/Reset, name/email/role to Profile).

## Resources
- `values/strings.xml` – all text (default, English)
- `values-ur/strings.xml` – Urdu (alternative resource)
- `values/colors.xml` and `values-night/colors.xml` – light and dark palettes
- `values/styles.xml`, `values/dimens.xml`, `values/themes.xml` – shared styles for a consistent UI
- `drawable/` – vector icons and shape backgrounds

## Demo credentials
| Email | Password |
|---|---|
| test@locallink.com | 123456 |

New accounts created on the Register screen are stored in SharedPreferences and work for login.

## Tech
Java, XML, Android Studio, AppCompat, ConstraintLayout, min SDK 24.
