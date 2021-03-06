# Fyber_challenge

Fyber Challenge has been done. It's great experience to make it. Fyber App runs well. 

# Action Steps
Action Steps have been applied into Fyber App.

1. Create a form asking for the variable params (uid, API Key, appid, pub0)

 => Please refer to [requestOfferList method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/ui/fragment/OfferListFragment.java),  [getOffers method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/Intermediary.java),  [getRequestMethod method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java) and [getOffers](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java).

2. Make the request to the API passing the params and the authentication hash 

 => Please refer to [requestOfferList method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/ui/fragment/OfferListFragment.java),  [getOffers method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/Intermediary.java),  [getRequestMethod method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java) and [getOffers](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java).

3. Get the result from the response.

 => Please refer to [getRequestMethod method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java), [getOffers](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java), [RequestCallback class](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java), [onEvent(OffersDataEvent event) method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/ui/fragment/OfferListFragment.java), [all files which are related with json data](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/main/java/com/goforer/fyber/model/data) and [RecyclerFragment class](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/base/ui/fragment/RecyclerFragment.java).

4. Check the returned hash to check that it’s a real response (check signature)

 => Please refer to [responseValidityCheck method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java).

5. Render the offers in a view.

   A. If we have offers there we render them (title, teaser, thumbnail hires and payout)

   B. If we have no offers there we render a message like ‘No offers’

 => Please refer to [all files which are related with UI](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/main/java/com/goforer/fyber/ui), [RecyclerFragment class](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/base/ui/fragment/RecyclerFragment.java) and [all files which are related Event Bus](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/main/java/com/goforer/fyber/model); 

6. Create functional and unit tests (choose your tool)

 => Please refer to [Unit Test](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/test/java/com/goforer/fyber) and [Instruments Test](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/androidTest/java/com/goforer/fyber).

## Libraries

This app leverages third-party libraries:

 * [Retrofit](http://square.github.io/retrofit/) - For asynchronous network requests
 * [EventBus](http://greenrobot.org/eventbus/) - For communication between Activiteis, Fragments, Servcie, etc
 * [ButterKnife](http://jakewharton.github.io/butterknife/) - For field and method binding for Android views
 * [Glide](https://github.com/bumptech/glide) - For an image loading and caching library for Android focused on smooth scrollin
