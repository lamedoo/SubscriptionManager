package com.lukakordzaia.feature_subscriptions

import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

sealed class SubscriptionListType {
    data class Header(val label: Int): SubscriptionListType()
    data class Item(val data: SubscriptionItemDomain): SubscriptionListType()
}
