package com.lukakordzaia.subscriptionmanager.domain.repository.addsubscription

import com.lukakordzaia.subscriptionmanager.network.networkmodels.AddSubscriptionItemNetwork

interface AddSubscriptionRepository {
    fun addSubscription(subscription: AddSubscriptionItemNetwork)
}