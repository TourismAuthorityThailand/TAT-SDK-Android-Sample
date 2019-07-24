# Place search and Get Place's detail 
This sample demonstrates how to search and get the place detail from TAT SDK.

# Place search
How to use `TATPlaces` to search places used `TATPlacesSearchParameter` specific condition to search by `search` method.

## Parameters
 * **language** use `TATLanguage` to set display language. *supported `TATLanguage.ENGLISH` for display in English or `TATLanguage.THAI` for display in Thai.*
 * **keyword / setKeyword method** The keyword to search *such as place's name, latitude and longitude of place or mapcode.*
 * **geolocation / setGeolocation method** use `TATGeolocation` set up tha latitude, longitude value when you want to search the places around.
 * **categoryCodes / setCategoryCodes method** use `TATCategoryCode` enumerable for specific search category *supported `TATCategoryCode.ALL`, `TATCategoryCode.OTHER`, `TATCategoryCode.SHOP`, `TATCategoryCode.RESTAURANT`, `TATCategoryCode.ACCOMMODATION` or `TATCategoryCode.ATTRACTION`.*
 * **setProvinceName method** for specific province that you want to search.
 * **setSearchRadius method** The search radius is the meter unit which is referenced with latitude, longitude. *(Maximum is 200,000 meters. The default is 100 meters)*
 * **setNumberOfResult method** The number of result. *(Maximum is 50 items. The default is 20 items.)*
 

## How to use the sample
 1. Enter keyword to search.
 2. Choose categories one or more options.
 3. Select `Nearby location` when used the location reference search result.
 4. Click `Search` button to get place result.
 5. When you clicked a result that show the detail of place by category.

### Place search
![](PlaceSearch_android.png)

### Search result
![](SearchResult_android.png)

# Get Place's detail
How to use `TATPlaces` to get place's detail from `TATGetPlaceDetailParameter` and use the category of place to choose the get detail method, such as: `getAttraction`, `getAccommodation`, `getRestaurant`, `getShop` and `getOtherPlace` method with place id from search result.

## Parameters
 * **id** The Place ID from search result.
 * **language** use `TATLanguage` to set display language. *supported `TATLanguage.ENGLISH` for display in English or `TATLanguage.THAI` for display in Thai.*

## How to use the sample
 1. Choose one of place from result.
 2. Show the result of the selected place.

### Place's detail
![](PlaceDetail_android.png)

