# Get list of Events and Event Detail <a name="Events and Festivals"></a>

This sample demonstrates how to use `TATGetEvents` to get the list of events with `TATGetEventsParameter` and how to get the detail of event from `TATGetEventDetail` with `TATGetEventDetailParameter` to show in `TATGetEventDetailResult`.

## Get Events parameters
 * **latitude and longitude / setLatitude or setLongitude method** *value when you want to search events around you.*
 * **sort / setSort method** *use `TATSort.DISTANCE` for sorted by distance or use `TATSort.DATE` for sorted by start date of event.*
 * **language** *use `TATLanguage.ENGLISH` for English display or `TATLanguage.THAI` for Thai display.*

## How to use the sample
 1. Choose sort menu on status bar that show 2 options are By Date or By Distance.
 2. The results are sorted according by sort option you choose.


### Get Events
<img src="GetEvents_android.png" width="30%">

### Sort options
<img src="SortOptions_android.png" width="30%">

## Get Event Detail parameters
 * **eventId / setEventId method** *from events result*
 * **language / setLanguage method** *use `TATLanguage.ENGLISH` for English display or `TATLanguage.THAI` for Thai display.*

## How to use the sample
 1. Choose one of event from result.
 2. Show the result of the selected event.

### Event Detail
<img src="EventDetail_android.png" width="30%">
