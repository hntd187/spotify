package io.scarman.spotify.response

import io.scarman.spotify.request.CategoryPage

case class Categories(
    categories: Paging[Category]
)
