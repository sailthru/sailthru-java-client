package com.sailthru.client.params.query;

/**
 *
 * @author praj
 */
public enum Criteria {
    match,
    not,
    gt,
    lt,
    min,
    max,
    valid,
    domain,
    geo_city,
    geo_state,
    geo_country,
    horizon_interest,
    click_time,
    open_time,
    purchase_time,
    purchase_count_min,
    purchase_count_max,
    purchase_largest_item_price_min,
    purchase_largest_item_price_max,
    signup_before,
    signup_after,
    status_softbounce,
    status_hardbounce,
    optout_template,
    optout_blast,
    optout_blast_only,
    optout_all,
    list_member_not,
    engagement_min,
    engagement_max
}
