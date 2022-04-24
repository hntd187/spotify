package io.scarman.spotify.response

/** { "href" : "https://api.spotify.com/v1/browse/categories/party", "icons" : [ { "height" : 274, "url" :
  * "https://datsnxq1rwndn.cloudfront.net/media/derived/party-274x274_73d1907a7371c3bb96a288390a96ee27_0_0_274_274.jpg", "width" : 274 } ],
  * "id" : "party", "name" : "Party" }
  */
// TODO sttp URI instead of String? Explore circe decoders
case class Category(id: String, name: String, href: String, icons: List[SharedImage])
