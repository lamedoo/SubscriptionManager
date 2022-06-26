package com.lukakordzaia.feature_home

import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain

sealed class HomeSubscriptionType {
    data class Header(val label: Int): HomeSubscriptionType()
    data class Item(val data: SubscriptionItemDomain): HomeSubscriptionType()
}
