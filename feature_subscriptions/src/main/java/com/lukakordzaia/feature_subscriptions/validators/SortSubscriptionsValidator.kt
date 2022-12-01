package com.lukakordzaia.feature_subscriptions.validators

import com.lukakordzaia.core.utils.Constants
import com.lukakordzaia.core_domain.BaseUseCase
import com.lukakordzaia.core_domain.domainmodels.SubscriptionItemDomain
import com.lukakordzaia.feature_subscriptions.SubscriptionListType

class SortSubscriptionsValidator : BaseUseCase<List<SubscriptionItemDomain>, List<SubscriptionListType>> {
    override suspend fun invoke(args: List<SubscriptionItemDomain>?): List<SubscriptionListType> {
        val music = args!!.filter { it.subscriptionType == Constants.SubscriptionType.MUSIC }
        val entertainment = args.filter { it.subscriptionType == Constants.SubscriptionType.ENTERTAINMENT }
        val online = args.filter { it.subscriptionType == Constants.SubscriptionType.ONLINE }
        val videoStreaming = args.filter { it.subscriptionType == Constants.SubscriptionType.VIDEO_STREAMING }
        val profession = args.filter { it.subscriptionType == Constants.SubscriptionType.PROFESSION }
        val other = args.filter { it.subscriptionType == Constants.SubscriptionType.OTHER }

        val list: MutableList<SubscriptionListType> = ArrayList()

        if (music.isNotEmpty()) {
            list.add(0, SubscriptionListType.Header(com.lukakordzaia.core.R.string.music))
            list.addAll(list.size, music.map { SubscriptionListType.Item(it) })
        }

        if (entertainment.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.entertainment))
            list.addAll(list.size, entertainment.map { SubscriptionListType.Item(it) })
        }

        if (online.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.online))
            list.addAll(list.size, online.map { SubscriptionListType.Item(it) })
        }

        if (videoStreaming.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.video_streaming))
            list.addAll(list.size, videoStreaming.map { SubscriptionListType.Item(it) })
        }

        if (profession.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.profession))
            list.addAll(list.size, profession.map { SubscriptionListType.Item(it) })
        }

        if (other.isNotEmpty()) {
            list.add(if (list.isEmpty()) 0 else list.size, SubscriptionListType.Header(com.lukakordzaia.core.R.string.other))
            list.addAll(list.size, other.map { SubscriptionListType.Item(it) })
        }

        return list
    }
}