# FutureChat

Android messaging app starter project.

## Included in this first build
- FutureChat branded phone-number entry screen (demo only)
- Chats, Calls, Contacts, and Profile navigation
- GitHub Actions workflow for a debug APK

## Not connected yet
Phone OTP verification, real-time messaging, friend discovery, push notifications, voice/video calls, and admin controls are not implemented in this starter.

## Before production
- Configure Supabase Phone Auth with an SMS provider.
- Integrate Supabase Auth and database access in the Android app.
- Review and tighten Row Level Security policies.
- Add a trusted backend for admin role assignment.
- Implement WebRTC call signalling and push notifications.

Never put a Supabase service-role key in an Android app.
