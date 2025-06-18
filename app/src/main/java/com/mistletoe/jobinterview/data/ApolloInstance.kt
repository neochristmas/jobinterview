package com.mistletoe.jobinterview.data

import com.apollographql.apollo.ApolloClient

object ApolloInstance {
    val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:4000/")
            .build()
    }
}
