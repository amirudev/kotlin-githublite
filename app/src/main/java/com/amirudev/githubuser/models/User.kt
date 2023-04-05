package com.amirudev.githubuser.models

class User(
    id: Int = 0,
    login: String = "",
    avatar_url: String = "",
    html_url: String = "",
    url: String = "",
    name: String = "",
    company: String = "",
    location: String = "",
    public_repos: Int = 0,
    followers: Int = 0,
    following: Int = 0,
) {
    val id: Int = id
    val login: String? = login
    val avatar_url: String? = avatar_url
    val html_url: String? = html_url
    val url: String? = url
    val name: String? = name
    val company: String? = company
    val location: String? = location
    val public_repos: Int = public_repos
    val followers: Int = followers
    val following: Int = following
}