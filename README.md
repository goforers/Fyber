# Fyber_challenge

Fyber Challenge has been done. It's great experience to make it. Fyber App runs well. 

# Action Steps
Action Steps have been applied into Fyber App.

1. Create a form asking for the variable params (uid, API Key, appid, pub0)

 => Please refer to [requestOfferList method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/ui/fragment/OfferListFragment.java),  [getOffers method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/Intermediary.java),  [getRequestMethod method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java) and [getOffers](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java).

2. Make the request to the API passing the params and the authentication hash 

 => Please refer to

3. Get the result from the response.

 => Please refer to

4. Check the returned hash to check that it’s a real response (check signature)

 => Please refer to [responseValidityCheck method](https://github.com/goforers/Fyber/blob/master/Fyber/app/src/main/java/com/goforer/fyber/web/communicator/RequestClient.java).

5. Render the offers in a view.

   A. If we have offers there we render them (title, teaser, thumbnail hires and payout)

   B. If we have no offers there we render a message like ‘No offers’

 => Please refer to

6. Create functional and unit tests (choose your tool)

 => Please refer to [Unit Test](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/test/java/com/goforer/fyber) and [Instruments Test](https://github.com/goforers/Fyber/tree/master/Fyber/app/src/androidTest/java/com/goforer/fyber).
