# EventTracker: Product Requirement Document

Screenshots of EventTracker App
-------------------------------

![](https://user-images.githubusercontent.com/9923181/29957553-1e2100dc-8ebc-11e7-8244-761be11105e8.JPG)

![](https://user-images.githubusercontent.com/9923181/29957532-f8713a5a-8ebb-11e7-890a-7666c641c433.png)

![](https://user-images.githubusercontent.com/9923181/29957543-0e77f05a-8ebc-11e7-8ced-9ea36012576b.JPG)

Description of App’s Use-Case
-----------------------------

Typically in universities or colleges, people put up posters, flyers, etc to notify people of events, happenings, occurrences. So instead of that, my idea is an android app that that lets users see a list of events, view information about single events, add/delete new events and share it with a community of perspective attendees - for this project's purpose, it can be a group of friends or people within an university’s club.
The app has the added social benefit of reducing paper waste. 

List of Screens
---------------

The app will have the following page organization: 

o Timeline/Feed Of Events (Home Page): Allows viewing of all events’ shared within the group
	
o *Menu: Allows users to view what groups they are part of and create more groups

o Add-Event Page: Allows users to add new events in the feed 

o View-Event Page: Allows users to view information about individual event and RSVP

*Limited implementation. Not necessary to demonstrate the minimum viable product.

What Features/UI Elements/Interactions Each Page Will Implement
---------------------------------------------------------------

Timeline/Feed of Events (Home Page):
* Have a hamburger menu icon on the top, left side of the app (beside the name of the app) in the header
* Have a red, circular button at the bottom, right side corner that when pressed will open the Add-Event page
* The timeline/feed will consist of the names of events and their descriptions
o Clicking on an event/description will take to the View-Event page

![Feed](https://user-images.githubusercontent.com/9923181/29957545-171174ac-8ebc-11e7-802e-152407ac10b0.JPG)

Add-Event Page:
* Can be accessed through the Home Page via clicking the red, circular button at the bottom, right side corner
* The following are required fields that a user needs to fill out to create a new event:
o Event Name (a text field): Add a short, clear name
o Location (a text field): Include a place or address
o Date and Time (date and time fields)
o Description (a textarea field): Tell people more about the event
* A “Cancel” button and a “Create Event” button is shown at the bottom of the page

View-Event Page:
* Can be accessed via clicking the event’s name/description on the timeline/feed
* Displays the following information about the event:
o The date of the event
o The name of the event
o Shows the “RSVP” button
o Date and Time
o Location
o Description

*Menu: 
* Accessed through the Home Page via clicking the hamburger menu icon
* Has a list of all the groups the user is part of
* Has a circular button that allows user to create new groups by entering a new group’s name and selecting a group of perspective attendees from phone’s contacts or manually entering email address/phone numbers

*Limited implementation. Not necessary to demonstrate the minimum viable product.
