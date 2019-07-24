# Feed news and Get News's detail 
This sample demonstrates how to use `TATNews` to feed the news with `TATFeedNewsParameter` and how to get news' detail from `TATGetNewsDetailParameter`.


# Feed news 
How to use `TATNews` to feed the news by `feed` method.
## Parameters
 * **language** use `TATLanguage` to set display language. *supported `TATLanguage.ENGLISH` for display in English or `TATLanguage.THAI` for display in Thai.*

## How to use the sample
 1. The results are sorted according by latest published date.

### Feed news
![](GetNews_android.png)

# Get News's detail 
How to use `TATNews` to get the news's detail from feed news result by `getDetail` method with news id.
## Parameters
 * **id** The News ID from feed news result.
 * **language** use `TATLanguage` to set display language. *supported `TATLanguage.ENGLISH` for display in English or `TATLanguage.THAI` for display in Thai.*

## How to use the sample
 1. Choose one of news from feed result.
 2. Show the result of the selected news.

### News's detail
![](NewsDetail_android.png)
